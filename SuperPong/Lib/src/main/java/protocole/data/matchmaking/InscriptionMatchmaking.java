package protocole.data.matchmaking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.data.IData;

public class InscriptionMatchmaking implements IData {
    private String username;
    private int nbPlayer;
    private boolean item;

    @JsonCreator
    public InscriptionMatchmaking(@JsonProperty(value="username") String username, @JsonProperty(value="nbPlayer") int nbPlayer, @JsonProperty(value="item") boolean item){
        this.username = username;
        this.nbPlayer = nbPlayer;
        this.item = item;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public void setItem(boolean item) {
        this.item = item;
    }

    public String getUsername() {
        return username;
    }


    public int getNbPlayer() {
        return nbPlayer;
    }

    @JsonProperty("item")
    public boolean withItems(){return item;}

    public String toString(){
        return "Inscription by " + username + " to a game with " + nbPlayer + " with items: " + item;
    }
}
