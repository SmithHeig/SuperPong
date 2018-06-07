package Game;

import game.Ball;
import game.Field;
import game.Player;
import protocole.Protocole;
import protocole.SuperPongProtocole;
import protocole.data.matchmaking.GameJoin;
import protocole.data.matchmaking.InscriptionMatchmaking;
import protocole.mapper.JsonMapper;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe représentant une partie et gérant une partie en multijoueur
 */
public class Game implements Runnable{
    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(Game.class.getName());

    /* ATTRIBUTS */
    private LinkedList<PlayerServer> players;
    private Ball ball;
    private Field field;

    /**
     * Contructeur
     * @param _players liste de joueur participant à cette partie.
     */
    public Game(LinkedList<PlayerServer> _players){
        for(PlayerServer player : _players){
            players.add(player);
        }
        notifyPlayerGameJoin();
    }

    /**
     * Méthode exécuté par le thread. Gestion de la partie.
     */
    @Override
    public void run() {

    }

    private void notifyPlayerGameJoin(){
        for(int i = 0; i < players.size(); ++i){
            Protocole msg = new Protocole(SuperPongProtocole.CMD_INSCRIPTION_GAME, new GameJoin(true, i));
            sendMessage(msg, players.get(i).getWriter());
        }
    }

    /**
     * Méthode qui notifie aux joueurs: l'état de la balle et des autres joueurs
     */
    private void notifyPlayers(){

    }

    private void sendMessage(Protocole msg, PrintWriter writer){
        String msgJson = JsonMapper.getInstance().convertToString(msg);
        LOG.log(Level.INFO, "SERVER: Send msg : " + msgJson);
        writer.println(msgJson + "\n");
        writer.flush();
    }
}
