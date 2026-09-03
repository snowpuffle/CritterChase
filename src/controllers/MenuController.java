package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    @FXML
    private Button startButton;

    @FXML
    private Button howToButton;

    @FXML
    private Button exitButton;

    // Handle the Start Game Button Click Event
    @FXML
    private void handleStartGame() {
        try {
            SceneManager.show("game.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle the How To Play Button Click Event
    @FXML
    private void handleHowTo() {
        try {
            SceneManager.show("howto.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle the Exit Button Click Event
    @FXML
    private void handleExit() {
        System.out.println("Exit clicked!");
    }
}
