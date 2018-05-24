package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {
	
	private Circle ball;
	
	public Ball(int width, int height){
		ball = new Circle(5, Color.WHITE);
		setPositionX(width);
		setPositionY(height);
	}
	
	public Circle getBall(){
		return ball;
	}
	
	public void setPositionX(double pos){
		ball.setLayoutX(pos);
	}
	
	public void setPositionY(double pos){
		ball.setLayoutY(pos);
	}
	
	public double getPositionX() {
		return ball.getLayoutX();
	}
	
	public double getPositionY() {
		return ball.getLayoutY();
	}
}
