package Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Displayer;

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

    }

    @FXML
    void playOneVOneFriend(ActionEvent event) {

    }

    @FXML
    void playOneVOneFriendWithItems(ActionEvent event) {

    }

    @FXML
    void playOneVOneWithItems(ActionEvent event) {

    }

}
