package protocole.data.matchmaking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import game.Player;
import protocole.data.IData;

public class InscriptionMatchmaking implements IData {
    private Player player;
    private int nbPlayer;

    @JsonCreator
    public InscriptionMatchmaking(@JsonProperty(value="player") Player player, @JsonProperty(value="nbPlayer") int nbPlayer){
        this.player = player;
        this.nbPlayer = nbPlayer;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setNbPlayer(int nbPlayer) {
        this.nbPlayer = nbPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public int getNbPlayer() {
        return nbPlayer;
    }

    public String toString(){
        return "Inscription by " + player + " to a game with " + nbPlayer;
    }
}
