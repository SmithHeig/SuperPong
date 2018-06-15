package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe gérant le serveur
 */
public class Server {
    /** LOGGER **/
    private final static Logger LOG = Logger.getLogger(Server.class.getName());

    /** ATTRIBUTS **/
    private String serverAddress;
    private int serverPort;
    private boolean shouldRun;

    private ServerSocket serverSocket;

    /*
     * The server maintains a list of client workers, so that they can be notified
     * when the server shuts down
     */
    private List<ClientWorker> clientWorkers = new CopyOnWriteArrayList();

    public Server() {
        initServer();
    }

    private void initServer(){
        try {
            // Récupération du fichier contenant les configuration server
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config/configServer.properties");

            Properties properties = new Properties();
            properties.load(inputStream);

            serverAddress = properties.getProperty("ServerGameAdress");
            serverPort = Integer.parseInt(properties.getProperty("ServerGamePort"));
            shouldRun = false;

            LOG.log(Level.INFO, "Init server on Port");
        } catch(IOException e){
            LOG.log(Level.SEVERE, "Can not open configServer.properties with exception: " + e.getMessage());
        }
    }

    public void start(){
        try {
            if (serverSocket == null|| !serverSocket.isBound()) {
                serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(serverPort));
            } else {
                LOG.log(Level.INFO, "Didnt create serverSocket");
            }

            LOG.log(Level.INFO, "Starting Server on port " + serverPort);

            Thread serverThread = new Thread(() -> {
                shouldRun = true;
                while (shouldRun) {
                    try {
                        Socket clientSocket = serverSocket.accept();

                        LOG.info("New client has arrived...");
                        ClientWorker worker = new ClientWorker(clientSocket, getClientHandler(), Server.this);
                        clientWorkers.add(worker);

                        LOG.info("Delegating work to client worker...");
                        Thread clientThread = new Thread(worker);
                        clientThread.start();
                    } catch (IOException ex) {
                        LOG.log(Level.SEVERE, "IOException in main server thread, exit: {0}", ex.getMessage());
                        shouldRun = false;
                    }
                }
            });

            serverThread.start();

        } catch(IOException e){
            LOG.log(Level.SEVERE, "Can not create serverSocket with exception: " + e.getMessage());
        }
    }

    private IClientHandler getClientHandler(){
        return new ClientHandler();
    }

    /**
     * This method is invoked by the client worker when it has completed its
     * interaction with the server (e.g. the user has issued the BYE command, the
     * connection has been closed, etc.)
     *
     * @param worker the worker which has completed its work
     */
    public void notifyClientWorkerDone(ClientWorker worker) {
        clientWorkers.remove(worker);
    }
}
