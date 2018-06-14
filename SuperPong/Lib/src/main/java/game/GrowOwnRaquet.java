package game;

import protocole.game.Item;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import protocole.game.ItemEffects;


public class GrowOwnRaquet extends Item {
	private Timeline timeline;
	private static final double INCREASE_SIZE = 100;
	private Player playerActif;


	public GrowOwnRaquet(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
		timeline = new Timeline();
		setColor(Color.RED);
		name = ItemEffects.INCREASE_OWN_SIZE;
	}

	public synchronized void execute(Game game){
		//EXECUTION
		game.getPlayerLastTouch().getRaquet().setSize(game.getPlayerLastTouch().getRaquet().getSize() + INCREASE_SIZE);
		playerActif = game.getPlayerLastTouch();

		KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), ev -> {
			restore(game);
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(1);
		timeline.play();
	}

	public void restore(Game game){
		playerActif.getRaquet().setSize(playerActif.getRaquet().getSize() - INCREASE_SIZE);
	}
}
