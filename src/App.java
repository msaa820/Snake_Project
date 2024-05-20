import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {

        MenuScene menuScene = new MenuScene(stage); // Create menu scene
        stage.setScene(menuScene); // Set menu scene as the initial scene
        stage.setTitle("SNAKE GAME");
        stage.setResizable(false);
        stage.getIcons().add(new Image(""));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
