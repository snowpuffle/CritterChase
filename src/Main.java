import controllers.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

// Main Class for the Critter Chase Application
// Initializes the Application and Sets Up the Initial Scene
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Set the Primary Stage in the SceneManager
        SceneManager.setStage(stage);

        // Show the Initial Scene (Welcome Screen)
        SceneManager.show("welcome.fxml");

        // Set the Title of the Application Window
        stage.setTitle("Critter Chase");
    }

    public static void main(String[] args) {
        launch(args);
    }
}