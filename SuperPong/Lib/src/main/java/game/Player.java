package game;

/**
 * Classe représentant un joueur et sa raquette.
 */
public class Player {
    private String username; // nom du joueur
    private int points; // Nombre de point que le joueur à
    private Raquet raquet; // Raquette de l'utilisateur


    /**
     * Constructeur avec tous les paramètres
     * @param username - pseudo du joueur
     * @param points - nombre de point du joueur
     * @param position - position de la raquette du joueur
     */
    public Player(String username, int points, double position, Raquet raquet){
        this.username = username;
        this.points = points;
        this.raquet = raquet;
    }

    /**
     * Constructeur avec paramètres parcielle
     * @param username - pseudo du joueur
     * @param position - position de la raquette du joueur
     */
    public Player(String username, double position, Raquet raquet){
        this(username, 0, position, raquet);
    }

    /* GETTER */
    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }
    
    public Raquet getRaquet() {
        return raquet;
    }
    
    /* SETTER */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
