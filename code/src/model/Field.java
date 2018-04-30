package model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Field {
	private int nbPlayers; // TODO : changera la forme du terrain
	private Pane root;
	
	public Field(int width, int height){
		root = new Pane();
		root.setPrefSize(width,height);
		root.setStyle("-fx-background-color:black");
		
		Line line = new Line(width/2,0,width/2,height);
		line.setStroke(Color.WHITE);
		
		root.getChildren().add(line);
	}
	
	public Pane printField(){
		
		return root;
	}
	
}
