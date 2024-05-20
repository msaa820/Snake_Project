import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GamePopup {
    private Snake snake;
    private Stage popupStage;
    private boolean resumeClicked = false;

    public GamePopup(Snake snake, Stage stage) {
        this.snake = snake;
        // Create the pop-up window
        popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Pause Menu");
        popupStage.setResizable(false);
        popupStage.initStyle(StageStyle.UNDECORATED);
        // Create buttons
        Button resumeButton = new Button("Resume");
        Button toggleMusicButton = new Button("Music");
        Button backbutton = new Button("Back");
        resumeButton.setStyle(
                "-fx-background-color: #0059FF; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 100px; " +
                        "-fx-pref-height: 40px;");
        toggleMusicButton.setStyle(
                "-fx-background-color: #0059FF; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 100px; " +
                        "-fx-pref-height: 40px;");
        backbutton.setStyle(
                "-fx-background-color: #0059FF; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-pref-width: 100px; " +
                        "-fx-pref-height: 40px;");

        DropShadow shadow = new DropShadow();

        resumeButton.setOnMouseEntered(e -> resumeButton.setEffect(shadow));
        resumeButton.setOnMouseExited(e -> resumeButton.setEffect(null));
        resumeButton.setOnMousePressed(e -> resumeButton.setEffect(null));
        toggleMusicButton.setOnMouseEntered(e -> toggleMusicButton.setEffect(shadow));
        toggleMusicButton.setOnMouseExited(e -> toggleMusicButton.setEffect(null));
        toggleMusicButton.setOnMousePressed(e -> toggleMusicButton.setEffect(null));
        backbutton.setOnMouseEntered(e -> backbutton.setEffect(shadow));
        backbutton.setOnMouseExited(e -> backbutton.setEffect(null));
        backbutton.setOnMousePressed(e -> backbutton.setEffect(null));

        // Add event handlers
        resumeButton.setOnAction(event -> {
            resumeClicked = true;
            snake.setpause(false); // Corrected method name
            popupStage.close();
        });
        // new MenuScene(popupStage);
        backbutton.setOnAction(event -> {
            popupStage.close();
            stage.setScene(new Levels(stage)); // Platform.exit();
        });

        toggleMusicButton.setOnAction(e -> {
            if (Options.isPlaying) {
                Options.mediaPlayer.pause();
                Options.isPlaying = false;
            } else {
                Options.mediaPlayer.play();
                Options.isPlaying = true;
            }
        });

        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(resumeButton, toggleMusicButton, backbutton);
        layout.setAlignment(Pos.CENTER);

        Image backgroundImage = new Image("images/pop.png");

        // Set background properties
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        // Apply background to the root VBox

        layout.setBackground(new Background(background));

        // Scene
        Scene scene = new Scene(layout, 284, 240);
        popupStage.setScene(scene);
    }

    // Method to show the pop-up window
    public void showPopup() {
        popupStage.showAndWait();
    }

    // Getter method to check if resume button was clicked
    public boolean isResumeClicked() {
        return resumeClicked;
    }
}
