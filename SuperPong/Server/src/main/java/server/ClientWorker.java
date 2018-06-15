package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientWorker implements Runnable {
	/* LOGGER */
	private final static Logger LOG = Logger.getLogger(ClientWorker.class.getName());
	
	/* ATTRIBUTS */
	private Socket clientSocket;
	private IClientHandler handler;
	private Server server;
	private InputStream is;
	private OutputStream os;
	
	public ClientWorker(Socket clientSocket, IClientHandler handler, Server server) throws IOException {
		this.clientSocket = clientSocket;
		this.handler = handler;
		this.server = server;
		this.is = clientSocket.getInputStream();
		this.os = clientSocket.getOutputStream();
	}
	
	public void run() {
		try {
			handler.handleClientConnection(is, os);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error with matching the handler with exception : " + e.getMessage());
		} finally {
			server.notifyClientWorkerDone(this); // Notifie au serveur que le clientWorker se déconnecte
			try {
				clientSocket.close(); // Fermeture du clientSocket
			} catch (IOException e) {
				LOG.log(Level.INFO, e.getMessage());
			}
			try {
				is.close(); // Fermeture des l'inputStream
			} catch (IOException e) {
				LOG.log(Level.INFO, e.getMessage());
			}
			try {
				os.close(); // Fermeture de l'outputStream
			} catch (IOException e) {
				LOG.log(Level.INFO, e.getMessage());
			}
		}
	}
}
