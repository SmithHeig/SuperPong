package menu;

import game.Game1v1;
import game.Game1v1Network;
import game.Game1v1NetworkItems;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Displayer;
import network.ServerManager;

public class NetworkGameOneVSOneMenuController {

    @FXML
    private AnchorPane networkGameOneVSOneMenu;

    @FXML
    private Button BackButton;

    @FXML
    private Button OneVOneButton;

    @FXML
    private Button OneVOneWithItemsButton;

    @FXML
    private Button OneVOneFriendButton;

    @FXML
    private Button OneVOneFriendWithItemsButton;

    @FXML
    void goBackToMainMenu(ActionEvent event) {
        Displayer.getInstance().showNetworkGameMenu();
    }

    @FXML
    void playOneVOneClassique(ActionEvent event) {
        if(ServerManager.getInstance().inscriptionGame(2, false)){
	        try {
		        Game1v1Network game = new Game1v1Network(ServerManager.getInstance().receivedGameInfos().getPlayers(), ServerManager.getInstance().getIdUser());
		        
		        // TODO utilise NetworkManager.getInstance().receivedGameInfos() dans un thrad de Game1v1 pour mettre à jour des données (ball + joueurs)
		        game.run(Displayer.getInstance().getStage());
	        } catch (Exception e) {
		        e.printStackTrace();
	        }
        }
    }

    @FXML
    void playOneVOneFriend(ActionEvent event) {

    }

    @FXML
    void playOneVOneFriendWithItems(ActionEvent event) {

    }

    @FXML
    void playOneVOneWithItems(ActionEvent event) {
        if(ServerManager.getInstance().inscriptionGame(2, true)){
            try {
                Game1v1Network game = new Game1v1NetworkItems(ServerManager.getInstance().receivedGameInfos().getPlayers(), ServerManager.getInstance().getIdUser());

                // TODO utilise NetworkManager.getInstance().receivedGameInfos() dans un thrad de Game1v1 pour mettre à jour des données (ball + joueurs)
                game.run(Displayer.getInstance().getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
