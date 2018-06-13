package view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import game.Raquet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

@JsonIgnoreProperties({"raquet"})
public class RaquetView extends Raquet {
	
	private Rectangle raquet;
	
	public RaquetView(double size, double positionX, double positionY) {
		super(size, positionY);
		
		raquet = new Rectangle(10, size, Color.WHITE);
		
		raquet.setLayoutX(positionX);
		raquet.setLayoutY(positionY);
	}

	public RaquetView(double positionX, double positionY){
		super(positionY);
		raquet = new Rectangle(INIT_THICKNESS, super.getSize(), Color.WHITE);
		raquet.setLayoutX(positionX);
		raquet.setLayoutY(positionY);
	}
	
	public Rectangle getView() {
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
