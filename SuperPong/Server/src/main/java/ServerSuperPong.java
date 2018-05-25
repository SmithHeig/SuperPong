import server.Server;

import java.io.IOException;

/**
 * Classe de lancement du serveur
 */
public class ServerSuperPong {

    /** Constructeur **/
    public static void main(String[] argv) throws IOException{
        Server server = new Server();
        server.start();
    }


}
