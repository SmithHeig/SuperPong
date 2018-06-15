package protocole.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemProtocole{
    protected double positionX;
    protected double positionY;
    protected double duration;
    private String name;

    @JsonCreator
    public ItemProtocole(@JsonProperty("positionX") double positionX, @JsonProperty("positionY") double positionY, @JsonProperty("duration") double duration, @JsonProperty("name") String name) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.duration = duration;
        this.name = name;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setItem(String name) {
        this.name = name;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getDuration() {
        return duration;
    }

    public String getItem() {
        return name;
    }
}
