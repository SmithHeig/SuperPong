package game;

import com.fasterxml.jackson.annotation.*;
import javafx.scene.paint.Color;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = GrowOwnRaquet.class, name = "GrowOnwRaquet"),
	@JsonSubTypes.Type(value = IncreaseVelocity.class, name = "IncreaseVelocity"),
	@JsonSubTypes.Type(value = ReduceOtherRaquet.class, name = "IncreaseOtherRaquet")
})
abstract public class Item {

	protected double positionX;
	protected double positionY;
	protected double duration;
	private Color color;

	public Item(){
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

	public Color getColor(){return color;}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public void setColor(Color color){this.color = color;}

	public abstract void execute(Game game);

	public abstract void restore(Game game);
}
