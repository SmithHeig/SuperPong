package menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.Displayer;
import network.ServerManager;

public class LoginMenuController {
	
	@FXML
	private AnchorPane loginMenu;
	
	@FXML
	private Button BackButton;
	
	@FXML
	private Button LoginButton;
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private TextField pwdTextField;
	
	@FXML
	void goBackToMainMenu() {
		Displayer.getInstance().showMainMenu();
	}
	
	@FXML
	void login() {
		String username = usernameTextField.getText();
		String pwd = pwdTextField.getText();
		if (username != null && pwd != null) {
			if (ServerManager.getInstance().connect(username, pwd)) {
				Displayer.getInstance().showNetworkMenu(); // To change
			} else {
				// TODO: afficher un message pour dire que la connection à échoué
			}
		}
	}
	
}
