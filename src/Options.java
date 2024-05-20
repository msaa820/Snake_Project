import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Options extends Scene {
    private Snake snake;

    public static MediaPlayer mediaPlayer;
    public static boolean isPlaying = true; // Track if the music should be playing

    // Singleton pattern for MediaPlayer
    private static MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            Media sound = new Media(new File("E:/JAVA/Snake_projectt/src/sound/Music.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
        }
        return mediaPlayer;
    }

    public Options(Stage stage) {
        super(new VBox(), 800, 600);

        VBox root = (VBox) this.getRoot();
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        Image backgroundImage = new Image("images/optionsbackground.jpg");

        // Set background properties
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        // Apply background to the root VBox
        root.setBackground(new Background(background));

        // Get or create the singleton MediaPlayer instance
        mediaPlayer = getMediaPlayer();

        // Initialize play/pause checkbox
        CheckBox playPauseCheckbox = new CheckBox("Music");
        playPauseCheckbox.setSelected(isPlaying); // Set checkbox based on music state

        // Play or pause music based on isPlaying state
        if (isPlaying) {
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
        }

        playPauseCheckbox.setOnAction(e -> {
            if (playPauseCheckbox.isSelected()) {
                mediaPlayer.play();
                isPlaying = true;
            } else {
                mediaPlayer.pause();
                isPlaying = false;
            }
        });

        // Style the checkbox
        playPauseCheckbox.setStyle(
                "-fx-font-size: 50px; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-color: #1300FF; " +
                        "-fx-border-radius: 40; " +
                        "-fx-padding: 20px; " +
                        "-fx-font-family: 'Arial'; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 5, 0, 0, 1);"); // Set background color to
                                                                                           // white

        // Initialize back button

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

        // Add components to the root layout
        root.getChildren().addAll(playPauseCheckbox, backButton);
    }

}
