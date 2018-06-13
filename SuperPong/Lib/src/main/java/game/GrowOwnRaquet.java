package game;

import game.Item;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;


public class GrowOwnRaquet extends Item {


	public GrowOwnRaquet(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
	}

	public PhongMaterial color(){
		final PhongMaterial color = new PhongMaterial();
		color.setDiffuseColor(Color.RED);
		return color;
	}
	
	/*public void execute(Game1v1 game){
	
	}
	
	public void desexecute(Game1v1 game){
	
	}*/
}
