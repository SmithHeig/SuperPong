package Game;

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
		notifyPlayerGameJoin();
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
						players.get(1).setPoints(players.get(1).getPoints() + 1);
						ball.setPositionY(field.getHeight() / 2);
						ball.setPositionX(field.getWidth() / 2);
					}
				}
				
				
				// rebonds contre les murs haut et bas
				if (ball.getPositionX() >= field.getHeight()) ball.setVelocityY(ball.getVelocityY() * (-1));
				if (ball.getPositionX() <= 0 || ball.getPositionX() >= field.getWidth()) ball.setVelocityX(ball.getVelocityX() * (-1));
				
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
	
	private void notifyPlayerGameJoin() {
		for (int i = 0; i < players.size(); ++i) {
			players.get(i).setId(i);
			Protocole msg = new Protocole(SuperPongProtocole.CMD_INSCRIPTION_GAME, new GameJoin(true, i));
			sendMessage(msg, players.get(i).getClientHandler().getWriter());
			players.get(i).getClientHandler().setGame(this);
		}
	}
	
	/**
	 * Méthode qui notifie aux joueurs: l'état de la balle et des autres joueurs
	 */
	private synchronized void notifyPlayers() {
		LinkedList<Player> lol = new LinkedList<>();
		for (Player p : players) {
			lol.add(p);
		}
		for (PlayerServer player : players) {
			ServerInfo playerInfos = new ServerInfo(lol, ball);
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
		System.out.println(updatePlayer.getRaquet().getPosition());
		players.get(updatePlayer.getId()).update(updatePlayer); // TODO: Verifier que les joueurs sont trié par ID dans la linkedlist
	}
}
