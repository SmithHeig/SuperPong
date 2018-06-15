package gameServer;

import protocole.game.Item;
import game.Player;
import game.ItemFactory;
import protocole.Protocole;
import protocole.SuperPongProtocole;
import protocole.game.ItemProtocole;
import protocole.game.ServerInfo;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class GameServerItems extends GameServer {
    private Item item;
    private Timer createItemTimer;
    private boolean isNewItems; //TODO mettre dans item
    private boolean isTouch;
    /**
     * Contructeur
     *
     * @param players liste de joueur participant à cette partie.
     */
    public GameServerItems(LinkedList<PlayerServer> players) {
        super(players);
        isNewItems = false;
        createItemTimer = new Timer();
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                isNewItems = true;
                isTouch = false;
                item = ItemFactory.generateItem((int) field.getWidth(), (int) field.getHeight());
                System.out.println("item " + item);
            }
        };
        createItemTimer.scheduleAtFixedRate(timerTask1, 10000, 10000);
    }

    @Override
    public void endGame() {
        super.endGame();
        createItemTimer.cancel();
    }

    public void gameUpdate(){
        super.gameUpdate();
        if(item != null) {
            if (item.isTouch(ball)) {
                item.execute(this);
                item = null;
                isTouch = true;
            }
        }
    }

    protected synchronized void notifyPlayers() {
        LinkedList<Player> _players = new LinkedList<>();
        for (Player p : players) {
            _players.add(p);
        }
        for (PlayerServer player : players) {
            ServerInfo playerInfos = new ServerInfo();
            playerInfos.setPlayers(_players);
            playerInfos.setBall(ball);
            playerInfos.setFinised(false);
            System.out.println(isNewItems);
            playerInfos.setNewItem(isNewItems);
            if(item != null){
                ItemProtocole itemProtocole = new ItemProtocole(item.getPositionX(),item.getPositionY(), item.getDuration(), item.getName());
                playerInfos.setItem(itemProtocole);
            }

            playerInfos.setTouch(isTouch);

            System.out.println(playerInfos.getItem() + " " + item);
            Protocole msg = new Protocole(SuperPongProtocole.CMD_PLAY, playerInfos);
            sendMessage(msg, player.getClientHandler().getWriter());
        }

        if(item != null){
            isNewItems = false;
        }
        isTouch = false;
    }

}
