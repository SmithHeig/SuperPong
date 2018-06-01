package game;

public class Ball {
	
	private double velocity;
	private double velocityX;
	private double velocityY;
	private double positionX;
	private double positionY;
	
	public Ball(int velocity, int velocityX, int velocityY, int positionX, int positionY) {
		this.velocity = velocity;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
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
		setVelocity(ball.getVelocityY());
		setPositionX(ball.getPositionX());
		setPositionY(ball.getPositionY());
	}
}
