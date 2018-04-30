package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Ball;
import model.Field;
import view.Raquet;

public class Game1v1 extends Application {
	
	private Ball ball;
	private Raquet playerRaquet;
	private Raquet bot;
	private final int  PLAYER_RAQUET_SIZE = 200, BOT_RAQUET_SIZE = 100;
	private int speedX = 4, speedY = 4, dv = speedX, dy = speedY;
	
	private final int WIDTH = 1000, HEIGHT = 600;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Game1v1");
		primaryStage.setScene(new Scene(createContent()));
		
		primaryStage.show();
		primaryStage.getScene().setOnKeyPressed(event -> {
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
		Pane root = field.printField();
		playerRaquet = new Raquet(0, HEIGHT / 2 - PLAYER_RAQUET_SIZE/2, PLAYER_RAQUET_SIZE);
		bot = new Raquet(WIDTH - 10, HEIGHT / 2 - BOT_RAQUET_SIZE, BOT_RAQUET_SIZE);
		
		ball = new Ball(WIDTH / 2, HEIGHT / 2);
		
		root.getChildren().addAll(playerRaquet.createPlayer(), bot.createPlayer(), ball.getBall());
		
		AnimationTimer timer = new AnimationTimer() {
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
		
		if (x <= 10 && y > playerRaquet.getPositionY() && y < playerRaquet.getPositionY() + playerRaquet.getHeight()) dv = speedX;
		
		if (x >= WIDTH - 10 && y > bot.getPositionY() && y < bot.getPositionY() + bot.getHeight()) {
			dv = -speedX;
		}
		
		if (x < 0){
			System.out.println("bof, t'es nul");
			ball.setPositionY(HEIGHT/2);
			ball.setPositionX(WIDTH/2);
		}
		
		if (x > WIDTH){
			System.out.println("yeah");
			ball.setPositionY(HEIGHT/2);
			ball.setPositionX(WIDTH/2);
		}
		
		
		if (y <= 0) dy = speedY;
		if (y >= HEIGHT - 5) dy = -speedY;
		
		
		ball.setPositionX(ball.getPositionX() + dv);
		ball.setPositionY(ball.getPositionY() + dy);
		
		// l'ia du bot, suit la hauteur de la balle quand elle est dans sa moitié de terrain
		if (x > WIDTH / 2 && bot.getPositionY()-bot.getHeight()/2-1 > y) {
			if (bot.getPositionY() > 0) {
				bot.setPositionY(bot.getPositionY() - 30);
			}
		}
		if (x > WIDTH / 2 && bot.getPositionY() + bot.getHeight()/2+1 < y) {
			if (bot.getPositionY() + bot.getHeight() < HEIGHT) {
				bot.setPositionY(bot.getPositionY() + 30);
			}
			
		}
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
