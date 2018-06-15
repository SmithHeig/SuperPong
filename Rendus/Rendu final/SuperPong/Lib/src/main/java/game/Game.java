package game;

import java.util.LinkedList;

public interface Game {
    Ball getBall();
    Player getPlayerLastTouch();
    LinkedList<Player> getPlayers();
}
