package game;


import game.Item;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class ReduceOtherRaquet extends Item{

	public ReduceOtherRaquet(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
	}
	
	/*
	public ReduceOtherRaquet(int width, int height) {
		super(width, height);
	}*/
	
	public PhongMaterial color() {
		final PhongMaterial color = new PhongMaterial();
		color.setDiffuseColor(Color.YELLOW);
		return color;
	}
	
	/*public void execute(Game1v1 game) {
	
	}
	
	public void desexecute(Game1v1 game) {
	
	}*/
}
