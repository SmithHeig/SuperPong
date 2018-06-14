package view;

import game.Ball;
import game.Field;
import game.Item;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import main.Displayer;

public class ItemView {
    private Item item;
	private Box box;
	private Pane root;
	private Timeline timeline;

	public ItemView(Item item, Pane root) {
	    this.item = item;
	    this.root = root;
        start();
	}
	
	public Box getBox() {
		return box;
	}

	public PhongMaterial color(){
        final PhongMaterial color = new PhongMaterial();
        color.setDiffuseColor(item.getColor());
        return color;
    }

	public void start(){
	    System.out.println(item.getPositionX() + " " + item.getPositionY());
        box = new Box(25, 25, 25);
        box.setLayoutX(item.getPositionX());
        box.setLayoutY(item.getPositionY());
        Rotate rxBox = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate ryBox = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rzBox = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        rxBox.setAngle(30);
        ryBox.setAngle(50);
        rzBox.setAngle(30);
        box.setMaterial(color());
        box.getTransforms().addAll(rxBox, ryBox, rzBox);

        root.getChildren().addAll(box);



        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(20), ev -> {
            rotate();
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount((int)(10000 / 20)); // stop after 10 seconds
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteItem();
            }
        });
    }

    // TODO PRIVATE ?
    public synchronized void deleteItem(){
	    System.out.println(box);
        if(box != null) {
            root.getChildren().remove(box);
            box = null;
        }
    }

    private void rotate(){
	    box.setRotate(box.getRotate() + 1);
    }

    public synchronized boolean isTouch(Ball ball){
	    if(box != null && ball != null) {
            if (ball.getPositionX() >= box.getLayoutX() - 25 && ball.getPositionX() <= box.getLayoutX() + 50 &&
                    ball.getPositionY() >= box.getLayoutY() - 25 && ball.getPositionY() <= box.getLayoutY() + 50) {
                System.out.println("TOUCH");

               box.setVisible(false);
                return true;
            }
        }
        return false;
    }

    public Item getItem(){
	    return item;
    }
}
