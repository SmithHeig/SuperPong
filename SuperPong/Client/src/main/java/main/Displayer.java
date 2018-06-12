package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Displayer {
	private static Displayer ourInstance = new Displayer();
	
	public static Displayer getInstance() {
		return ourInstance;
	}
	
	private Stage stage;
	
	private Displayer() {
	}
	
	public void setStage(Stage stage){
		this.stage = stage;
		stage.setTitle("SuperPong");
	}
	
	public void showMainMenu(){
		changeMenu("/Menu/MainMenu.fxml");
	}
	
	public void showLocalMenu() {
		changeMenu("/Menu/LocalMenu.fxml");
	}
	
	public void showLoginMenu() {
		changeMenu("/Menu/LoginMenu.fxml");
	}
	
	public void showNetworkGameMenu(){
		changeMenu("/Menu/NetworkGameMenu.fxml");
	}
	
	public void showNetworkGameMultiplayerMenu(){
		changeMenu("/Menu/NetworkGameMultiplayerMenu.fxml");
	}
	
	public void showNetworkMultiplayerOneVSOneMenu(){
		changeMenu("/Menu/NetworkGameOneVSOneMenu.fxml");
	}
	
	public void showNetworkMenu() {
		changeMenu("/Menu/NetworkMenu.fxml");
	}

	public void showStatsMenu() {changeMenu("/Menu/StatsMenu.fxml");}

	public void showAdminZone() {changeMenu("/Menu/AdminMenu.fxml");}
	
	private void changeMenu(String fxml){
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			setScene(new Scene(root, 600, 400));
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void setScene(Scene scene){
		stage.setScene(scene);
		stage.show();
	}
	
	public Stage getStage(){
		return stage;
	}

	public void setTitle(String title) {
		stage.setTitle(title);
	}
}
