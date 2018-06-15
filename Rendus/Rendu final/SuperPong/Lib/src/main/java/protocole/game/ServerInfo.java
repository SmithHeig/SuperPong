package protocole.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import game.Ball;
import game.Player;
import protocole.data.IData;

import java.util.LinkedList;

public class ServerInfo implements IData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    ItemProtocole item = null;
    boolean isNewItem = false;
    boolean isTouch = false;
    LinkedList<Player> players;
    Ball ball;
    Boolean isFinised;

    public ServerInfo(){
    }

    /*public ServerInfo(@JsonProperty("players") LinkedList<Player> players, @JsonProperty("ball") Ball ball, @JsonProperty("isFinished")boolean isFinised){
        this.players = players;
        this.ball = ball;
        this.isFinised = isFinised;
        this.item = null;
    }*/

    /* GETTER */
    public LinkedList<Player> getPlayers() {
        return players;
    }

    public Ball getBall() {
        return ball;
    }

    @JsonProperty("isNewItem")
    public boolean isNewItem() {
        return isNewItem;
    }

    @JsonProperty("isTouch")
    public boolean isTouch(){return isTouch;}

    @JsonProperty(value="isFinished")
    public Boolean isFinised() {
        return isFinised;
    }

    public ItemProtocole getItem(){return item;}

    /* GETTER */
    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setFinised(Boolean finised) {
        this.isFinised = finised;
    }

    public void setItem(ItemProtocole item){this.item = item;}

    public void setTouch(boolean isTouch){this.isTouch = isTouch;}

    public void setNewItem(boolean newItem) {
        isNewItem = newItem;
    }
}
