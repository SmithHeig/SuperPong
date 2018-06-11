package view;

import game.Field;
import game.Item;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

abstract public class ItemView extends Item {
	
	private Box box;
	
	public ItemView(Field field) {
		super(field);
		box = new Box(25, 25, 25);
		box.setLayoutX(getPositionX());
		box.setLayoutY(getPositionY());
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
	
	abstract public void execute();
	
	abstract public void desexecute();
	
}
