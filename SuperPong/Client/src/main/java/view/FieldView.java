package view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class FieldView {
    private Pane root;
    private int width;
    private int height;

    public FieldView(int width, int height){
        this.width = width;
        this.height = height;
        root = new Pane();
        root.setPrefSize(width,height);
        root.setStyle("-fx-background-color:black");

        Line line = new Line(width/2,0,width/2,height);
        line.setStroke(Color.WHITE);

        root.getChildren().addAll(line);
    }

    public Pane printField(){
        return root;
    }

    public Pane getRoot() {
        return root;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
