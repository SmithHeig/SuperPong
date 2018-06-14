package game;

import game.Item;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.util.Duration;


public class GrowOwnRaquet extends Item {
	private Timeline timeline;
	private static final double INCREASE_SIZE = 200;


	public GrowOwnRaquet(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
		timeline = new Timeline();
		setColor(Color.RED);
	}

	public void execute(Game game){
		//EXECUTION
		game.getPlayerLastTouch().getRaquet().setSize(game.getPlayerLastTouch().getRaquet().getSize() + INCREASE_SIZE);
		KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), ev -> {
			restore(game);
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(1);
		timeline.play();
	}

	public void restore(Game game){
		game.getPlayerLastTouch().getRaquet().setSize(game.getPlayerLastTouch().getRaquet().getSize() - INCREASE_SIZE);
	}
}
