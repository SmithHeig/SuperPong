package protocole.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import game.Ball;
import game.Player;
import protocole.data.IData;

import java.util.LinkedList;

public class ServerInfo implements IData {
    private LinkedList<Player> players;
    private Ball ball;
    private Boolean isFinised;

    @JsonCreator
    public ServerInfo(@JsonProperty("players") LinkedList<Player> players, @JsonProperty("ball") Ball ball, @JsonProperty("isFinished") boolean isFinised){
        this.players = players;
        this.ball = ball;
        this.isFinised = isFinised;
    }

    /* GETTER */
    public LinkedList<Player> getPlayers() {
        return players;
    }

    public Ball getBall() {
        return ball;
    }


    @JsonProperty(value="isConnected")
    public Boolean isFinised() {
        return isFinised;
    }

    /* GETTER */
    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setFinised(Boolean finised) {
        isFinised = finised;
    }
}
