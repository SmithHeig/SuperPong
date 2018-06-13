package game;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
	@JsonSubTypes.Type(value = GrowOwnRaquet.class, name = "GrowOnwRaquet"),
	@JsonSubTypes.Type(value = IncreaseVelocity.class, name = "IncreaseVelocity"),
	@JsonSubTypes.Type(value = ReduceOtherRaquet.class, name = "IncreaseOtherRaquet")
})
abstract public class Item {

	private double positionX;
	private double positionY;
	private double duration;

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

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
}
