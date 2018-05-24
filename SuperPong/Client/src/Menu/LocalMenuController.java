package Menu;

import game.Game1v1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import main.Displayer;

import java.io.IOException;

public class LocalMenuController {

    @FXML
    private AnchorPane localMenu;

    @FXML
    private Button BackButton;

    @FXML
    private Button IAGameButton;

    @FXML
    private Button IAGameWithItemsButton;

    @FXML
    void goBackToMainMenu(ActionEvent event) {
        Displayer.getInstance().showMainMenu();
    }

    @FXML
    void playIAGame(ActionEvent event) {
        try {
            Game1v1 game = new Game1v1();
            game.run(Displayer.getInstance().getStage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void playIAGameWithItems(ActionEvent event) {
        // Play ia game with itmes
    }

}
