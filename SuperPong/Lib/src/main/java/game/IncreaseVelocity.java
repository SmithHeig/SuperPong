package game;

import game.Item;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class IncreaseVelocity extends Item {

	public IncreaseVelocity(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
	}

	/*
        public IncreaseVelocity(int width, int height){
            super(width,height);
        }
        */
	public PhongMaterial color(){
		final PhongMaterial color = new PhongMaterial();
		color.setDiffuseColor(Color.DARKGREY);
		color.setSpecularColor(Color.GREY);
		return color;
	}
	
	/*public void execute(Game1v1 game){
	
	}
	
	public void desexecute(Game1v1 game){
	
	}*/
}
