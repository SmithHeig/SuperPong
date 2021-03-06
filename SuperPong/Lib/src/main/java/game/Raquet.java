package game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe représentant une raquette
 */
@JsonIgnoreProperties({"raquet"})
public class Raquet {
    private double size; // Taille de la raquette
    private double position; // Position de la raquette (Vertical)
    

    /** CONSTANTE **/
    protected static final double INIT_SIZE = 100;
    protected static final double INIT_THICKNESS = 10;

    /**
     * Constructeur avec tous les paramètres
     * @param size - taille de la raquette
     * @param position - position de la raquette
     */
    
    public Raquet(double size, double position){
        this.size = size;
        this.position = position;
    }

    public Raquet(){
        this(100,300);
    }

    /**
     * Constructeur avec la position de la raquette et la taille par défaut
     * @param position - position de la raquette
     */
    public Raquet(double position){
        this(INIT_SIZE, position);
    }

    /**
     * Méthode pour mettre à jour la raquette grace à un autre objet raquette (reçu via transmission réseau par exemple)
     * @param raquet
     */
    public void update(Raquet raquet){
        setPosition(raquet.getPosition());
        setSize(raquet.getSize());
    }

    /* GETTER */
    public double getSize() {
        return size;
    }

    public double getPosition() {
        return position;
    }

    /* SETTER */
    public void setSize(double size) {
        this.size = size;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public static double getInitThickness(){
        return INIT_THICKNESS;
    }

    public static double getInitSize(){
        return INIT_SIZE;
    }
}
