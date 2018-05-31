package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import protocole.Protocole;
import protocole.SuperPongProtocole;
import protocole.data.Disconnection.Disconnection;
import protocole.data.Disconnection.DisconnectionConfirmation;
import protocole.data.connection.LoginConfirmation;
import protocole.data.connection.Login;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements IClientHandler{
    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(ClientHandler.class.getName());

    /* OBJECT MAPPER */
    private static final ObjectMapper JSONobjectMapper = new ObjectMapper();
    static {
        JSONobjectMapper.registerSubtypes(new NamedType(LoginConfirmation.class, "LoginConfirmation"));
        JSONobjectMapper.registerSubtypes(new NamedType(Login.class, "Login"));
    }

    /* ATTRIBUTS */
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isConnected;

    public void handleClientConnection(InputStream is, OutputStream os) throws IOException {

        reader = new BufferedReader(new InputStreamReader(is));
        writer = new PrintWriter(new OutputStreamWriter(os));

        isConnected = false;

        String json;
        boolean done = false;
        boolean isConnected = false;

        LOG.log(Level.INFO, "Initialisation fo the handleClientConnection");

        // TODO: A améliorer
        json = reader.readLine();
        LOG.log(Level.INFO, json);
        while (!done && (json != null)) {
            LOG.log(Level.INFO, "COMMAND: {0}", json);

            Protocole msgReveived = JSONobjectMapper.readValue(json, Protocole.class);


            String cmd = msgReveived.getName();

            /* Regarde pour la connection */
            if (!isConnected) {
                Login user = (Login) msgReveived.getData();
                LOG.log(Level.INFO, "The user " + user.getUsername() + " try to connect to the server");

                Protocole ConnectionMsg;

                if (SuperPongProtocole.CMD_CONNECT.equals(cmd.toUpperCase())) {
                    /* Connection accepté */
                    ConnectionMsg = new Protocole(SuperPongProtocole.CMD_CONNECT, new LoginConfirmation(true));
                } else {
                    /* Connection refusé */
                    ConnectionMsg = new Protocole(SuperPongProtocole.CMD_CONNECT, new LoginConfirmation(false));
                }
                sendToClient(ConnectionMsg);
            }
            /* User Connected */
            else {
                switch (cmd.toUpperCase()) {
                    /* DECONNECTION */
                    case SuperPongProtocole.CMD_DISCONNECT:
                        Disconnection userDisconnected = (Disconnection) msgReveived.getData();

                        LOG.log(Level.INFO, "The user " + userDisconnected.getUsername() + " disconnected from the server");

                        /* Respond to the client */
                        Protocole disconnectMsg = new Protocole(SuperPongProtocole.CMD_DISCONNECT, new DisconnectionConfirmation(true));

                        sendToClient(disconnectMsg);

                        done = true; // Will stopped the clientHandler

                        break;
                    default:
                        writer.println("Huh? please use HELwP if you don't know what commands are available.");
                        writer.flush();

                        break;
                }
                writer.flush();
                json = reader.readLine();
            }
        }
    }

    private void sendToClient(Protocole msg){
        try {
            writer.println(JSONobjectMapper.writeValueAsString(msg));
            writer.flush();
        } catch(JsonProcessingException e){
            LOG.log(Level.SEVERE, "Can not serialize the msg with exception :" + e.getMessage());
        }
    }
}
