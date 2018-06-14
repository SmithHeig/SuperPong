package game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import protocole.game.Item;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import protocole.game.ItemEffects;

public class IncreaseVelocity extends Item {
	private Timeline timeline;
	private static final double INCREASE_VELOCITY = 3;

	@JsonCreator
	public IncreaseVelocity(@JsonProperty("positionX") double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
		timeline = new Timeline();
		setColor(Color.GREY);
		name = ItemEffects.INCREASE_SPEED_BALL;
	}

	//TODO Devrait prendre des GameItems
	public void execute(Game game){
		//EXECUTION
		game.getBall().setVelocity(game.getBall().getVelocity() + INCREASE_VELOCITY);

		KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), ev -> {
			restore(game);
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(1);
		timeline.play();



	}
	
	public void restore(Game game){
		game.getBall().setVelocity(game.getBall().getVelocity() - INCREASE_VELOCITY);
	}
}
