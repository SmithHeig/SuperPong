package Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Displayer;

public class LoginMenuController {

    @FXML
    private AnchorPane loginMenu;

    @FXML
    private Button BackButton;

    @FXML
    private Button LoginButton;

    @FXML
    void goBackToMainMenu(ActionEvent event) {
        Displayer.getInstance().showMainMenu();
    }

    @FXML
    void login(ActionEvent event) {
        Displayer.getInstance().showNetworkMenu(); // To change
    }

}
