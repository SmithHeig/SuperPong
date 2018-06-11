package game;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Displayer;
import model.Field;
import view.BallView;
import view.RandomItem;
import view.RaquetView;

import java.util.Timer;
import java.util.TimerTask;

public class Game1v1 {
	
	private BallView ball;
	private Player player1;
	private Player player2;
	private Label player1Score;
	private Label player2Score;
	private Player myself;
	private double yplayer;
	
	private final int PLAYER_RAQUET_SIZE = 100;
	private Pane root;
	private Item item = null;
	private final int WIDTH = 1000, HEIGHT = 600;
	
	private RandomItem randomItem = new RandomItem();
	private long timerTime = 1;
	private boolean estTouche = false;
	
	public Game1v1(int myselfID) {
		player1 = new Player("player1", 0, 0, new RaquetView(PLAYER_RAQUET_SIZE, HEIGHT / 2 - PLAYER_RAQUET_SIZE / 2, 0));
		player2 = new Player("player2", 0, 1, new RaquetView(PLAYER_RAQUET_SIZE, HEIGHT / 2 - PLAYER_RAQUET_SIZE / 2, WIDTH - 10));
		
		if (myselfID == 0) {
			myself = player1;
		} else{
			myself = player2;
		}
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
		
		return root;
	}
	
	private void gameUptate() {
		double x = ball.getPositionX();
		double y = ball.getPositionY();
		
		
		
		boolean PlayerlastTouchTheBall = true;
		
		// items
		/*
		if (item != null) {
			item.getBox().setRotate(item.getBox().getRotate() + 1);
			
			// un joueur touche l'objet
			if (!estTouche && x >= item.getBox().getLayoutX() - 25 && x <= item.getBox().getLayoutX() + 50 && y >= item.getBox().getLayoutY() - 25 && y <= item.getBox().getLayoutY() + 50) {
				root.getChildren().remove(item.getBox());
				item.execute(this);
				estTouche = true;
			}
		}
		
		if (timerTime % 1000 == 0) {
			item = randomItem.generateItem(WIDTH, HEIGHT);
			root.getChildren().add(item.getBox());
		}
		
		
		if ((timerTime + 1) % 1000 == 0) {
			if (item != null) {
				root.getChildren().remove(item.getBox());
				if (estTouche) {
					item.desexecute(this);
					estTouche = false;
				}
			}
		}
		*/
		
		timerTime++;
		
		double ANGLE_MIN = -45;
		double ANGLE_MAX = 45;
		double ANGLE_DELTA = ANGLE_MAX - ANGLE_MIN;
		
		// si le joueur touche la balle
		if (x <= 15 && y > player1.getRaquet().getPosition() && y < player1.getRaquet().getPosition() + player1.getRaquet().getSize()) {
			PlayerlastTouchTheBall = true;
			double perCent = (y - player1.getRaquet().getPosition()) / player1.getRaquet().getSize();
			
			double alpha = ANGLE_DELTA * perCent + ANGLE_MIN;
			
			double rad = Math.toRadians(alpha);
			
			ball.setVelocityX(Math.cos(rad));
			ball.setVelocityY(Math.sin(rad));
		}
		
		ANGLE_MIN = -135;
		ANGLE_DELTA = 90;
		if (x >= WIDTH - 10 && y > player2.getRaquet().getPosition() && y < player2.getRaquet().getPosition() + player2.getRaquet().getSize()) {
			
			PlayerlastTouchTheBall = false;
			double perCent = (y - player2.getRaquet().getPosition()) / player2.getRaquet().getSize();
			
			double alpha = -ANGLE_DELTA * perCent + ANGLE_MIN;
			
			double rad = Math.toRadians(alpha);
			
			ball.setVelocityX(Math.cos(rad));
			ball.setVelocityY(Math.sin(rad));
		}
		
		// le player 1 prend un goal
		if (x < 0) {
			player2.setPoints(player2.getPoints() + 1);
			player2Score.setText(Integer.toString(player2.getPoints()));
			ball.setPositionY(HEIGHT / 2);
			ball.setPositionX(WIDTH / 2);
		}
		
		// le player 2 prend un goal
		if (x > WIDTH) {
			player1.setPoints(player1.getPoints() + 1);
			player1Score.setText(Integer.toString(player1.getPoints()));
			ball.setPositionY(HEIGHT / 2);
			ball.setPositionX(WIDTH / 2);
		}
		
		// rebonds contre les murs haut et bas
		if (y <= 0 || y >= HEIGHT - 5) ball.setVelocityY(ball.getVelocityY() * (-1));
		
		// mise à jour de la position de la balle
		ball.setPositionX(ball.getPositionX() + ball.getVelocity() * ball.getVelocityX());
		ball.setPositionY(ball.getPositionY() + ball.getVelocity() * ball.getVelocityY());
	
		
		// ia du bot
		if (x > WIDTH / 2 && player2.getRaquet().getPosition() + 30 > y) {
			if (player2.getRaquet().getPosition() > 0) {
				player2.getRaquet().setPosition(player2.getRaquet().getPosition() - 5);
			}
		}
		if (x > WIDTH / 2 && player2.getRaquet().getPosition() - 30 + player2.getRaquet().getSize() / 2 < y) {
			if (player2.getRaquet().getPosition() + player2.getRaquet().getSize() < HEIGHT) {
				player2.getRaquet().setPosition(player2.getRaquet().getPosition() + 5);
			}
		}
		
//player
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
