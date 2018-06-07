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

    @JsonCreator
    public ServerInfo(@JsonProperty("players") LinkedList<Player> players, @JsonProperty("ball") Ball ball){
        this.players = players;
        this.ball = ball;
    }

    /* GETTER */
    public LinkedList<Player> getPlayers() {
        return players;
    }

    public Ball getBall() {
        return ball;
    }

    /* GETTER */
    public void setPlayers(LinkedList<Player> players) {
        this.players = players;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }
}
