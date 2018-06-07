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
			}
		};
		time.scheduleAtFixedRate(timerTask1, 10, 10);
		
		
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
			Protocole msg = new Protocole(SuperPongProtocole.CMD_INSCRIPTION_GAME, new GameJoin(true, i));
			sendMessage(msg, players.get(i).getWriter());
		}
	}
	
	/**
	 * Méthode qui notifie aux joueurs: l'état de la balle et des autres joueurs
	 */
	private synchronized void notifyPlayers() {
		LinkedList<Player> lol = new LinkedList<>();
		for (Player p : players){
			lol.add(p);
		}
		for(PlayerServer player : players) {
			ServerInfo playerInfos = new ServerInfo(lol, ball);
			Protocole msg = new Protocole(SuperPongProtocole.CMD_PLAY, playerInfos);
			sendMessage(msg, player.getWriter());
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
