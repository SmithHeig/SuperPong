package Game;

import game.Player;
import server.ClientHandler;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Classe gérant les joueurs qui veulent jouer en multijoueur
 * Choix d'utiliser des listes: Permet de mettre en place une liste d'attente si trop de partie en cours
 */
public class Matchmaking {
    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(Matchmaking.class.getName());

    /* ATTRIBUTS */
    private LinkedList<Player> game2players; // liste d'attente pour les partie 1v1
    private LinkedList<Player> game3players; // liste d'attente pour les partie 1v1v1
    private LinkedList<Player> game4players; // liste d'attente pour les partie 1v1v1
    private LinkedList<Player> game5players; // liste d'attente pour les parties 1v1v1v1
    private LinkedList<Player> game6players; // liste d'attente pour les parties 1v1v1v1v1
    private LinkedList<Player> game7players; // liste d'attente pour les parties 1v1v1v1v1v1
    private LinkedList<Player> game8players; // liste d'attetne pour les parties 1v1v1v1v1v1v1

    private LinkedList<Game> currentGames;


    /**
     * Constructeur de la classe gérant le matchmaking
     */
    public Matchmaking(){
        game2players = new LinkedList<>();
        game3players = new LinkedList<>();
        game4players = new LinkedList<>();
        game5players = new LinkedList<>();
        game6players = new LinkedList<>();
        game7players = new LinkedList<>();
        game8players = new LinkedList<>();

        currentGames = new LinkedList<>();
    }

    /**
     * Inscrit un jouer à la liste d'attente et tente de créer une partie si assez de joueur
     * @param player - joueur à rajouter
     */
    public synchronized void inscriptionGame2players(Player player){
        game2players.push(player);
        tryCreateGame2players();
    }

    /**
     * Essaie de créer une partie 1v1 avec les joueurs de la liste d'attente
     * Peut être appelé lorsqu'un joueur rejoint la liste d'attente ou à la fin d'une partie (place qui se libère)
     */
    public synchronized void  tryCreateGame2players() {
        if(game2players.size() >= 2){
            createGame(2,game2players);
        }
    }

    /**
     * Méthode pour créé une partie
     * @param nbPlayers nombre de joueur dans la partie
     * @param listPlayer référence vers la liste des joueurs en attentes.
     */
    private synchronized void createGame(int nbPlayers, LinkedList<Player> listPlayer){
        LinkedList<Player> players = new LinkedList<>();
        for(int i = 0; i < nbPlayers; ++i){
            players.add(listPlayer.pop());
        }

        currentGames.add(new Game(players));
    }

    /**
     * Permet d'enlevé la partie de la liste des parties en cours. Doit être appelé à la fin des parties
     * @param game - partie à supprimer
     */
    public synchronized void removeGame (Game game){
        currentGames.remove(game);
    }
}