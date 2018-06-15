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
		changeMenu("/menu/MainMenu.fxml");
	}
	
	public void showLocalMenu() {
		changeMenu("/menu/LocalMenu.fxml");
	}
	
	public void showLoginMenu() {
		changeMenu("/menu/LoginMenu.fxml");
	}
	
	public void showNetworkGameMenu(){
		changeMenu("/menu/NetworkGameMenu.fxml");
	}
	
	public void showNetworkGameMultiplayerMenu(){
		changeMenu("/menu/NetworkGameMultiplayerMenu.fxml");
	}
	
	public void showNetworkMultiplayerOneVSOneMenu(){
		changeMenu("/menu/NetworkGameOneVSOneMenu.fxml");
	}
	
	public void showNetworkMenu() {
		changeMenu("/menu/NetworkMenu.fxml");
	}

	public void showStatsMenu() {changeMenu("/menu/StatsMenu.fxml");}

	public void showAdminZone() {changeMenu("/menu/AdminMenu.fxml");}
	
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
