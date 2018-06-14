package game;

import protocole.game.Item;
import protocole.game.ItemEffects;
import protocole.game.ItemProtocole;

import java.util.Random;

public class ItemFactory {
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
		int positionX = random.nextInt(fieldWidht);
		int positionY = random.nextInt(fieldHeight);
		switch (item) {
			case INCREASE_VELOCITY:
				return new IncreaseVelocity(positionX, positionY, 3000);
			case GROW_OWN_RAQUET:
				return new GrowOwnRaquet(positionX, positionY, 5000);
			case REDUCE_OTHER_RAQUET:
				return new ReduceOtherRaquet(positionX, positionY, 5000);
			default:
				System.out.println("NONE");
				return null;
		}
	}

	public static Item getItem(ItemProtocole itemProtocole){
		double posX = itemProtocole.getPositionX();
		double posY = itemProtocole.getPositionY();
		double duration = itemProtocole.getDuration();
		String name = itemProtocole.getItem();
		switch (name){
			case ItemEffects.INCREASE_SPEED_BALL:
				return new IncreaseVelocity(posX, posY, duration);
			case ItemEffects.INCREASE_OWN_SIZE:
				return new GrowOwnRaquet(posX,posY, duration);
			case ItemEffects.DECREASE_OTHER_SIZE:
				return new ReduceOtherRaquet(posX, posY, duration);
			default:
				System.out.println("WRONG ITEM NAMEs");
				return null;
		}
	}
	

}
