package view;

import game.Player;

import java.util.LinkedList;

public interface GameView {
    LinkedList<Player> getPlayers();
    LinkedList<RaquetView> getRaquetView();
}
