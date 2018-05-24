package Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Displayer;

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
    void disconnect(ActionEvent event) {
        Displayer.getInstance().showMainMenu(); // to do log out
    }

    @FXML
    void goToFriendsMenu(ActionEvent event) {

    }

    @FXML
    void goToGameNetworkMenu(ActionEvent event) {
        Displayer.getInstance().showNetworkGameMenu();
    }

    @FXML
    void goToStatMenu(ActionEvent event) {

    }

}
