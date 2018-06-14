package game;


import game.Item;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.util.Duration;

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
	}
	
	/*
	public ReduceOtherRaquet(int width, int height) {
		super(width, height);
	}*/

	
	public void execute(Game game) {
		//EXECUTION
		playerActif = game.getPlayers().get(1 - game.getPlayerLastTouch().getId()); // TODO Fonction que pour 1v1
		System.out.println("REDUCE OTHER RAQUET: player: " + playerActif.getUsername() + " ");
		playerActif.getRaquet().setSize(playerActif.getRaquet().getSize() - DECREASE_SIZE);


		KeyFrame keyFrame = new KeyFrame(Duration.millis(duration), ev -> {
			restore(game);
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(1);
		timeline.play();
	}
	
	public void restore(Game game) {
		playerActif.getRaquet().setSize(playerActif.getRaquet().getSize() + DECREASE_SIZE);
	}
}
