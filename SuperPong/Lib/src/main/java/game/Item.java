package game;

abstract public class Item {
	
	private double positionX;
	private double positionY;
	
	public Item(Field field) {
		
		positionX = 200 + (Math.random() * (int) (field.getWidth() - 200));
		positionY = 25 + (Math.random() * (int) (field.getHeight() - 25));
		
	}
	
	public double getPositionX() {
		return positionX;
	}
	
	public double getPositionY() {
		return positionY;
	}
}
