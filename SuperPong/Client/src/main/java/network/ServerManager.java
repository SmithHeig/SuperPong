package network;

import game.Player;
import protocole.data.matchmaking.GameJoin;
import protocole.data.matchmaking.InscriptionMatchmaking;
import protocole.game.ClientInfoMove;
import protocole.game.ServerInfo;
import protocole.mapper.JsonMapper;
import protocole.Protocole;
import protocole.SuperPongProtocole;
import protocole.data.Disconnection.Disconnection;
import protocole.data.Disconnection.DisconnectionConfirmation;
import protocole.data.connection.LoginConfirmation;
import protocole.data.connection.Login;

import java.io.*;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Classe gérant la conenction avec le serveur et la communication **/
public class ServerManager {

    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(ServerManager.class.getName());

    /* INSTANCE */
    private static ServerManager instance;

    /* ATTRIBUTS */
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;
    private int idUser;

    boolean isConnected;

    /**
     * Constructeur privé (Singleton)
     */
    private ServerManager(){
    	idUser =0;
    }

    /**
     * Fonction pour récupéré l'instance du manager de serveur
     * @return l'instance du serveur
     */
    public static ServerManager getInstance() {
        if(instance == null){
            instance = new ServerManager();
        }
        return instance;
    }

    /**
     * Fonction pour se connecter et logger au serveur
     * @param _username - Username du client
     * @param pwd - password du client
     * @return true if have been connected
     */
    public boolean connect(String _username, String pwd){
        try {
            this.username = _username; // TODO: Stoquer cette info dans la classe Player

            /* INITIALISATION CONNECTION */
            InputStream in = getClass().getClassLoader().getResourceAsStream("config/configServer.properties");
            Properties properties = new Properties();
            properties.load(in);
            String serverAddr = properties.getProperty("serverSuperPongAddress");
            int serverPort = Integer.parseInt(properties.getProperty("serverSuperPongPort"));
            LOG.log(Level.INFO, "Try to connect to server " + serverAddr + " by port " + serverPort);
            clientSocket = new Socket(serverAddr, serverPort);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            /* LOGIN */
            Protocole msg = new Protocole(SuperPongProtocole.CMD_CONNECT, new Login(username, pwd));

            /* SENDING */
            sendMessageToServer(msg);

            /* Attente de la réponse du serveur */
            Protocole responseServer = readMsgFromServer();

            if(responseServer.getName().equals(SuperPongProtocole.CMD_CONNECT)){
                /* Test si la connexion à été accepté */
                LoginConfirmation data = (LoginConfirmation) responseServer.getData();
                if(data.isConnected()){
                    LOG.log(Level.INFO, "User is connected to server");
                    isConnected = true;
                    return true; // connection possible
                } else {
                    LOG.log(Level.INFO, "User have been refused connection");
                }
            } else {
                LOG.log(Level.SEVERE, "Wrong response from server");
            }
        } catch (IOException e){
            LOG.log(Level.SEVERE, "Impossible to join the server with exception : " + e.getMessage());
        }
        return false; // Echec de la connection
    }

    /**
     * Permet de ce déconnecter du serveur
     * @return true si la déconnection c'est bien passé
     */
    public boolean disconnect(){
        /* DECONNECTION */
        Protocole msg = new Protocole(SuperPongProtocole.CMD_DISCONNECT, new Disconnection(username));

        sendMessageToServer(msg);

        /* Reponse du serveur */
        Protocole responseServer = readMsgFromServer();

        if(responseServer != null && responseServer.getName().equals(SuperPongProtocole.CMD_DISCONNECT)) {
            DisconnectionConfirmation data = (DisconnectionConfirmation) responseServer.getData();
            if(data.isDisconnected()){
                LOG.log(Level.INFO, "User have been disconnected");
                isConnected = false;
                return true;
            }
        } else {
            LOG.log(Level.SEVERE, "Wrong response from server");
        }
        return false;
    }

    /**
     * Inscrit le joueur à une partie (matchmaking)
     * @param nbPlayer - nombre de joueur dans la partie à rejoindre
     */
    public boolean inscriptionGame(int nbPlayer){
        LOG.log(Level.INFO, "User inscrit in a matchmaking");
        /* INSCRIPTION */
        Protocole msg = new Protocole(SuperPongProtocole.CMD_INSCRIPTION_GAME, new InscriptionMatchmaking(username,nbPlayer));

        sendMessageToServer(msg);

        /* Reponse du server */
        Protocole responseServer = readMsgFromServer();

        if(responseServer != null & responseServer.getName().equals(SuperPongProtocole.CMD_INSCRIPTION_GAME)){
            GameJoin data = (GameJoin) responseServer.getData();
            LOG.log(Level.INFO, "User ");
            idUser = data.getId();
            return data.isHaveJoin();
        }
        return false;
    }

    /**
     * Envoie les informations de position du joueur
     * @param player - Les infos à serialiser
     */
    public synchronized void sendPlayerInfo(Player player){
        LOG.log(Level.INFO, "User send movement to server");

        Protocole msg = new Protocole(SuperPongProtocole.CMD_PLAY, new ClientInfoMove(player));

        sendMessageToServer(msg);
    }

    /**
     * Methode pour récupéré les infos des autres joueurs et de la balle
     * @return
     */
    public synchronized ServerInfo receivedGameInfos(){
        Protocole responseServer = readMsgFromServer();
        return (ServerInfo)responseServer.getData();
    }

    /**
     * Retourne si le client est connecté ou non
     * @return
     */
    public boolean isConnected(){
        return isConnected;
    }
    
    public int getIdUser(){return idUser;}

    /**
     * Fonction permettant d'envoyé un msg au server
     * @param msg - msg à envoyé sous forme de protocole
     */
    private void sendMessageToServer(Protocole msg){
        String jsonMsg = JsonMapper.getInstance().convertToString(msg);
        LOG.log(Level.INFO,"CLIENT: Sending msg : " + jsonMsg);
        writer.print(jsonMsg + "\n");
        writer.flush();
    }

    /**
     * Permet de lire la réponse du server et de la sérialisé dans la classe "Protocole"
     * @return le messag du serveur (protocole)
     */
    private Protocole readMsgFromServer(){
        try {
            String msgJson;
            while((msgJson = reader.readLine()) == null || msgJson.equals("")); // TODO: Comprendre pourquoi le serveur envoie des "" de temps en temps
            LOG.log(Level.INFO, "CLIENT: Received msg : " + msgJson);
            Protocole msg = JsonMapper.getInstance().convertToProtocole(msgJson);
            return msg;
        } catch(IOException e){
            LOG.log(Level.SEVERE, "Can not read server msg with excpetion : " + e.getMessage());
        }
        return null;
    }
}
