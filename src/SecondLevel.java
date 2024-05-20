import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SecondLevel extends Scene {
    private Food food;
    private Snake snake;
    private Obstacle obstacle1;
    private Obstacle obstacle2;
    private Obstacle obstacletop;
    private Obstacle obstaclebotom;
    private Obstacle obstacleleft;
    private Obstacle obstacleright;
    private TranslateTransition transition1;
    private TranslateTransition transition2;
    private Group root;
    private GamePopup gamePopup;
    private MenuScene menuScene;

    public SecondLevel(Stage stage) {
        super(new Group(), 800, 600);
        root = (Group) this.getRoot();
        setupBackground();
        setupObstaclesAndTransitions();
        setupCollisionDetection();

        food = new Food();
        snake = new Snake(800, 600, food);
        root.getChildren().addAll(food, snake, obstacle1, obstacle2, obstacletop, obstaclebotom, obstacleleft,
                obstacleright);
        snake.requestFocus();
        gamePopup = new GamePopup(snake, stage);

        // Add event handler for Esc key press
        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                snake.setpause(true);
                gamePopup.showPopup();
            }
        });
    }

    private void setupBackground() {
        Image backgroundImage = new Image("images/secondlevel.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(800);
        backgroundImageView.setFitHeight(600);
        backgroundImageView.toBack(); // Ensuring the background is behind other elements
        root.getChildren().add(backgroundImageView);
    }

    private void setupObstaclesAndTransitions() {
        obstacle1 = new Obstacle(50, 200, 200, 0);
        obstacle2 = new Obstacle(50, 200, 600, 400);
        obstacletop = new Obstacle(800, 20, 0, 0);
        obstacleright = new Obstacle(20, 600, 780, 0);
        obstacleleft = new Obstacle(20, 600, 0, 0);
        obstaclebotom = new Obstacle(800, 20, 0, 580);

        transition1 = createTransition(obstacle1, 380);
        transition2 = createTransition(obstacle2, -380);
        transition1.play();
        transition2.play();
    }

    private TranslateTransition createTransition(Obstacle obstacle, double yTranslation) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(3), obstacle);
        transition.setByY(yTranslation);
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setAutoReverse(true);
        return transition;
    }

    private void setupCollisionDetection() {
        Timeline collisionCheck = new Timeline(new KeyFrame(Duration.seconds(0.01), event -> {
            if (checkShapeCollision()) {
                handleCollision();
                snake.showGameOver();
                snake.showReloadText();
            }
            if (snake.alive) {
                snake.hideGameOver();
                snake.hideReloadText();
            }
        }));
        collisionCheck.setCycleCount(Timeline.INDEFINITE);
        collisionCheck.play();
    }

    private boolean checkShapeCollision() {
        if (snake == null) {
            return false;
        }

        // Check collision with obstacles
        boolean obstacleCollision = snake.getHead().getBoundsInParent().intersects(obstacle1.getBoundsInParent()) ||
                snake.getHead().getBoundsInParent().intersects(obstacle2.getBoundsInParent()) ||
                snake.getHead().getBoundsInParent().intersects(obstacletop.getBoundsInParent()) ||
                snake.getHead().getBoundsInParent().intersects(obstaclebotom.getBoundsInParent()) ||
                snake.getHead().getBoundsInParent().intersects(obstacleleft.getBoundsInParent()) ||
                snake.getHead().getBoundsInParent().intersects(obstacleright.getBoundsInParent());

        boolean obstacleCollision2 = false;
        for (int i = 1; i < snake.bodySegments.size(); i++) {
            if (obstacle1.getBoundsInParent().intersects(snake.bodySegments.get(i).getBoundsInParent())
                    || obstacle2.getBoundsInParent().intersects(snake.bodySegments.get(i).getBoundsInParent())) {
                obstacleCollision2 = true;
                break;
            }
        }

        return obstacleCollision || obstacleCollision2;
    }

    private void handleCollision() {
        if (snake != null) {
            snake.alive = false;
        }
    }
}
