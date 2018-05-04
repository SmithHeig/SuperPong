package main;

import game.Game1v1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SuperPong extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		Displayer.getInstance().setStage(stage);
		Displayer.getInstance().showMainMenu();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
