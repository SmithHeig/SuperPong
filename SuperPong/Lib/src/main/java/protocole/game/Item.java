package protocole.game;

import com.fasterxml.jackson.annotation.*;
import game.*;
import javafx.scene.paint.Color;

abstract public class Item {

	protected double positionX;
	protected double positionY;
	protected double duration;
	private Color color;
	protected String name;

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

	public synchronized boolean isTouch(Ball ball){
		if(ball != null) {
			if (ball.getPositionX() >= positionX- 25 && ball.getPositionX() <= positionX + 50 &&
					ball.getPositionY() >= positionY - 25 && ball.getPositionY() <= positionY + 50) {
				return true;
			}
		}
		return false;
	}

	public abstract void execute(Game game);

	public abstract void restore(Game game);

	public String getName(){return name;}

	public void setName(String name){this.name = name;}
}
