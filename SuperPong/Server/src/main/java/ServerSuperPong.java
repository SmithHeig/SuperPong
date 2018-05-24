import server.Server;

/**
 * Classe de lancement du serveur
 */
public class ServerSuperPong {

    /** Constructeur **/
    public static void main(String[] argv){
        Server server = new Server();
        server.start();
    }


}
