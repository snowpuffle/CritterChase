import javafx.application.Application;
import javafx.stage.Stage;
import controllers.SceneManager;

// Main Class for the Critter Chase Application
// Initializes the Application and Sets Up the Initial Scene
public class Main extends Application {

    private static final double WINDOW_WIDTH = 1200;
    private static final double WINDOW_HEIGHT = 900;

    @Override
    public void start(Stage stage) throws Exception {

        // Set the Primary Stage in the SceneManager
        SceneManager.setStage(stage);

        // Show the Initial Scene (Welcome Screen)
        SceneManager.show("welcome.fxml");

        // Set the Title of the Application Window
        stage.setTitle("Critter Chase");

        // Fixed window size
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);

        // Prevent resizing and maximizing
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}