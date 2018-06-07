package game;

public class Field {
    private int nbPlayer; // Nécessaire pour la création du terrain
    private double width;
    private double height;
    
    public Field(int nbPlayer, double width, double height) {
        this.nbPlayer = nbPlayer;
        this.width = width;
        this.height = height;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
}
