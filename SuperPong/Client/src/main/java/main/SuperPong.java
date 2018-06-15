package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class SuperPong extends Application {
	
	@Override
	public void start(Stage stage) {
		Displayer.getInstance().setStage(stage);
		Displayer.getInstance().showMainMenu();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
