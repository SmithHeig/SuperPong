package view;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import game.Raquet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class RaquetView {
	@JsonIgnore
	private Raquet raquet;
	private Rectangle view;

	public RaquetView(Raquet raquet , double positionX) {
		this.raquet = raquet;
		view = new Rectangle(10, raquet.getSize(), Color.WHITE);
		
		view.setLayoutX(positionX);
		view.setLayoutY(raquet.getPosition());
	}

	public void update(){
		view.setLayoutY(raquet.getPosition());
	}

	public Rectangle getView(){ return view;}
}
