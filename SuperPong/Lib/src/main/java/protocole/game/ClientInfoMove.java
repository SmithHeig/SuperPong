package protocole.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import game.Ball;
import protocole.data.IData;

public class ClientInfoMove implements IData {
    private double position; // Position horizontal du joueur
    private Ball ball;
    private String username;

    @JsonCreator
    public ClientInfoMove(@JsonProperty("position") double position, @JsonProperty("ball") Ball ball, @JsonProperty("username") String username){
        this.position = position;
        this.ball = ball;
        this.username = username;
    }

    /* GETTER */
    public double getPosition() {
        return position;
    }

    public Ball getBall() {
        return ball;
    }

    public String getUsername() {
        return username;
    }

    /* SETTER */
    public void setPosition(double position) {
        this.position = position;
    }

    public void setBall(Ball ball){
        this.ball = ball;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
