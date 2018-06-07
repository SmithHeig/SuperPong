package Game;

import game.Player;

import java.io.PrintWriter;

public class PlayerServer extends Player {
    private PrintWriter writer;

    // TODO a changé Po Bo
    public PlayerServer(String username, int points, double position, PrintWriter socket) {
        super(username);
        this.writer = socket;
    }

    public PlayerServer(Player player, PrintWriter writer){
        super(player);
        this.writer = writer;
    }

    public PrintWriter getWriter(){
        return writer;
    }

    public void update(Player player){
        this.raquet = player.getRaquet();
    }
}
