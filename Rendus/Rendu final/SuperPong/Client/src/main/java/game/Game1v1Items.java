package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import protocole.game.Item;
import view.ItemView;
import view.RaquetView;

public class Game1v1Items extends Game1v1{
    private ItemView itemView;
    private Item item;
    private Timeline createItemTimer;

    public Game1v1Items(){
        super();
        createItemTimer = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(11), ev -> {
            item = ItemFactory.generateItem(field.getWidth(), field.getHeight());
            itemView = new ItemView(item, root);
        });
        createItemTimer.getKeyFrames().add(keyFrame);
        createItemTimer.setCycleCount(Animation.INDEFINITE);
        createItemTimer.play();
    }

    public void gameUpdate(){
        super.gameUpdate();
        if(itemView != null && item != null) {
            if (item.isTouch(ball)) {
                item.execute(this);
                itemView.deleteItem();
                itemView = null;
                for(RaquetView raquetView: raquetViews){
                    raquetView.update();
                }
            }
        }
    }
}
