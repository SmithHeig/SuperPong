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
import model.Field;
import network.ServerManager;
import protocole.game.ServerInfo;
import view.BallView;
import view.RandomItem;
import view.RaquetView;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class Game1v1Network {
	
	private BallView ball;
	private Player player1;
	private Player player2;
	private Label player1Score;
	private Label player2Score;
	private Player myself;
	private Player other;
	private  int myselfID;
	private double yplayer;
	
	private final int PLAYER_RAQUET_SIZE = 100;
	private Pane root;
	private Item item = null;
	private final int WIDTH = 1000, HEIGHT = 600;
	
	private RandomItem randomItem = new RandomItem();
	private long timerTime = 1;
	private boolean estTouche = false;
	
	public Game1v1Network(LinkedList<Player> players, int myselfID) {
		this.myselfID = myselfID;
		
		player1 = new Player("player1", 0, 0, new RaquetView(PLAYER_RAQUET_SIZE, HEIGHT / 2 - PLAYER_RAQUET_SIZE / 2, 0));
		player2 = new Player("player2", 0, 1, new RaquetView(PLAYER_RAQUET_SIZE, HEIGHT / 2 - PLAYER_RAQUET_SIZE / 2, WIDTH - 10));
		
		if (myselfID == 0) {
			myself = player1;
			other = player2;
		} else {
			myself = player2;
			other = player1;
		}
	}
	
	public void run(Stage primaryStage) throws Exception {
		Displayer.getInstance().setTitle("Game1v1");
		Displayer.getInstance().setScene(new Scene(createContent()));

		root.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				yplayer = me.getY();
			}
		});
	}
	
	
	private Parent createContent() {
		Field field = new Field(WIDTH, HEIGHT);
		root = field.printField();
		
		ball = new BallView(7, -1, 0, WIDTH / 2, HEIGHT / 2);
		
		player1Score = new Label("0");
		player1Score.setTextFill(Color.WHITE);
		player1Score.setFont(new Font("Arial", 30));
		player1Score.setLayoutX(200);
		player1Score.setLayoutY(50);
		
		player2Score = new Label("0");
		player2Score.setTextFill(Color.WHITE);
		player2Score.setFont(new Font("Arial", 30));
		player2Score.setLayoutX(WIDTH - 200);
		player2Score.setLayoutY(50);
		
		root.getChildren().addAll(((RaquetView) player1.getRaquet()).getRaquet(), ((RaquetView) player2.getRaquet()).getRaquet(), ball.getBall(), player1Score, player2Score);
		
		Timer time = new Timer();

		TimerTask timerTask1 = new TimerTask() {
			@Override
			public void run() {
				gameUptate();
			}
		};
		time.scheduleAtFixedRate(timerTask1, 20, 20);
		Timeline timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(Duration.millis(100), ev -> {
			ServerInfo serverInfo = ServerManager.getInstance().receivedGameInfos();
			if(!serverInfo.isFinised()) {
				ball.update(serverInfo.getBall());

				other.update(serverInfo.getPlayers().get(1 - myselfID));
				player1Score.setText(String.valueOf(serverInfo.getPlayers().get(0).getPoints()));
				player2Score.setText(String.valueOf(serverInfo.getPlayers().get(1).getPoints()));
			} else {
				timeline.stop();
				time.cancel();
				showWinner(player1.getPoints() == 5 ? player1 : player2);
			}
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		TimerTask timerTask3 = new TimerTask() {
			@Override
			public void run() {
				ServerManager.getInstance().sendPlayerInfo(myself);
			}
		};
		time.scheduleAtFixedRate(timerTask3, 100, 100);
		
		
		return root;
	}
	
	private void gameUptate() {
		
		// mise à jour de la position de la balle
		ball.setPositionX(ball.getPositionX() + ball.getVelocity() * ball.getVelocityX());
		ball.setPositionY(ball.getPositionY() + ball.getVelocity() * ball.getVelocityY());
		
		
		// l'ia du bot, suit la hauteur de la balle quand elle est dans sa moitié de terrain
		if (myself.getRaquet().getPosition() + 30 > yplayer) {
			if (myself.getRaquet().getPosition() > 0) {
				myself.getRaquet().setPosition(myself.getRaquet().getPosition() - 5);
			}
		}
		if (myself.getRaquet().getPosition() - 30 + myself.getRaquet().getSize() / 2 < yplayer) {
			if (myself.getRaquet().getPosition() + myself.getRaquet().getSize() < HEIGHT) {
				myself.getRaquet().setPosition(myself.getRaquet().getPosition() + 5);
			}
		}
	}

	private void showWinner(Player winner){
		Displayer.getInstance().showNetworkMultiplayerOneVSOneMenu();
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
}
