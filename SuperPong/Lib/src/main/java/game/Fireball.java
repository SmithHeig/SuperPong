package game;

import protocole.game.Item;
import javafx.scene.paint.Color;

public class Fireball extends Item {

	public Fireball(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
		setColor(Color.BLUE);
	}


	public void execute(Game game){
	
	}
	
	public void restore(Game game){
	
	}
}
