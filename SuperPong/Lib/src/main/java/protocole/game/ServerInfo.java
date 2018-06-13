package protocole.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import game.Ball;
import game.Item;
import game.Player;
import protocole.data.IData;

import java.util.LinkedList;

public class ServerInfo implements IData {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Item item = null;
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


    @JsonProperty(value="isFinished")
    public Boolean isFinised() {
        return isFinised;
    }

    public Item getItem(){return item;}

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

    public void setItem(Item item){this.item = item;}
}
