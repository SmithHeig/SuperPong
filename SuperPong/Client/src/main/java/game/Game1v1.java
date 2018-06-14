package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Displayer;
import view.BallView;
import view.FieldView;
import view.GameView;
import view.RaquetView;

import java.util.LinkedList;

public class Game1v1 implements Game{
	
	protected BallView ball;
	private Player player1;
	private Player player2;
	private Player playerLastTouch; // Derner joueur à avoir touché la ball
	private Label player1Score;
	private Label player2Score;
	private RaquetView raquetView1;
	private RaquetView raquetView2;
	protected LinkedList<RaquetView> raquetViews;
	protected double yplayer;
	protected FieldView field = new FieldView(1000,600);
	private Item item;

	protected Pane root;

	protected Timeline timeline;
	
	public Game1v1() {
		player1 = new Player("player1", 0, 0, new Raquet(field.getHeight()/ 2 - Raquet.getInitSize() / 2));
		player2 = new Player("player2", 0, 1, new Raquet(field.getHeight()/ 2 - Raquet.getInitSize() / 2));
		raquetView1 = new RaquetView(player1.getRaquet(),0);
		raquetView2 = new RaquetView(player2.getRaquet(), field.getWidth() - Raquet.getInitThickness());
		raquetViews = new LinkedList<>();
		raquetViews.add(raquetView1);
		raquetViews.add(raquetView2);
	}

	public void run(Stage primaryStage) throws Exception {
		Displayer.getInstance().setTitle("Game1v1");
		Displayer.getInstance().setScene(new Scene(createContent()));

		primaryStage.show();
		root.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				yplayer = me.getY();
			}
		});

		timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(Duration.millis(20), ev -> {
			gameUpdate();
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}


	protected Parent createContent() {
		root = field.printField();

		ball = new BallView(7, -1, 0, field.getWidth() / 2, field.getHeight() / 2);

		player1Score = new Label("0");
		player1Score.setTextFill(Color.WHITE);
		player1Score.setFont(new Font("Arial", 30));
		player1Score.setLayoutX(200);
		player1Score.setLayoutY(50);

		player2Score = new Label("0");
		player2Score.setTextFill(Color.WHITE);
		player2Score.setFont(new Font("Arial", 30));
		player2Score.setLayoutX(field.getWidth() - 200);
		player2Score.setLayoutY(50);


		root.getChildren().addAll(raquetView1.getView(), raquetView2.getView(), ball.getBall(), player1Score, player2Score);

		return root;
	}

	protected void gameUpdate() {
		double x = ball.getPositionX();
		double y = ball.getPositionY();

		double ANGLE_MIN = -45;
		double ANGLE_MAX = 45;
		double ANGLE_DELTA = ANGLE_MAX - ANGLE_MIN;

		// si le joueur touche la balle
		if (x <= 15 && y > player1.getRaquet().getPosition() && y < player1.getRaquet().getPosition() + player1.getRaquet().getSize()) {
			playerLastTouch = player1;
			double perCent = (y - player1.getRaquet().getPosition()) / player1.getRaquet().getSize();

			double alpha = ANGLE_DELTA * perCent + ANGLE_MIN;

			double rad = Math.toRadians(alpha);

			ball.setVelocityX(Math.cos(rad));
			ball.setVelocityY(Math.sin(rad));
		}

		ANGLE_MIN = -135;
		ANGLE_DELTA = 90;
		if (x >= field.getWidth() - 10 && y > player2.getRaquet().getPosition() && y < player2.getRaquet().getPosition() + player2.getRaquet().getSize()) {

			playerLastTouch = player2;
			double perCent = (y - player2.getRaquet().getPosition()) / player2.getRaquet().getSize();

			double alpha = -ANGLE_DELTA * perCent + ANGLE_MIN;

			double rad = Math.toRadians(alpha);

			ball.setVelocityX(Math.cos(rad));
			ball.setVelocityY(Math.sin(rad));
		}

		// le player 1 prend un goal
		if (x < 0) {
			player2.setPoints(player2.getPoints() + 1);
			player2Score.setText(String.valueOf(player2.getPoints()));
			ball.setPositionY(field.getHeight() / 2);
			ball.setPositionX(field.getWidth() / 2);
			/* Player2 gagne */
			if(player2.getPoints() >= 5){
				showWinner(player2);
			}
		}

		// le player 2 prend un goal
		if (x > field.getWidth()) {
			player1.setPoints(player1.getPoints() + 1);
			player1Score.setText(Integer.toString(player1.getPoints()));
			ball.setPositionY(field.getHeight() / 2);
			ball.setPositionX(field.getWidth() / 2);
			/* Player1 gagne */
			if(player1.getPoints() >= 5){

				showWinner(player1);
			}
		}

		// rebonds contre les murs haut et bas
		if (y <= 0 || y >= field.getHeight() - 5) ball.setVelocityY(ball.getVelocityY() * (-1));

		// mise à jour de la position de la balle
		ball.setPositionX(ball.getPositionX() + ball.getVelocity() * ball.getVelocityX());
		ball.setPositionY(ball.getPositionY() + ball.getVelocity() * ball.getVelocityY());


		// ia du bot
		if (x > field.getWidth() / 2 && player2.getRaquet().getPosition() + 30 > y) {
			if (player2.getRaquet().getPosition() > 0) {
				player2.getRaquet().setPosition(player2.getRaquet().getPosition() - 5);
				raquetView2.update();
			}
		}
		if (x > field.getWidth() / 2 && player2.getRaquet().getPosition() - 30 + player2.getRaquet().getSize() / 2 < y) {
			if (player2.getRaquet().getPosition() + player2.getRaquet().getSize() < field.getHeight()) {
				player2.getRaquet().setPosition(player2.getRaquet().getPosition() + 5);
				raquetView2.update();
			}
		}

//player
		if (player1.getRaquet().getPosition() + 30 > yplayer) {
			if (player1.getRaquet().getPosition() > 0) {
				player1.getRaquet().setPosition(player1.getRaquet().getPosition() - 5);
				raquetView1.update();
			}
		}
		if (player1.getRaquet().getPosition() - 30 + player1.getRaquet().getSize() / 2 < yplayer) {
			if (player1.getRaquet().getPosition() + player1.getRaquet().getSize() < field.getHeight()) {
				player1.getRaquet().setPosition(player1.getRaquet().getPosition() + 5);
				raquetView1.update();
			}
		}
	}

	protected void showWinner(Player winner){
		timeline.stop();
		Displayer.getInstance().showLocalMenu();
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Fin de la partie");
		alert.setHeaderText(winner.getUsername() + " a gagné!");
		// TODO à rendre plus générique et peu etre déplacer (super classse?)
		alert.setContentText(player1.getUsername() + ": " + player1.getPoints() + "\n" +
							 player2.getUsername() + ": " + player2.getPoints() + "\n");
		ButtonType buttonTypeOne = new ButtonType("Retour au menu"); // ajoute un bouton "Rejouer" à la boite de dialogue
		alert.getButtonTypes().add(buttonTypeOne);
		alert.show();

	}

	public Player getPlayerLastTouch(){
		return playerLastTouch;
	}

	public Ball getBall(){
		return ball;
	}

	public LinkedList<Player> getPlayers(){
		LinkedList<Player> players = new LinkedList<>();
		players.add(player1);
		players.add(player2);
		return players;
	}

}
