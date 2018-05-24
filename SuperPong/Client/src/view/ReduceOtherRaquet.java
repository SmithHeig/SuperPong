package view;

import game.Game1v1;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class ReduceOtherRaquet extends Item {
	
	Raquet toReduce;
	
	public ReduceOtherRaquet(int width, int height) {
		super(width, height);
	}
	
	public PhongMaterial color() {
		final PhongMaterial color = new PhongMaterial();
		color.setDiffuseColor(Color.YELLOW);
		return color;
	}
	
	public void execute(Game1v1 game) {
		toReduce = game.PlayerlastTouchTheBall ? game.bot : game.playerRaquet;
		toReduce.setHeight(toReduce.getHeight() / 2);
	}
	
	public void desexecute(Game1v1 game) {
		toReduce.setHeight(toReduce.getHeight() * 2);
	}
}
