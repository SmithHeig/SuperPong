package game;

import game.Item;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.util.Duration;

public class IncreaseVelocity extends Item {
	private Timeline timeline;
	private static final double INCREASE_VELOCITY = 7;

	public IncreaseVelocity(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
		timeline = new Timeline();
		setColor(Color.GREY);
	}

	//TODO Devrait prendre des GameItems
	public void execute(Game game){
		//EXECUTION
		game.getBall().setVelocity(game.getBall().getVelocityX() + INCREASE_VELOCITY);

		KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), ev -> {
			restore(game);
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(1);
		timeline.play();



	}
	
	public void restore(Game game){
		game.getBall().setVelocity(game.getBall().getVelocityX() - INCREASE_VELOCITY);
	}
}
