package game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ball {
	
	private double velocity;
	private double velocityX;
	private double velocityY;
	private double positionX;
	private double positionY;
	
	/* CONSTRUCTEUR */
	@JsonCreator
	public Ball(@JsonProperty("velocity")double velocity, @JsonProperty("velocityX")double velocityX,@JsonProperty("velocityY") double velocityY, @JsonProperty("positionX")double positionX, @JsonProperty("positionY")double positionY) {
		this.velocity = velocity;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	/* GETTER */
	public double getVelocity() {
		return velocity;
	}
	
	public double getVelocityX() {
		return velocityX;
	}
	
	public double getVelocityY() {
		return velocityY;
	}
	
	public double getPositionX() {
		return positionX;
	}
	
	public double getPositionY() {
		return positionY;
	}
	
	/* SETTER */
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	
	/**
	 * Mise à jour de la balle par rapport à une balle passée en paramètre
	 * @param ball la balle sur laquelle se mettre à jour
	 */
	public void update(Ball ball){
		setVelocity(ball.getVelocity());
		setVelocityX(ball.getVelocityX());
		setVelocityY(ball.getVelocityY());
		setPositionX(ball.getPositionX());
		setPositionY(ball.getPositionY());
	}
}
