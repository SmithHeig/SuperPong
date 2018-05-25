package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import protocole.Protocole;
import protocole.SuperPongProtocole;
import protocole.data.Connection;
import protocole.data.IData;
import protocole.data.Login;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        LOG.log(Level.INFO, "Initialisation fo the handleClientConnection");

        // TODO: A améliorer
        while (!done && ((json = reader.readLine()) != null)) {
            LOG.log(Level.INFO, "COMMAND: {0}", json);
            SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());

            SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
            resolver.addMapping(IData.class, Login.class); // TODO FOnctionne uniquement pour la connexion
            module.setAbstractTypes(resolver);

            JSONobjectMapper.registerModule(module);

            Protocole msgReveived = JSONobjectMapper.readValue(json, Protocole.class);

            String cmd = msgReveived.getName();
            switch (cmd.toUpperCase()) {
                case SuperPongProtocole.CMD_CONNECT:
                    Login user = (Login)msgReveived.getData();
                    LOG.log(Level.INFO, "The user " + user.getUsername() + " try to connect to the server");

                    /** Respond to the client **/
                    Protocole msg = new Protocole();
                    msg.setName(SuperPongProtocole.CMD_CONNECT);

                    /** DATA RESPONSE IF OK **/
                    msg.setData(new Connection(true));
                    sendToClient(msg);

                    break;
                default:
                    writer.println("Huh? please use HELwP if you don't know what commands are available.");
                    writer.flush();
                    break;
            }
            writer.flush();
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
