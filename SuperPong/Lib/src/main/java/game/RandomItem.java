package game;

import java.util.Random;

public class RandomItem {
	private static Random random = new Random();
	
	private enum Items {
		INCREASE_VELOCITY,
		GROW_OWN_RAQUET,
		REDUCE_OTHER_RAQUET,
		//FIRE_BALL
	}
	

	public static Item generateItem(int fieldWidht, int fieldHeight) {
		int rand = random.nextInt(3) ; // TODO changer le 3 par le nombre d'item (constante ?)
		Items item = Items.values()[rand];
		int width = random.nextInt(fieldWidht);
		int height = random.nextInt(fieldHeight);
		System.out.println(rand);
		switch (item) {
			case INCREASE_VELOCITY:
				System.out.println("VELOCITY");
				return new IncreaseVelocity(width, height, 5000);
			case GROW_OWN_RAQUET:

				System.out.println("GROW");
				return new GrowOwnRaquet(width, height, 5000);
			case REDUCE_OTHER_RAQUET:
				System.out.println("REDUCE");
				return new ReduceOtherRaquet(width, height, 5000);
			default:
				System.out.println("NONE");
				return null;
		}
	}
	

}
