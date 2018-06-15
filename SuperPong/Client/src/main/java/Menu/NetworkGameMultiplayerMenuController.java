package Menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import main.Displayer;

public class NetworkGameMultiplayerMenuController {

	@FXML
	private AnchorPane networkGameMultiplayerMenu;

	@FXML
	private Button BackButton;

	@FXML
	private Spinner<?> nbJoueurSpinner;

	@FXML
	private Button MultiplayerButton;

	@FXML
	private Button MultiplayerWithItemsButton;

	@FXML
	void goBackToMainMenu() {
		Displayer.getInstance().showNetworkGameMenu();
	}

	@FXML
	void playMulitplayerWithItems() {
		// TODO: fonctionnalité à implémenter
	}

	@FXML
	void playMultiplayerClassique() {
		// TODO: fonctionnalité à implémenter
	}

}
