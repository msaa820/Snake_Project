import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.util.Random;

public class Food extends Pane {
    private ImageView imageView;
    private static final int FOOD_SIZE = 20; // Size of the image
    private static final int PANE_WIDTH = 800;
    private static final int PANE_HEIGHT = 600;
    private static final int Obstaclewidth = 30;

    public Food() {
        Image image = new Image("images/food.png"); // Provide the path to your image file
        imageView = new ImageView(image);
        imageView.setFitWidth(FOOD_SIZE);
        imageView.setFitHeight(FOOD_SIZE);

        generateNewFood();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void remove() {
        getChildren().remove(imageView);
    }

    public void generateNewFood() {
        getChildren().remove(imageView); // Remove existing image
        Random random = new Random();
        double randomX = random.nextDouble() * (PANE_WIDTH - FOOD_SIZE - Obstaclewidth);
        double randomY = random.nextDouble() * (PANE_HEIGHT - FOOD_SIZE - Obstaclewidth);

        imageView.setLayoutX(randomX);
        imageView.setLayoutY(randomY);

        getChildren().add(imageView); // Add new image
    }
}
