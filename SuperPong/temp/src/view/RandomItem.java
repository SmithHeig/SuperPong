package view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class RandomItem {
	
	private enum Items {
		INCREASE_VELOCITY,
		GROW_OWN_RAQUET,
		REDUCE_OTHER_RAQUET,
		FIRE_BALL
	}
	
	public Item generateItem(int width, int height) {
		int rand = (int) ((4)*Math.random());
		Items item = Items.values()[rand];
		System.out.println(rand);
		switch (item) {
			case INCREASE_VELOCITY:
				return new IncreaseVelocity(width, height);
			case GROW_OWN_RAQUET:
				return new GrowOwnRaquet(width, height);
			case REDUCE_OTHER_RAQUET:
				return new ReduceOtherRaquet(width, height);
			default:
				return new Fireball(width, height);
		}
	}
	
	
}
