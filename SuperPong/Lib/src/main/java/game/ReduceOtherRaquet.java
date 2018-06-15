package game;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import protocole.game.Item;
import protocole.game.ItemEffects;

public class ReduceOtherRaquet extends Item{
	private Timeline timeline;
	private static final double DECREASE_SIZE = 50;
	private Player playerActif;

	public ReduceOtherRaquet(double positionX, double positionY, double duration) {
		super();
		setPositionX(positionX);
		setPositionY(positionY);
		setDuration(duration);
		timeline = new Timeline();
		setColor(Color.YELLOW);
		name = ItemEffects.DECREASE_OTHER_SIZE;
	}
	
	/*
	public ReduceOtherRaquet(int width, int height) {
		super(width, height);
	}*/

	
	public void execute(Game game) {
		//EXECUTION
		playerActif = game.getPlayers().get(1 - game.getPlayerLastTouch().getId());
		playerActif.getRaquet().setSize(playerActif.getRaquet().getSize() - DECREASE_SIZE);


		KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), ev -> restore(game));
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(1);
		timeline.play();
	}
	
	public void restore(Game game) {
		playerActif.getRaquet().setSize(playerActif.getRaquet().getSize() + DECREASE_SIZE);
	}
}
