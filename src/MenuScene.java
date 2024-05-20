import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScene extends Scene {

    public MenuScene(Stage stage) {
        super(new VBox(), 800, 600); // Setting up the scene with a VBox layout and dimensions

        VBox root = (VBox) this.getRoot(); // Getting the root node of the Scene

        // Create background image
        Image backgroundImage = new Image("images/background.jpg");

        // Set background properties
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        // Apply background to the root VBox
        root.setBackground(new Background(background));

        // Create image views for buttons
        ImageView startImageView = new ImageView(new Image("images/start.png"));
        ImageView optionsImageView = new ImageView(new Image("images/options.png"));
        ImageView exitImageView = new ImageView(new Image("images/exit.png"));

        // Shadow
        // first button
        DropShadow shadow = new DropShadow();
        startImageView.setOnMouseEntered(e -> startImageView.setEffect(shadow));
        startImageView.setOnMouseExited(e -> startImageView.setEffect(null));
        startImageView.setOnMousePressed(e -> startImageView.setEffect(null));
        // sceond button
        optionsImageView.setOnMouseEntered(e -> optionsImageView.setEffect(shadow));
        optionsImageView.setOnMouseExited(e -> optionsImageView.setEffect(null));
        optionsImageView.setOnMousePressed(e -> optionsImageView.setEffect(null));
        // third button
        exitImageView.setOnMouseEntered(e -> exitImageView.setEffect(shadow));
        exitImageView.setOnMouseExited(e -> exitImageView.setEffect(null));
        exitImageView.setOnMousePressed(e -> exitImageView.setEffect(null));

        // Set image sizes
        startImageView.setFitWidth(250);
        startImageView.setFitHeight(100);
        optionsImageView.setFitWidth(250);
        optionsImageView.setFitHeight(100);
        exitImageView.setFitWidth(250);
        exitImageView.setFitHeight(100);

        // Set button actions
        // set scene of levels
        Levels levels = new Levels(stage);
        startImageView.setOnMouseClicked(e -> stage.setScene(levels));
        Options options = new Options(stage);
        optionsImageView.setOnMouseClicked(e -> stage.setScene(options));
        exitImageView.setOnMouseClicked(e -> Platform.exit());

        // Add image views to the root VBox
        root.getChildren().addAll(startImageView, optionsImageView, exitImageView);
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

    }

}
