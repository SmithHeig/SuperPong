package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Raquet {
	
	private int height;
	private Rectangle raquet;
	
	public Raquet(int posX, int posY, int height){
		this.height = height;
		raquet = new Rectangle(10,height, Color.WHITE);
		
		raquet.setLayoutX(posX);
		raquet.setLayoutY(posY);
	}
	
	public Rectangle getRaquet() {
		return raquet;
	}
	
	public Rectangle createPlayer(){
		return raquet;
	}
	
	public int getHeight(){
		return height;
	}
	
	public double getPositionX(){
		return raquet.getLayoutX();
	}
	
	public double getPositionY(){
		return raquet.getLayoutY();
	}
	
	public void setPositionX(double pos){
	raquet.setLayoutX(pos);
	}
	
	public void setPositionY(double pos){
		raquet.setLayoutY(pos);
	}
}
