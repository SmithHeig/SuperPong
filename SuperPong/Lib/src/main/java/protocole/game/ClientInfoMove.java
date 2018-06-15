package protocole.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import game.Player;
import protocole.data.IData;

public class ClientInfoMove implements IData {
    private Player player;

    @JsonCreator
    public ClientInfoMove(@JsonProperty("player") Player player){
        this.player = player;
    }

    /* GETTER */
    public Player getPlayer() {
        return player;
    }

    /* SETTER */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
