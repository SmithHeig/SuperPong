package Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Displayer;

public class NetworkGameMenuController {

    @FXML
    private AnchorPane networkGameMenu;
    @FXML
    private Button BackButton;

    @FXML
    private Button OneVOneNetworkButton;

    @FXML
    private Button IAGameWithItemsButton;

    @FXML
    void goBackToNetworkMenu(ActionEvent event) {
        Displayer.getInstance().showNetworkMenu();
    }

    @FXML
    void goToMultiPlayerMenu(ActionEvent event) {
        Displayer.getInstance().showNetworkGameMultiplayerMenu();
    }

    @FXML
    void goToOneVOneNetworkMenu(ActionEvent event) {
        Displayer.getInstance().showNetworkMultiplayerOneVSOneMenu();
    }

}
