package view;

import game.Game1v1;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

abstract public class Item {
	
	Box box;
	
	public Item(int width, int height) {
		
		box = new Box(25, 25, 25);
		box.setLayoutX(100 + (Math.random() * (width- 200)));
		box.setLayoutY(25 + (Math.random() * (height- 25)));
		Rotate rxBox = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate ryBox = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		Rotate rzBox = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
		rxBox.setAngle(30);
		ryBox.setAngle(50);
		rzBox.setAngle(30);
		box.setMaterial(color());
		
		box.getTransforms().addAll(rxBox, ryBox, rzBox);
		
	}
	
	public Box getBox() {
		return box;
	}
	
	public abstract PhongMaterial color();
	
	abstract public void execute(Game1v1 game);
	
	abstract public void desexecute(Game1v1 game);
	
}
