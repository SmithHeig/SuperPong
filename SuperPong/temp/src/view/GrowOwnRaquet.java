package view;

import game.Game1v1;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class GrowOwnRaquet extends Item {
	
	Raquet toGrow;
	
	public GrowOwnRaquet(int width, int height){
		super(width,height);
	}
	
	public PhongMaterial color(){
		final PhongMaterial color = new PhongMaterial();
		color.setDiffuseColor(Color.RED);
		return color;
	}
	
	public void execute(Game1v1 game){
		toGrow = game.PlayerlastTouchTheBall ? game.playerRaquet : game.bot;
		toGrow.setHeight(toGrow.getHeight()*2);
	}
	
	public void desexecute(Game1v1 game){
		toGrow.setHeight(toGrow.getHeight()/2);
	}
}
