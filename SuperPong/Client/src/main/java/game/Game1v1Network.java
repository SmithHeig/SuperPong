package game;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Field;
import network.ServerManager;
import sun.net.NetworkServer;
import view.BallView;
import view.Item;
import view.RandomItem;
import view.RaquetView;

import java.util.LinkedList;
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
		
		player1 = new Player("player1", 0, new RaquetView(PLAYER_RAQUET_SIZE, HEIGHT / 2 - PLAYER_RAQUET_SIZE / 2, 0));
		player2 = new Player("player2", 0, new RaquetView(PLAYER_RAQUET_SIZE, HEIGHT / 2 - PLAYER_RAQUET_SIZE / 2, WIDTH - 10));
		
		if (myselfID == 0) {
			myself = player1;
			other = player2;
		} else {
			myself = player2;
			other = player1;
		}
	}
	
	public void run(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Game1v1");
		primaryStage.setScene(new Scene(createContent()));
		
		primaryStage.show();
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
		
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				ball.update(ServerManager.getInstance().receivedGameInfos().getBall());
				player2Score.setText(Integer.toString(player2.getPoints()));
				player1Score.setText(Integer.toString(player1.getPoints()));
			
				//ServerManager.getInstance().sendPlayerInfo(myself);
				//other.getRaquet().setPosition(ServerManager.getInstance().receivedGameInfos().getPlayers().get(1 - myselfID).raquet.getPosition());
			}
		};
		time.scheduleAtFixedRate(timerTask, 100, 100);
		
		TimerTask timerTask3 = new TimerTask() {
			@Override
			public void run() {
				
				ServerManager.getInstance().sendPlayerInfo(myself);
			}
		};
		time.scheduleAtFixedRate(timerTask3, 100, 100);
		
		TimerTask timerTask4 = new TimerTask() {
			@Override
			public void run() {
				
				other.getRaquet().setPosition(ServerManager.getInstance().receivedGameInfos().getPlayers().get(1 - myselfID).getRaquet().getPosition());
			}
		};
		time.scheduleAtFixedRate(timerTask4, 200, 200);
		
		
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
}
