package protocole.data.matchmaking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class InscriptionMatchmaking implements IData {
    private String username;
    private int nbPlayer;

    @JsonCreator
    public InscriptionMatchmaking(@JsonProperty(value="username") String username, @JsonProperty(value="nbPlayer") int nbPlayer){
        this.username = username;
        this.nbPlayer = nbPlayer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public String getUsername() {
        return username;
    }

    public int getNbPlayer() {
        return nbPlayer;
    }

    public String toString(){
        return "Inscription by " + username + " to a game with " + nbPlayer;
    }
}
