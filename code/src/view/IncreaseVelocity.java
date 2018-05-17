package view;

import game.Game1v1;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import model.Ball;

public class IncreaseVelocity extends Item {
	
	public IncreaseVelocity(int width, int height){
		super(width,height);
	}
	
	public PhongMaterial color(){
		final PhongMaterial color = new PhongMaterial();
		color.setDiffuseColor(Color.DARKGREY);
		color.setSpecularColor(Color.GREY);
		System.out.println("lol");
		return color;
	}
	
	public void execute(Game1v1 game){
		game.VELOCITY += 3;
	}
	
	public void desexecute(Game1v1 game){
		game.VELOCITY -= 3;
	}
}
