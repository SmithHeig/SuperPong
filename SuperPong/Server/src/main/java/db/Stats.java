package db;

public class Stats {
    private int nbWins;
    private int nbPlays;

    public Stats(int nbWins, int nbPlays){
        this.nbWins = nbWins;
        this.nbPlays = nbPlays;
    }

    public int getNbWins(){
        return nbWins;
    }

    public int getNbPlays(){
        return nbPlays;
    }

    public double getRatio(){
        return (double)nbWins / ((double)nbPlays - (double)nbWins);
    }
}
