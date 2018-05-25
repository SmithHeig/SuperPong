package network;

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
import java.net.ProtocolException;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Classe gérant la conenction avec le serveur et la communication **/
public class ServerManager {

    /** LOGGER **/
    private final static Logger LOG = Logger.getLogger(ServerManager.class.getName());

    /** OBJECT MAPPER **/
    private static final ObjectMapper JSONobjectMapper = new ObjectMapper();

    /** INSTANCE **/
    private static ServerManager instance;

    /** ATTRIBUTS **/
    Socket clientSocket;
    BufferedReader reader;
    PrintWriter writer;

    /**
     * Constructeur privé (Singleton)
     */
    private ServerManager(){
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
     * @param username - Username du client
     * @param pwd - password du client
     * @return true if have been connected
     */
    public boolean connect(String username, String pwd){
        try {
            /** INITIALISATION CONNECTION **/
            InputStream in = getClass().getClassLoader().getResourceAsStream("config/configServer.properties");
            Properties properties = new Properties();
            properties.load(in);
            String serverAddr = properties.getProperty("serverSuperPongAddress");
            int serverPort = Integer.parseInt(properties.getProperty("serverSuperPongPort"));
            LOG.log(Level.INFO, "Try to connect to server " + serverAddr + " by port " + serverPort);
            clientSocket = new Socket(serverAddr, serverPort);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            /** LOGIN **/
            Login login = new Login(username, pwd);
            Protocole msg = new Protocole();
            msg.setName(SuperPongProtocole.CMD_CONNECT);
            msg.setData(login);

            /** SENDING **/
            sendMessageToServer(msg);

            /** Attente de la réponse du serveur **/
            Protocole responseServer = readMsgFromServer();

            if(responseServer.getName() == SuperPongProtocole.CMD_CONNECT){
                /** Test si la connexion à été accepté **/
                Connection data = (Connection) responseServer.getData();
                if(data.getConnected()){
                    LOG.log(Level.INFO, "User is connected to server");
                    return true; // Connection possible
                } else {
                    LOG.log(Level.INFO, "User have been refused connection");
                }
            } else {
                LOG.log(Level.SEVERE, "wrong response from server");
            }
        } catch (IOException e){
            LOG.log(Level.SEVERE, e.getMessage());
        }
        return false; // Echec de la connection
    }

    /**
     * Permet de ce déconnecter du serveur
     */
    public void disconnect(){
        // TODO
    }

    public void play(){
        // TODO
    }

    /**
     * Fonction permettant d'envoyé un msg au server
     * @param msg - msg à envoyé sous forme de protocole
     */
    private void sendMessageToServer(Protocole msg){
        try {
            LOG.log(Level.INFO,"SERVER: Sending msg : " + msg.toString());
            writer.print(JSONobjectMapper.writeValueAsString(msg));
            writer.flush();
        } catch(JsonProcessingException e){
            LOG.log(Level.SEVERE, "Can not serialize msg with exception : " + e.getMessage());
        }
    }

    /**
     * Permet de lire la réponse du server et de la sérialisé dans la classe "Protocole"
     * @return le messag du serveur (protocole)
     */
    private Protocole readMsgFromServer(){
        try {
            String msgJson = reader.readLine();
            Protocole msg = JSONobjectMapper.readValue(msgJson, Protocole.class);
            LOG.log(Level.INFO, "SERVER: Received msg : " + msgJson);
            return msg;
        } catch(IOException e){
            LOG.log(Level.SEVERE, "Can not read server msg with excpetion : " + e.getMessage());
        }
        return null;
    }

}
