package controllers;

import java.io.IOException;

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

    // Shows a New Scene and Returns Its Controller
    public static <T> T show(String fxmlFile) throws IOException {

        // Load the FXML File
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/views/" + fxmlFile));

        // Create a New Scene from the FXML File
        Scene scene = new Scene(loader.load());

        // Set and Show the New Scene on the Primary Stage
        stage.setScene(scene);
        stage.show();

        // Return the Controller Created by FXMLLoader
        return loader.getController();
    }
}