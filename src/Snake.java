import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Snake extends Pane {
    public ArrayList<ImageView> bodySegments;
    private final double WINDOW_WIDTH;
    private final double WINDOW_HEIGHT;
    private Food food;
    private Image segmentImage;
    private Image headUpImage;
    private Image headDownImage;
    private Image headLeftImage;
    private Image headRightImage;
    private int initialLength = 3;
    private double initialX = 400;
    private double initialY = 300;
    private double displacement = 20; // Speed of movement
    private double dx = 0; // Initial movement in x-direction
    private double dy = displacement; // Initial movement in y-direction
    private int gameSpeed = 100;
    public boolean alive = true;
    private Text gameOverText;
    private Text reloadText;
    private boolean paused = false; // Flag to track whether the game is paused

    public Snake(double width, double height, Food food) {
        this.WINDOW_WIDTH = width;
        this.WINDOW_HEIGHT = height;
        this.food = food;
        this.bodySegments = new ArrayList<>();
        segmentImage = new Image("images/body.png");
        headUpImage = new Image("images/headup.png");
        headDownImage = new Image("images/headdown.png");
        headLeftImage = new Image("images/headleft.png");
        headRightImage = new Image("images/headright.png");

        ImageView head = new ImageView(headDownImage);
        head.setFitWidth(20);
        head.setFitHeight(20);
        head.setLayoutX(initialX);
        head.setLayoutY(initialY);
        bodySegments.add(head);
        for (int i = 1; i < initialLength; i++) {
            ImageView segmentImageView = new ImageView(segmentImage);
            segmentImageView.setFitWidth(20);
            segmentImageView.setFitHeight(20);
            segmentImageView.setLayoutX(initialX - i * 20);
            segmentImageView.setLayoutY(initialY);
            bodySegments.add(segmentImageView);
        }

        gameOverText = new Text("Game Over");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gameOverText.setFill(Color.RED);

        reloadText = new Text("PRESS R TO RELOAD");
        reloadText.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        reloadText.setFill(Color.BLUE);

        double gameOverTextWidth = gameOverText.getLayoutBounds().getWidth();
        double gameOverTextHeight = gameOverText.getLayoutBounds().getHeight();
        gameOverText.setLayoutX((width - gameOverTextWidth) / 2);
        gameOverText.setLayoutY(height / 2 - gameOverTextHeight);
        double reloadTextWidth = reloadText.getLayoutBounds().getWidth();
        double reloadTextHeight = reloadText.getLayoutBounds().getHeight();
        reloadText.setLayoutX((width - reloadTextWidth + 10) / 2);
        reloadText.setLayoutY(height / 2 + reloadTextHeight - 20);
        gameOverText.setVisible(false);
        gameOverText.toFront();
        reloadText.setVisible(false);
        reloadText.toFront();
        getChildren().addAll(gameOverText, reloadText);
        getChildren().addAll(bodySegments);

        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (!alive && code == KeyCode.R) {
                    resetGame();
                } else {
                    switch (code) {
                        case UP:
                            if (dy == 0) {
                                dx = 0;
                                dy = -displacement;
                                bodySegments.get(0).setImage(headUpImage); // Change head image to up
                            }
                            break;
                        case DOWN:
                            if (dy == 0) {
                                dx = 0;
                                dy = displacement;
                                bodySegments.get(0).setImage(headDownImage); // Change head image to down
                            }
                            break;
                        case LEFT:
                            if (dx == 0) {
                                dx = -displacement;
                                dy = 0;
                                bodySegments.get(0).setImage(headLeftImage); // Change head image to left
                            }
                            break;
                        case RIGHT:
                            if (dx == 0) {
                                dx = displacement;
                                dy = 0;
                                bodySegments.get(0).setImage(headRightImage); // Change head image to right
                            }
                            break;
                        case SPACE:
                            paused = !paused; // Toggle the paused flag
                            break;
                        case ESCAPE:
                            break;
                        default:
                            break;
                    }

                }
            }
        });
        setFocusTraversable(true);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (alive && !paused) { // Check if the game is running and not paused
                    move();
                    if (checkFoodCollision()) {
                        food.remove();
                        grow();
                        food.generateNewFood();
                    }
                    if (checkSelfCollision()) {
                        showGameOver();
                        showReloadText();
                        alive = false;
                    }
                    checkWallCollision();
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void move() {
        double prevX = bodySegments.get(0).getLayoutX();
        double prevY = bodySegments.get(0).getLayoutY();
        double newX = prevX + dx;
        double newY = prevY + dy;

        bodySegments.get(0).relocate(newX, newY);

        for (int i = 1; i < bodySegments.size(); i++) {
            double tempX = bodySegments.get(i).getLayoutX();
            double tempY = bodySegments.get(i).getLayoutY();
            bodySegments.get(i).relocate(prevX, prevY);
            prevX = tempX;
            prevY = tempY;
        }
    }

    public boolean checkSelfCollision() {
        ImageView head = bodySegments.get(0);
        double xhead = head.getLayoutX();
        double yhead = head.getLayoutY();
        for (int i = 1; i < bodySegments.size(); i++) {
            ImageView segment = bodySegments.get(i);
            double xbody = segment.getLayoutX();
            double ybody = segment.getLayoutY();
            if (xhead == xbody && yhead == ybody) {
                return true;
            }
        }
        return false;
    }

    public boolean checkFoodCollision() {
        ImageView head = bodySegments.get(0);
        return head.getBoundsInParent().intersects(food.getImageView().getBoundsInParent());
    }

    public void grow() {
        ImageView newSegment = new ImageView(segmentImage);
        newSegment.setFitWidth(20);
        newSegment.setFitHeight(20);

        ImageView tail = bodySegments.get(bodySegments.size() - 1);
        double tailX = tail.getLayoutX();
        double tailY = tail.getLayoutY();
        newSegment.setLayoutX(tailX);
        newSegment.setLayoutY(tailY);
        bodySegments.add(newSegment);
        getChildren().add(newSegment);
    }

    public void resetGame() {
        hideGameOver();
        hideReloadText();
        alive = true;

        for (int i = bodySegments.size() - 1; i >= initialLength; i--) {
            ImageView segmentImageView = bodySegments.get(i);
            getChildren().remove(segmentImageView);
            bodySegments.remove(i);
        }

        for (int i = 0; i < initialLength; i++) {
            ImageView segmentImageView = bodySegments.get(i);
            segmentImageView.setLayoutX(initialX - i * 20);
            segmentImageView.setLayoutY(initialY);
            segmentImageView.setFitWidth(20);
        }

        bodySegments.get(0).setImage(headUpImage); // Reset head image to up

        dx = 0;
        dy = displacement; // Reset initial direction

        food.remove();
        food.generateNewFood();
    }

    public void checkWallCollision() {
        ImageView head = bodySegments.get(0);
        double headX = head.getLayoutX();
        double headY = head.getLayoutY();

        if (headX < 0) {
            head.setLayoutX(WINDOW_WIDTH - 20);
        }
        if (headX >= WINDOW_WIDTH) {
            head.setLayoutX(0);
        }

        if (headY < 0) {
            head.setLayoutY(WINDOW_HEIGHT - 20);
        }

        if (headY >= WINDOW_HEIGHT) {
            head.setLayoutY(0);
        }
    }

    public ImageView getHead() {
        return bodySegments.get(0);
    }

    public void showGameOver() {
        gameOverText.setVisible(true);
    }

    public void hideGameOver() {
        gameOverText.setVisible(false);
    }

    public void showReloadText() {
        reloadText.setVisible(true);
    }

    public void hideReloadText() {
        reloadText.setVisible(false);
    }

    public boolean getpause() {
        return paused;
    }

    public void setpause(boolean paused) {
        this.paused = paused;
    }

}
