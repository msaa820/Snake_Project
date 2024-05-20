import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FirstLevel extends Scene {
    private Food food;
    private Snake snake;
    private GamePopup gamePopup;
    private MenuScene menuScene;

    public FirstLevel(Stage stage) {
        super(new Group(), 800, 600);

        // Create a StackPane to hold the background and game objects
        StackPane root = new StackPane();
        this.setRoot(root);

        // Initialize game elements
        food = new Food();
        snake = new Snake(800, 600, food);

        // Create background image view
        Image backgroundImage = new Image("images/firstlevel.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800);
        backgroundImageView.setFitHeight(600);

        // Add elements to the root
        root.getChildren().addAll(backgroundImageView, snake, food);
        // this.setFill(Color.TRANSPARENT);

        // Request focus for the snake to capture key events
        Platform.runLater(snake::requestFocus);

        // Create the pop-up window
        gamePopup = new GamePopup(snake, stage);

        // Add event handler for Esc key press
        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                snake.setpause(true);
                gamePopup.showPopup();
            }
        });
    }
}
