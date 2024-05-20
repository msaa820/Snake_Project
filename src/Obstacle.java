import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Obstacle extends Rectangle {
    public Obstacle(double width, double height, double layoutX, double layoutY) {
        super(width, height, Color.BLACK);
        setLayoutX(layoutX);
        setLayoutY(layoutY);
    }
}
