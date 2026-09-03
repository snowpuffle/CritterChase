package controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// SceneManager Manages the Scenes in the Application
// Allows Switching between Different FXML Views
public class SceneManager {

    // The Primary Stage of the Application
    private static Stage stage;

    // Sets the Primary Stage for the Application
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    // Shows a New Scene based on the Provided FXML File
    public static void show(String fxmlFile) throws Exception {

        // Load the FXML File and Create a New Scene.
        FXMLLoader loader = new FXMLLoader(
                SceneManager.class.getResource("/views/" + fxmlFile));

        // Create a New Scene from the Loaded FXML
        Scene scene = new Scene(loader.load());

        // Set the New Scene on the Primary Stage and Show it
        stage.setScene(scene);
        stage.show();
    }
}