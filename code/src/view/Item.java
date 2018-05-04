package view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

public class Item {
	
	Box box;
	Items item;
	
	public Item(){
		final PhongMaterial redMaterial = new PhongMaterial();
		redMaterial.setSpecularColor(Color.GREEN);
		redMaterial.setDiffuseColor(Color.LIGHTGREEN);
		
		box = new Box(25, 25, 25);
		box.setLayoutX(100);
		box.setLayoutY(100);
		Rotate rxBox = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate ryBox = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		Rotate rzBox = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
		rxBox.setAngle(30);
		ryBox.setAngle(50);
		rzBox.setAngle(30);
		box.setMaterial(redMaterial);
		
		box.getTransforms().addAll(rxBox, ryBox, rzBox);
	}
	
	public enum Items{
		INCREASE_VELOCITY,
		GROW_OWN_RAQUET,
		REDUCE_OTHER_RAQUET,
		FIRE_BALL
	}
	
	
	public Box getBox() {
		return box;
	}
}
