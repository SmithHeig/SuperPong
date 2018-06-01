package view;

import game.Game1v1;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class Fireball extends Item {
	
	public Fireball(int width, int height){
		super(width,height);
	}
	
	public PhongMaterial color(){
		final PhongMaterial color = new PhongMaterial();
		color.setDiffuseColor(Color.BLUE);
		return color;
	}
	
	public void execute(Game1v1 game){
	
	}
	
	public void desexecute(Game1v1 game){
	
	}
}
