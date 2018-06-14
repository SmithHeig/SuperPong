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
import network.ServerManager;
import protocole.game.ServerInfo;
import view.BallView;
import view.FieldView;
import view.RaquetView;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Game1v1Network implements Game{
	
	protected BallView ball;
	protected LinkedList<Player> players;
	protected LinkedList<RaquetView> raquetViews;
	protected Label player1Score;
	protected Label player2Score;
	protected int myselfID;
	protected double yplayer;

	protected Pane root;
	protected FieldView field = new FieldView(1000,600);

	
	public Game1v1Network(LinkedList<Player> _players, int myselfID) {
		this.myselfID = myselfID;
		players = _players;
		raquetViews = new LinkedList<>();
		raquetViews.add(new RaquetView(players.get(0).getRaquet(),0));
		raquetViews.add(new RaquetView(players.get(1).getRaquet(),field.getWidth() - players.get(1).getRaquet().getInitThickness()));
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

		for(RaquetView raquetView: raquetViews) {
			root.getChildren().add(raquetView.getView());
		}

		// TODO faire que les scores soit générique pour plus que 2 joueurs
		root.getChildren().addAll(ball.getBall(), player1Score, player2Score);
		
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

				for(int i = 0 ; i < serverInfo.getPlayers().size(); ++i){
					if(i != myselfID) {
						players.get(i).update(serverInfo.getPlayers().get(i));
						raquetViews.get(i).update();
					} else {
						players.get(i).softUpdate(serverInfo.getPlayers().get(i));
					}
				}
				player1Score.setText(String.valueOf(serverInfo.getPlayers().get(0).getPoints()));
				player2Score.setText(String.valueOf(serverInfo.getPlayers().get(1).getPoints()));
			} else {
				timeline.stop();
				time.cancel();
				showWinner(players.get(0).getPoints() == 5 ? players.get(0) : players.get(1));
			}
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		TimerTask timerTask3 = new TimerTask() {
			@Override
			public void run() {
				ServerManager.getInstance().sendPlayerInfo(players.get(myselfID));
			}
		};
		time.scheduleAtFixedRate(timerTask3, 100, 100);
		
		
		return root;
	}
	
	private void gameUptate() {
		// mise à jour de la position de la balle
		ball.setPositionX(ball.getPositionX() + ball.getVelocity() * ball.getVelocityX());
		ball.setPositionY(ball.getPositionY() + ball.getVelocity() * ball.getVelocityY());
		

		if (players.get(myselfID).getRaquet().getPosition() + 30 > yplayer) {
			if (players.get(myselfID).getRaquet().getPosition() > 0) {
				players.get(myselfID).getRaquet().setPosition(players.get(myselfID).getRaquet().getPosition() - 5);
				raquetViews.get(myselfID).update();
			}
		}
		if (players.get(myselfID).getRaquet().getPosition() - 30 + players.get(myselfID).getRaquet().getSize() / 2 < yplayer) {
			if (players.get(myselfID).getRaquet().getPosition() + players.get(myselfID).getRaquet().getSize() < field.getHeight()) {
				players.get(myselfID).getRaquet().setPosition(players.get(myselfID).getRaquet().getPosition() + 5);
				raquetViews.get(myselfID).update();
			}
		}
	}

	private void showWinner(Player winner){
		Displayer.getInstance().showNetworkMultiplayerOneVSOneMenu();
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle("Fin de la partie");
		alert.setHeaderText(winner.getUsername() + " a gagné!");
		// TODO à rendre plus générique et peu etre déplacer (super classse?)
		alert.setContentText(players.get(winner.getId()).getUsername() + ": " + players.get(winner.getId()).getPoints() + "\n" +
				players.get(1-winner.getId()).getUsername() + ": " + players.get(1-winner.getId()).getPoints() + "\n");
		ButtonType buttonTypeOne = new ButtonType("Retour au menu"); // ajoute un bouton "Rejouer" à la boite de dialogue
		alert.getButtonTypes().add(buttonTypeOne);
		alert.show();
	}

    @Override
    public Ball getBall() {
        return null;
    }

    @Override
    public Player getPlayerLastTouch() {
        return null;
    }

    public LinkedList<Player> getPlayers(){
        return players;
    }
}
