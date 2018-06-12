package protocole.data.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class ChangeStats implements IData {
    private String username;
    private int nbWins;
    private int nbPlays;

    @JsonCreator
    public ChangeStats(@JsonProperty("username") String username, @JsonProperty("nbWins") int nbWins, @JsonProperty("nbPlays") int nbPlays){
        this.username = username;
        this.nbPlays = nbPlays;
        this.nbWins = nbWins;
    }

    public int getNbWins() {
        return nbWins;
    }

    public int getNbPlays() {
        return nbPlays;
    }

    public String getUsername(){return username;}

    public void setNbWins(int nbWins) {
        this.nbWins = nbWins;
    }

    public void setNbPlays(int nbPlays) {
        this.nbPlays = nbPlays;
    }

    public void setUsername(String username){this.username = username;}
}
