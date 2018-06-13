package game;

import game.Item;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class Fireball extends Item {

	public Fireball(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
	}

	public PhongMaterial color(){
		final PhongMaterial color = new PhongMaterial();
		color.setDiffuseColor(Color.BLUE);
		return color;
	}
	
	/*public void execute(Game game){
	
	}
	
	public void desexecute(Game1v1 game){
	
	}*/
}
