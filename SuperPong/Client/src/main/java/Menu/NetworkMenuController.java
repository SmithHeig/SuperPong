package menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Displayer;
import network.ServerManager;

public class NetworkMenuController {

	@FXML
	private AnchorPane networkMenu;

	@FXML
	private Button DisconnectButton;

	@FXML
	private Button GameNetworkMenuButton;

	@FXML
	private Button FriendsButton;

	@FXML
	private Button StatButton;

	@FXML
	private Button buttonAdmin;

	@FXML
	private void initialize() {
		if (ServerManager.getInstance().isAdmin()) {
			buttonAdmin.setVisible(true);
		} else {
			buttonAdmin.setVisible(false);
		}
	}

	@FXML
	void disconnect() {
		/* Essaie de ce déconnecter */
		if (ServerManager.getInstance().disconnect()) {
			Displayer.getInstance().showMainMenu(); // to do log out
		}
	}

	@FXML
	void goToFriendsMenu() {
		// TODO: fonctionnalité à implémenter
	}

	@FXML
	void goToAdminZone() {
		Displayer.getInstance().showAdminZone();
	}

	@FXML
	void goToGameNetworkMenu() {
		Displayer.getInstance().showNetworkGameMenu();
	}

	@FXML
	void goToStatMenu() {
		Displayer.getInstance().showStatsMenu();
	}

}
