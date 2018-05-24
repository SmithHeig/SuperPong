package server;

import jdk.nashorn.internal.parser.JSONParser;

import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import protocole.Protocole;
import protocole.SuperPongProtocole;
import protocole.data.*;

public class ClientHandler implements IClientHandler{
    /** LOGGER **/
    private final static Logger LOG = Logger.getLogger(ClientHandler.class.getName());

    /** OBJECT MAPPER **/
    private static final ObjectMapper JSONobjectMapper = new ObjectMapper();

    /** ATTRIBUTS **/
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isConnected;

    public void handleClientConnection(InputStream is, OutputStream os) throws IOException {

        reader = new BufferedReader(new InputStreamReader(is));
        writer = new PrintWriter(new OutputStreamWriter(os));

        isConnected = false;

        String json;
        boolean done = false;
        // TODO: Changer le reader.readline
        while (!done && ((json = reader.readLine()) != null)) {
            LOG.log(Level.INFO, "COMMAND: {0}", json);

            Protocole msgReveived = JSONobjectMapper.readValue(json, Protocole.class);
            String cmd = msgReveived.getName();
            switch (cmd.toUpperCase()) {
                case SuperPongProtocole.CMD_CONNECT:
                    Login user = (Login)msgReveived.getData();
                    LOG.log(Level.INFO, "The user " + user.getUsername() + " try to connect to the server");

                    /** Respond to the client **/
                    IData data = user;
                    Protocole msg = new Protocole();
                    msg.setName(SuperPongProtocole.CMD_CONNECT);

                    /** DATA RESPONSE IF OK **/
                    msg.setData(new IData() {
                        boolean isConnected = true;
                    });
                    sendToServer(msg);

                    break;
                default:
                    writer.println("Huh? please use HELP if you don't know what commands are available.");
                    writer.flush();
                    break;
            }
            writer.flush();
        }
    }

    private void sendToServer(Protocole msg){
        try {
            writer.println(JSONobjectMapper.writeValueAsString(msg));
            writer.flush();
        } catch(JsonProcessingException e){
            LOG.log(Level.SEVERE, "Can not serialize the msg with exception :" + e.getMessage());
        }
    }
}
