package menu;

import game.Game1v1Network;
import game.Game1v1NetworkItems;
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
	void goBackToMainMenu() {
		Displayer.getInstance().showNetworkGameMenu();
	}

	@FXML
	void playOneVOneClassique() {
		if (ServerManager.getInstance().inscriptionGame(2, false)) {
			try {
				Game1v1Network game = new Game1v1Network(ServerManager.getInstance().receivedGameInfos().getPlayers(), ServerManager.getInstance().getIdUser());

				game.run(Displayer.getInstance().getStage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void playOneVOneFriend() {
		// TODO: fonctionnalité à implémenter
	}

	@FXML
	void playOneVOneFriendWithItems() {
		// TODO: fonctionnalité à implémenter
	}

	@FXML
	void playOneVOneWithItems() {
		if (ServerManager.getInstance().inscriptionGame(2, true)) {
			try {
				Game1v1Network game = new Game1v1NetworkItems(ServerManager.getInstance().receivedGameInfos().getPlayers(), ServerManager.getInstance().getIdUser());

				game.run(Displayer.getInstance().getStage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
