package protocole.data.stats;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class Stats implements IData {
    private int nbWins;
    private int nbPlays;

    @JsonCreator
    public Stats(@JsonProperty("nbWins") int nbWins,@JsonProperty("nbPlays") int nbPlays){
        this.nbWins = nbWins;
        this.nbPlays = nbPlays;
    }

    public void setNbWins(int nbWins) {
        this.nbWins = nbWins;
    }

    public void setNbPlays(int nbPlays) {
        this.nbPlays = nbPlays;
    }

    public int getNbWins(){
        return nbWins;
    }

    public int getNbPlays(){
        return nbPlays;
    }
}
