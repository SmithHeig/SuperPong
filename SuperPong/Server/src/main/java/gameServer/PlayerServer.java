package gameServer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import game.Player;
import server.ClientHandler;

@JsonIgnoreProperties({"clientHandler"})
public class PlayerServer extends Player {
    private ClientHandler clientHandler;

    // TODO a changé Po Bo
    public PlayerServer(String username, int points, double position, ClientHandler clientHandler) {
        super(username);
        this.clientHandler = clientHandler;
    }

    public PlayerServer(Player player, ClientHandler clientHandler){
        super(player);
        this.clientHandler = clientHandler;
    }

    public ClientHandler getClientHandler(){
        return clientHandler;
    }

    public void update(Player player){
        this.raquet = player.getRaquet();
    }
}
