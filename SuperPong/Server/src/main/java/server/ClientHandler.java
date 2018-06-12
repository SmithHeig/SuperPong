package server;

import db.DB;
import gameServer.Game;
import gameServer.Matchmaking;
import gameServer.PlayerServer;
import game.Player;
import protocole.Protocole;
import protocole.SuperPongProtocole;
import protocole.data.Disconnection.Disconnection;
import protocole.data.Disconnection.DisconnectionConfirmation;
import protocole.data.connection.LoginConfirmation;
import protocole.data.connection.Login;
import protocole.data.matchmaking.InscriptionMatchmaking;
import protocole.data.stats.Stats;
import protocole.game.ClientInfoMove;
import protocole.mapper.JsonMapper;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements IClientHandler{
    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(ClientHandler.class.getName());

    /* ATTRIBUTS */
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isConnected;
    private String username; // TODO Choisir si on passe dans un protocole ou si on le stoque
    private Game game;
    boolean done;

    public void handleClientConnection(InputStream is, OutputStream os) throws IOException {

        reader = new BufferedReader(new InputStreamReader(is));
        writer = new PrintWriter(new OutputStreamWriter(os));

        isConnected = false;

        String json;
        boolean isConnected = false;

        LOG.log(Level.INFO, "Initialisation fo the handleClientConnection");

        Protocole msgReceived;
        done = false;

        while (!done && ((msgReceived = readMsgFromClient()) != null)) {

            String cmd = msgReceived.getName();

            /* Regarde pour la connection */
            if (!isConnected) {
                if(msgReceived.getName().equals(SuperPongProtocole.CMD_CONNECT)) {
                    Login user = (Login) msgReceived.getData();
                    LOG.log(Level.INFO, "The user " + user.getUsername() + " try to connect to the server");

                    // TODO: verification si utilisateur et mdp correct
                    isConnected = DB.getInstance().checkPlayer(user.getUsername(), user.getPassword());

                    username = user.getUsername();
                    Protocole ConnectionMsg = new Protocole(SuperPongProtocole.CMD_CONNECT, new LoginConfirmation(isConnected));

                    sendToClient(ConnectionMsg);

                } else {
                    LOG.log(Level.SEVERE, "User not connected");
                    // TODO: A gérer et renvoyé une réponse à l'utilisateur
                }
            }
            /* User Connected */
            else {
                switch (cmd.toUpperCase()) {
                    /* DECONNECTION */
                    case SuperPongProtocole.CMD_DISCONNECT:
                        Disconnection userDisconnected = (Disconnection) msgReceived.getData();

                        LOG.log(Level.INFO, userDisconnected.toString());

                        /* Respond to the client */
                        Protocole disconnectMsg = new Protocole(SuperPongProtocole.CMD_DISCONNECT, new DisconnectionConfirmation(true));

                        sendToClient(disconnectMsg);

                        done = true; // Will stopped the clientHandler
                        LOG.log(Level.INFO, "user disconnected");
                        break;
                    /* MATCHMAKING */
                    case SuperPongProtocole.CMD_INSCRIPTION_GAME:
                        InscriptionMatchmaking inscriptionMatchmaking = (InscriptionMatchmaking) msgReceived.getData();

                        LOG.log(Level.INFO, inscriptionMatchmaking.toString());

                        String _username = inscriptionMatchmaking.getUsername();
                        PlayerServer playerServer = new PlayerServer(new Player(_username), this);

                        /* Donne la communication (écriture) au matchmaking puis au jeu */
                        Matchmaking.getInstance().inscriptionGame2players(playerServer);

                        break;
                    /* Play */
                    case SuperPongProtocole.CMD_PLAY:
                        ClientInfoMove clientInfoMove = (ClientInfoMove) msgReceived.getData();

                        LOG.log(Level.INFO, clientInfoMove.toString());

                        if(game != null){
                            game.updateFromClient(clientInfoMove.getPlayer());
                        }

                        break;
                    /* SHOW STATS */
                    case SuperPongProtocole.CMD_SHOW_STATS:
                        Stats stats = DB.getInstance().getStats(username);
                        Protocole msg = new Protocole(SuperPongProtocole.CMD_SHOW_STATS, stats);
                        sendToClient(msg);
                        break;
                    default:
                        LOG.log(Level.SEVERE, "Wrong Protocole");
                        break;
                }
            }
        }
        LOG.log(Level.INFO, "ClientHandler closing...");
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public void setGame(Game game){
        this.game = game;
    }

    private void sendToClient(Protocole msg){
        String msgJson = JsonMapper.getInstance().convertToString(msg);
        LOG.log(Level.INFO, "SERVER: Send msg : " + msgJson);
        writer.println(msgJson + "\n");
        writer.flush();
    }

    private Protocole readMsgFromClient(){
        try {
            Protocole msg;
            String msgJson = reader.readLine();
            LOG.log(Level.INFO, "SERVER: Received msg : " + msgJson);
            if(msgJson == null){
                LOG.log(Level.INFO, "User have been sudently disconnected");
                done = true;
                return null;
            } else {
                msg = JsonMapper.getInstance().convertToProtocole(msgJson);
            }
            return msg;
        } catch(IOException e){
            LOG.log(Level.SEVERE, "Can not read client msg with excpetion: " + e.getMessage());
            return null;
        }
    }
}
