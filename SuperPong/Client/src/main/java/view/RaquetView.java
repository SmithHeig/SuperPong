package view;

import game.Raquet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RaquetView extends Raquet {
	
	private Rectangle raquet;
	
	public RaquetView(double size, double positionY, double positionX) {
		super(size, positionY);
		
		raquet = new Rectangle(10, size, Color.WHITE);
		
		raquet.setLayoutX(positionX);
		raquet.setLayoutY(positionY);
	}
	
	public Rectangle getRaquet() {
		return raquet;
	}
	
	public void setSize(double size) {
		super.setSize(size);
		raquet.setHeight(size);
	}
	
	public void setPosition(double position) {
		super.setPosition(position);
		raquet.setLayoutY(position);
	}
}
