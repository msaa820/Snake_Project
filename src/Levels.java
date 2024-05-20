import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Levels extends Scene {

    public Levels(Stage stage) {
        super(new VBox(), 800, 600);
        VBox root = (VBox) this.getRoot();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        HBox imageBox = new HBox();
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setSpacing(20);

        ImageView levelone = new ImageView(new Image("images/Level1.png"));
        ImageView leveltwo = new ImageView(new Image("images/level2.png"));
        levelone.setFitWidth(385);
        levelone.setFitHeight(350);
        leveltwo.setFitWidth(385);
        leveltwo.setFitHeight(350);
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back");
        backButton.setStyle(
                "-fx-background-color: #4CAF50; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 100px; " +
                        "-fx-pref-height: 40px;");
        backButton.setOnAction(e -> {
            stage.setScene(new MenuScene(stage));
        });
        DropShadow shadow = new DropShadow();

        backButton.setOnMouseEntered(e -> backButton.setEffect(shadow));
        backButton.setOnMouseExited(e -> backButton.setEffect(null));
        backButton.setOnMousePressed(e -> backButton.setEffect(null));

        Image backgroundImage = new Image("images/levels.jpg");

        // Set background properties
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        // Apply background to the root VBox
        root.setBackground(new Background(background));

        // first button
        FirstLevel firstLevel = new FirstLevel(stage);

        levelone.setOnMouseClicked(e -> {
            stage.setScene(firstLevel);

        });
        levelone.setOnMouseEntered(e -> levelone.setEffect(shadow));
        levelone.setOnMouseExited(e -> levelone.setEffect(null));
        levelone.setOnMousePressed(e -> levelone.setEffect(null));
        // second button
        SecondLevel secondLevel = new SecondLevel(stage);
        leveltwo.setOnMouseClicked(e -> {
            stage.setScene(secondLevel);
        });
        leveltwo.setOnMouseEntered(e -> leveltwo.setEffect(shadow));
        leveltwo.setOnMouseExited(e -> leveltwo.setEffect(null));
        leveltwo.setOnMousePressed(e -> leveltwo.setEffect(null));

        imageBox.getChildren().addAll(levelone, leveltwo);
        root.getChildren().addAll(imageBox, backButton);

    }
}
