package menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.Displayer;
import network.ServerManager;

public class AdminMenuController {

    @FXML
    private Button BackButton;

    @FXML
    private TextField textFieldUsername;

    @FXML
    private TextField textFieldNbWins;

    @FXML
    private TextField textFieldNbPlays;

    @FXML
    private Button buttonChangeStats;

    @FXML
    private TextField textFieldPassword;

    @FXML
    private Button buttonChangePassword;

    @FXML
    void changePassword(ActionEvent event) {
        if(textFieldUsername.getText() != null || textFieldPassword.getText() != null) {
            boolean haveChanged = ServerManager.getInstance().changePassword(textFieldUsername.getText(), textFieldPassword.getText());
        }
    }

    @FXML
    void changeStats(ActionEvent event) {
        if(textFieldUsername.getText() != null || textFieldNbWins.getText() != null || textFieldNbPlays.getText() != null) {
            boolean haveChanged = ServerManager.getInstance().changeStats(textFieldUsername.getText(), Integer.parseInt(textFieldNbWins.getText()), Integer.parseInt(textFieldNbPlays.getText()));
        }
    }

    @FXML
    void goBackToNetworkMenu(ActionEvent event) {
        Displayer.getInstance().showNetworkMenu();
    }

}
