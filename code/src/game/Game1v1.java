package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Ball;
import model.Field;
import view.Item;
import view.Raquet;

public class Game1v1 extends Application {
	
	private Ball ball;
	private Raquet playerRaquet;
	private Raquet bot;
	private final int PLAYER_RAQUET_SIZE = 100, BOT_RAQUET_SIZE = 100, VELOCITY = 7;
	private double speedX = -5, speedY = 0, dv = speedX, dy = speedY;
	Pane root;
	Item item;
	private final int WIDTH = 1000, HEIGHT = 600;
	AnimationTimer timer;
	Label player1Score;
	Label player2Score;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Game1v1");
		primaryStage.setScene(new Scene(createContent()));
		
		primaryStage.show();
		primaryStage.getScene().setOnKeyTyped(event -> {
			if (event.getCode() == KeyCode.UP) {
				if (playerRaquet.getPositionY() > 0) {
					playerRaquet.setPositionY(playerRaquet.getPositionY() - 30);
				}
			}
			if (event.getCode() == KeyCode.DOWN) {
				if (playerRaquet.getPositionY() + playerRaquet.getHeight() < HEIGHT) {
					playerRaquet.setPositionY(playerRaquet.getPositionY() + 30);
				}
			}
		});
	}
	
	private Parent createContent() {
		Field field = new Field(WIDTH, HEIGHT);
		root = field.printField();
		playerRaquet = new Raquet(0, HEIGHT / 2 - PLAYER_RAQUET_SIZE / 2, PLAYER_RAQUET_SIZE);
		bot = new Raquet(WIDTH - 10, HEIGHT / 2 - BOT_RAQUET_SIZE, BOT_RAQUET_SIZE);
		
		ball = new Ball(WIDTH / 2, HEIGHT / 2);
		
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
		
		
		root.getChildren().addAll(playerRaquet.createPlayer(), bot.createPlayer(), ball.getBall(), player1Score, player2Score);
		
		
		item = new Item();
		root.getChildren().add(item.getBox());
		
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				gameUptate();
			}
		};
		
		timer.start();
		
		return root;
	}
	
	private void gameUptate() {
		double x = ball.getPositionX();
		double y = ball.getPositionY();
		
		double ANGLE_MIN = -45;
		double ANGLE_MAX = 45;
		double ANGLE_DELTA = ANGLE_MAX - ANGLE_MIN;
		
		item.getBox().setRotate(item.getBox().getRotate() + 1);
		
		// si le joueur touche la balle
		if (x <= 15 && y > playerRaquet.getPositionY() && y < playerRaquet.getPositionY() + playerRaquet.getHeight()) {
			
			double perCent = (y - playerRaquet.getPositionY()) / playerRaquet.getHeight();
			
			double alpha = ANGLE_DELTA * perCent + ANGLE_MIN;
			
			//System.out.println(alpha);
			
			double rad = Math.toRadians(alpha);
			
			dv = VELOCITY * Math.cos(rad);
			dy = VELOCITY * Math.sin(rad);
		}
		
		ANGLE_MIN = -135;
		ANGLE_MAX = 135;
		ANGLE_DELTA = 90;
		if (x >= WIDTH - 10 && y > bot.getPositionY() && y < bot.getPositionY() + bot.getHeight()) {
			double perCent = (y - bot.getPositionY()) / bot.getHeight();
			
			double alpha = -ANGLE_DELTA * perCent + ANGLE_MIN;
			
			System.out.println(alpha);
			
			double rad = Math.toRadians(alpha);
			
			dv = VELOCITY * Math.cos(rad);
			dy = VELOCITY * Math.sin(rad);
		}
		
		// le player 1 prend un goal
		if (x < 0) {
			player2Score.setText(Integer.toString(Integer.parseInt(player2Score.getText()) + 1));
			ball.setPositionY(HEIGHT / 2);
			ball.setPositionX(WIDTH / 2);
		}
		
		// le player 2 prend un goal
		if (x > WIDTH) {
			player1Score.setText(Integer.toString(Integer.parseInt(player1Score.getText()) + 1));
			ball.setPositionY(HEIGHT / 2);
			ball.setPositionX(WIDTH / 2);
		}
		
		
		if (y <= 0) dy *= -1;
		if (y >= HEIGHT - 5) dy *= -1;
		
		
		ball.setPositionX(ball.getPositionX() + dv);
		ball.setPositionY(ball.getPositionY() + dy);
		
		
		// l'ia du bot, suit la hauteur de la balle quand elle est dans sa moitié de terrain
		if (x > WIDTH / 2 && bot.getPositionY()+30 > y) {
			if (bot.getPositionY() > 0) {
				bot.setPositionY(bot.getPositionY() - 5);
			}
		}
		if (x > WIDTH / 2 && bot.getPositionY() -30  + bot.getHeight() / 2 < y) {
			if (bot.getPositionY() + bot.getHeight() < HEIGHT) {
				bot.setPositionY(bot.getPositionY() + 5);
			}
			
		}
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
