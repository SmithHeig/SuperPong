package view;

import game.Ball;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BallView extends Ball{
	
	private Circle ball;
	
	public BallView(int velocity, int velocityX, int velocityY, int positionX, int positionY) {
		super(velocity, velocityX, velocityY, positionX, positionY);
		ball = new Circle(5, Color.WHITE);
		ball.setLayoutY(positionX);
		ball.setLayoutY(positionY);
	}
	
	public Circle getBall(){
		return ball;
	}
	
	public void setPositionX(double pos){
		super.setPositionX(pos);
		ball.setLayoutX(getPositionX());
	}
	
	public void setPositionY(double pos){
		super.setPositionY(pos);
		ball.setLayoutY(getPositionY());
	}
	
	
}
