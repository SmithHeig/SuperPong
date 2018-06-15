package menu;

import javafx.event.ActionEvent;
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
    void goBackToMainMenu(ActionEvent event) {
        Displayer.getInstance().showNetworkGameMenu();
    }

    @FXML
    void playMulitplayerWithItems(ActionEvent event) {

    }

    @FXML
    void playMultiplayerClassique(ActionEvent event) {

    }

}
