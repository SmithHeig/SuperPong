package game;

import game.Item;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.util.Duration;


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
	}

	public synchronized void execute(Game game){
		//EXECUTION
		System.out.println("INCREASE SIZE: old size: " + game.getPlayerLastTouch().getRaquet().getSize());
		game.getPlayerLastTouch().getRaquet().setSize(game.getPlayerLastTouch().getRaquet().getSize() + INCREASE_SIZE);
		System.out.println("New size: " + game.getPlayerLastTouch().getRaquet().getSize());
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
