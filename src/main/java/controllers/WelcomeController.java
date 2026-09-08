package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

// WelcomeController is Responsible for Managing the Welcome Screen of the Application
// Initializes the Screen and Transitions to the Main Menu after a Delay
public class WelcomeController {

    @FXML
    private ProgressBar progressBar;

    @FXML
    public void initialize() {

        // Create a Timeline Animation to Fill the Progress Bar Over 2 Seconds
        Timeline loading = new Timeline(
                // Animate the Progress Bar from 0 to 1 over 2 seconds
                new KeyFrame(
                        Duration.seconds(2),
                        new KeyValue(progressBar.progressProperty(), 1.0)));

        // Set an Event Handler to Transition to the Main Menu Once the Animation is
        // Complete
        loading.setOnFinished(event -> {
            try {
                SceneManager.show("menu.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        loading.play();
    }
}