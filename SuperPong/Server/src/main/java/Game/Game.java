package Game;

import game.Player;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Classe représentant une partie et gérant une partie en multijoueur
 */
public class Game implements Runnable{
    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(Game.class.getName());

    /* ATTRIBUTS */
    LinkedList<Player> player;

    /**
     * Contructeur
     * @param players liste de joueur participant à cette partie.
     */
    public Game(LinkedList<Player> players){
        this.player = players;
    }

    /**
     * Méthode exécuté par le thread. Gestion de la partie.
     */
    @Override
    public void run() {

    }
}
