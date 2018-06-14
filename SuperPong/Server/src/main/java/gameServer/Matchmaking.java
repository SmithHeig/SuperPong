package gameServer;

import game.Game;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Classe gérant les joueurs qui veulent jouer en multijoueur
 * Choix d'utiliser des listes: Permet de mettre en place une liste d'attente si trop de partie en cours
 */
public class Matchmaking {
    /* LOGGER */
    private final static Logger LOG = Logger.getLogger(Matchmaking.class.getName());

    /* INSTANCE */
    private final static Matchmaking instance = new Matchmaking();

    /* ATTRIBUTS */
    private LinkedList<PlayerServer> game2players; // liste d'attente pour les partie 1v1
    private LinkedList<PlayerServer> game3players; // liste d'attente pour les partie 1v1v1
    private LinkedList<PlayerServer> game4players; // liste d'attente pour les partie 1v1v1
    private LinkedList<PlayerServer> game5players; // liste d'attente pour les parties 1v1v1v1
    private LinkedList<PlayerServer> game6players; // liste d'attente pour les parties 1v1v1v1v1
    private LinkedList<PlayerServer> game7players; // liste d'attente pour les parties 1v1v1v1v1v1
    private LinkedList<PlayerServer> game8players; // liste d'attetne pour les parties 1v1v1v1v1v1v1

    private LinkedList<PlayerServer> game2playersWithItem; // liste d'attente pour les partie 1v1
    private LinkedList<PlayerServer> game3playersWithItem; // liste d'attente pour les partie 1v1v1
    private LinkedList<PlayerServer> game4playersWithItem; // liste d'attente pour les partie 1v1v1
    private LinkedList<PlayerServer> game5playersWithItem; // liste d'attente pour les parties 1v1v1v1
    private LinkedList<PlayerServer> game6playersWithItem; // liste d'attente pour les parties 1v1v1v1v1
    private LinkedList<PlayerServer> game7playersWithItem; // liste d'attente pour les parties 1v1v1v1v1v1
    private LinkedList<PlayerServer> game8playersWithItem; // liste d'attetne pour les parties 1v1v1v1v1v1v1

    private LinkedList<GameServer> currentGames;


    /**
     * Constructeur de la classe gérant le matchmaking
     */
    private Matchmaking(){
        game2players = new LinkedList<>();
        game3players = new LinkedList<>();
        game4players = new LinkedList<>();
        game5players = new LinkedList<>();
        game6players = new LinkedList<>();
        game7players = new LinkedList<>();
        game8players = new LinkedList<>();

        game2playersWithItem = new LinkedList<>();
        game3playersWithItem = new LinkedList<>();
        game4playersWithItem = new LinkedList<>();
        game5playersWithItem = new LinkedList<>();
        game6playersWithItem = new LinkedList<>();
        game7playersWithItem = new LinkedList<>();
        game8playersWithItem = new LinkedList<>();

        currentGames = new LinkedList<>();
    }

    public static Matchmaking getInstance(){
        return instance;
    }

    /**
     * Inscrit un jouer à la liste d'attente et tente de créer une partie si assez de joueur
     * @param player - joueur à rajouter
     */
    public synchronized void inscriptionGame2players(PlayerServer player){
        game2players.push(player);
        tryCreateGame2players();
    }

    public synchronized void inscriptionGame2playersWithItems(PlayerServer player){
        game2playersWithItem.push(player);
        tryCreateGame2playersWithItems();
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

    public synchronized void tryCreateGame2playersWithItems(){
        if(game2playersWithItem.size() >= 2){
            createGameWithItems(2,game2playersWithItem);
        }
    }

    /**
     * Méthode pour créé une partie
     * @param nbPlayers nombre de joueur dans la partie
     * @param listPlayer référence vers la liste des joueurs en attentes.
     */
    private synchronized void createGame(int nbPlayers, LinkedList<PlayerServer> listPlayer){
        LinkedList<PlayerServer> players = new LinkedList<>();
        for(int i = 0; i < nbPlayers; ++i){
            players.add(listPlayer.pop());
        }
        GameServer game = new GameServer(players);
        currentGames.add(game);
        game.run();
    }

    private synchronized void createGameWithItems(int nbPlayers, LinkedList<PlayerServer> listPlayer){
        LinkedList<PlayerServer> players = new LinkedList<>();
        for(int i = 0; i < nbPlayers; ++i){
            players.add(listPlayer.pop());
        }
        GameServerItems game = new GameServerItems(players);
        currentGames.add(game);
        game.run();
    }

    /**
     * Permet d'enlevé la partie de la liste des parties en cours. Doit être appelé à la fin des parties
     * @param game - partie à supprimer
     */
    public synchronized void removeGame (Game game){
        currentGames.remove(game);
    }
}
