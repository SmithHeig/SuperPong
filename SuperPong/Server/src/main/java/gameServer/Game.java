package gameServer;

import game.Ball;
import game.Field;
import game.Player;
import protocole.Protocole;
import protocole.SuperPongProtocole;
import protocole.data.matchmaking.GameJoin;
import protocole.game.ServerInfo;
import protocole.mapper.JsonMapper;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe représentant une partie et gérant une partie en multijoueur
 */
public class Game implements Runnable {
	/* LOGGER */
	private final static Logger LOG = Logger.getLogger(Game.class.getName());
	
	/* ATTRIBUTS */
	private LinkedList<PlayerServer> players;
	private Ball ball;
	private Field field;
	
	private final static int TIME = 100;
	
	/**
	 * Contructeur
	 *
	 * @param players
	 * 		liste de joueur participant à cette partie.
	 */
	public Game(LinkedList<PlayerServer> players) {
		// TODO : change id player
		this.players = players;
		notifyPlayersGameJoin();
		field = new Field(players.size(), 1000, 600);
		ball = new Ball(7, -1, 0, (int) field.getWidth() / 2, (int) field.getHeight() / 2);
	}
	
	/**
	 * Méthode exécuté par le thread. Gestion de la partie.
	 */
	@Override
	public void run() {
		
		Timer time = new Timer();
		TimerTask timerTask1 = new TimerTask() {
			@Override
			public void run() {
				// mise à jour de la position de la balle
				ball.setPositionX(ball.getPositionX() + ball.getVelocity() * ball.getVelocityX());
				ball.setPositionY(ball.getPositionY() + ball.getVelocity() * ball.getVelocityY());
				
				double ANGLE_MIN = -45;
				double ANGLE_MAX = 45;
				double ANGLE_DELTA = ANGLE_MAX - ANGLE_MIN;
				
				if (ball.getPositionX() <= 0) {
					if (ball.getPositionY() > players.get(0).getRaquet().getPosition()
							&& ball.getPositionY() < players.get(0).getRaquet().getPosition() + players.get(0).getRaquet().getSize()) {
						double perCent = (ball.getPositionY() - players.get(0).getRaquet().getPosition()) / players.get(0).getRaquet().getSize();
						
						double alpha = ANGLE_DELTA * perCent + ANGLE_MIN;
						
						double rad = Math.toRadians(alpha);
						
						ball.setVelocityX(Math.cos(rad));
						ball.setVelocityY(Math.sin(rad));
					} else {
						/* GOAL */
						players.get(1).setPoints(players.get(1).getPoints() + 1);
						ball.setPositionY(field.getHeight() / 2);
						ball.setPositionX(field.getWidth() / 2);
						/* Un gagnant */
						if(players.get(1).getPoints() >= 5) {
							LOG.log(Level.INFO, "Player 2 win!");
							notifyPlayersGameFinished();
							time.cancel(); // Arrêt de la partie
						}
					}
				}
				
				ANGLE_MIN = -135;
				ANGLE_DELTA = 90;
				if (ball.getPositionX() >= field.getWidth()) {
					if (ball.getPositionY() > players.get(1).getRaquet().getPosition()
							&& ball.getPositionY() < players.get(1).getRaquet().getPosition() + players.get(1).getRaquet().getSize()) {
						double perCent = (ball.getPositionY() - players.get(1).getRaquet().getPosition()) / players.get(1).getRaquet().getSize();
						
						double alpha = -ANGLE_DELTA * perCent + ANGLE_MIN;
						
						double rad = Math.toRadians(alpha);
						
						ball.setVelocityX(Math.cos(rad));
						ball.setVelocityY(Math.sin(rad));
					} else {
						/* GOAL */
						players.get(0).setPoints(players.get(0).getPoints() + 1);
						ball.setPositionY(field.getHeight() / 2);
						ball.setPositionX(field.getWidth() / 2);

						/* Un gagnant */
						if(players.get(0).getPoints() >= 5) {
							LOG.log(Level.INFO, "Player 2 win!");
							notifyPlayersGameFinished();
							time.cancel(); // Arrêt de la partie
						}
					}
				}
				
				// rebonds contre les murs haut et bas
				if (ball.getPositionY() <= 0 || ball.getPositionY() >= field.getHeight()) ball.setVelocityY(ball.getVelocityY() * (-1));
				
			}
		};
		time.scheduleAtFixedRate(timerTask1, 20, 20);
		
		
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				notifyPlayers();
			}
		};
		time.scheduleAtFixedRate(timerTask, TIME, TIME);
		
		
	}
	
	private void notifyPlayersGameJoin() {
		for (int i = 0; i < players.size(); ++i) {
			players.get(i).setId(i);
			Protocole msg = new Protocole(SuperPongProtocole.CMD_INSCRIPTION_GAME, new GameJoin(true, i));
			sendMessage(msg, players.get(i).getClientHandler().getWriter());
			players.get(i).getClientHandler().setGame(this);
		}
	}

	private void notifyPlayersGameFinished(){
		for(int i = 0; i < players.size(); ++i){
			Protocole msg = new Protocole(SuperPongProtocole.CMD_PLAY, new ServerInfo(null, null, true));
			sendMessage(msg, players.get(i).getClientHandler().getWriter());
		}
	}
	
	/**
	 * Méthode qui notifie aux joueurs: l'état de la balle et des autres joueurs
	 */
	private synchronized void notifyPlayers() {
		LinkedList<Player> _players = new LinkedList<>();
		for (Player p : players) {
			_players.add(p);
		}
		for (PlayerServer player : players) {
			ServerInfo playerInfos = new ServerInfo(_players, ball,false);
			Protocole msg = new Protocole(SuperPongProtocole.CMD_PLAY, playerInfos);
			sendMessage(msg, player.getClientHandler().getWriter());
		}
	}
	
	private synchronized void sendMessage(Protocole msg, PrintWriter writer) {
		String msgJson = JsonMapper.getInstance().convertToString(msg);
		LOG.log(Level.INFO, "SERVER: Send msg : " + msgJson);
		writer.println(msgJson + "\n");
		writer.flush();
	}
	
	public synchronized void updateFromClient(Player updatePlayer) {
		players.get(updatePlayer.getId()).update(updatePlayer); // TODO: Verifier que les joueurs sont trié par ID dans la linkedlist
	}
}
