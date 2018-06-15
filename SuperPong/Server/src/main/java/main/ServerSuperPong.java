package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;

/**
 * Classe de lancement du serveur
 */
public class ServerSuperPong extends Application {
	
	public Button exitButton;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			FXMLLoader fxmlLoaderMainView = new FXMLLoader(getClass().getResource("/fxml/mainView.fxml"));
			Parent root = fxmlLoaderMainView.load();
			primaryStage.setTitle("Server");
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Server server = new Server();
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exit() {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close(); // ferme la fenêtre et stoppe le serveur
		System.exit(0);
	}
}
