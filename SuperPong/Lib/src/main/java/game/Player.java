package game;

/**
 * Classe représentant un joueur et sa raquette.
 */
public class Player {
    private int id;
    private String username; // nom du joueur
    private int points; // Nombre de point que le joueur à
    private Raquet raquet; // Raquette de l'utilisateur


    /**
     * Constructeur avec tous les paramètres
     * @param username - pseudo du joueur
     * @param points - nombre de point du joueur
     * @param position - position de la raquette du joueur
     */
    public Player(String username, int points, double position, int id){
        this.username = username;
        this.points = points;
        this.id = id;
        raquet = new Raquet(position);
    }

    /**
     * Constructeur par copie
     * @param player - player à copier
     */
    public Player(Player player){
        this.username = player.username;
        this.raquet = player.raquet;
        this.points = player.points;
    }

    /**
     * Constructeur avec paramètres parcielle
     * @param username - pseudo du joueur
     * @param position - position de la raquette du joueur
     */
    public Player(String username, double position){
        this(username, 0, position,0);
    }

    /* GETTER */
    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public int getId() {
        return id;
    }

    /* SETTER */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setId(int id) {
        this.id = id;
    }
}
