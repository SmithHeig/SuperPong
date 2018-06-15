package game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe représentant un joueur et sa raquette.
 */
public class Player {
    private int id;
    private String username; // nom du joueur
    private int points; // Nombre de point que le joueur à
    protected Raquet raquet; // Raquette de l'utilisateur


    /**
     * Constructeur avec tous les paramètres
     * @param username - pseudo du joueur
     * @param points - nombre de point du joueur
     */
    @JsonCreator
    public Player(@JsonProperty("username") String username, @JsonProperty("points")int points, @JsonProperty("id")int id, @JsonProperty("raquet")Raquet raquet){
        this.username = username;
        this.points = points;
        this.id = id;
        this.raquet = raquet;
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

    public Player(String username){
        this(username,0,0,new Raquet());
    }

   
    /**
     * Met à jour la position
     * @param player
     */
    public void update(Player player){
        raquet.setPosition(player.getRaquet().getPosition());
        raquet.setSize(player.getRaquet().getSize());
        this.points = player.getPoints();
    }

    public void softUpdate(Player player){
        this.points = player.getPoints();
        raquet.setSize(player.getRaquet().getSize());
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

    public void setRaquet(Raquet raquet) {this.raquet = raquet;}
}
