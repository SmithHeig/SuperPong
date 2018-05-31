package network;

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
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Classe gérant la conenction avec le serveur et la communication **/
public class ServerManager {

    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(ServerManager.class.getName());

    /* OBJECT MAPPER */
    private static final ObjectMapper JSONobjectMapper = new ObjectMapper();
    static {
        JSONobjectMapper.registerSubtypes(new NamedType(LoginConfirmation.class, "connection"));
        JSONobjectMapper.registerSubtypes(new NamedType(Login.class, "Login"));
    }
    /* INSTANCE */
    private static ServerManager instance;

    /* ATTRIBUTS */
    Socket clientSocket;
    BufferedReader reader;
    PrintWriter writer;
    String username;

    boolean isConnected;

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
     * @param _username - Username du client
     * @param pwd - password du client
     * @return true if have been connected
     */
    public boolean connect(String _username, String pwd){
        try {
            this.username = _username;

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
                if(data.getConnected()){
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

    /*
     * Permet de ce déconnecter du serveur
     */
    public boolean disconnect(){
        /* DECONNECTION */
        Protocole msg = new Protocole(SuperPongProtocole.CMD_DISCONNECT, new Disconnection(username));

        sendMessageToServer(msg);

        /* Reponse du serveur */
        Protocole responseServer = readMsgFromServer();

        if(responseServer.getName().equals(SuperPongProtocole.CMD_DISCONNECT)) {
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

    public void play(){
        // TODO
    }

    /**
     * Retourne si le client est connecté ou non
     * @return
     */
    public boolean isConnected(){
        return isConnected;
    }

    /**
     * Fonction permettant d'envoyé un msg au server
     * @param msg - msg à envoyé sous forme de protocole
     */
    private void sendMessageToServer(Protocole msg){
        try {
            String jsonMsg = JSONobjectMapper.writeValueAsString(msg);
            LOG.log(Level.INFO,"SERVER: Sending msg : " + jsonMsg);
            writer.print(jsonMsg + "\r\n");
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
