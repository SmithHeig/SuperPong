package Menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import main.Displayer;
import main.SuperPong;

public class MainMenuController {

    @FXML
    private AnchorPane mainMenu;

    @FXML
    private Button LocalMenuButton;

    @FXML
    private Button NetworkMenuButton;
    
    
    @FXML
    void goToLocalMenu(){
        Displayer.getInstance().showLocalMenu();
    }

    @FXML
    void goToNetworkMenu(ActionEvent event) {
        Displayer.getInstance().showLoginMenu();
    }

}

