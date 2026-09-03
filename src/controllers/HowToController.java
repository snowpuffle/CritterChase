package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

// HowToController is Responsible for Managing the How-To Screen of the Application
// Provides Functionality to Navigate Back to the Main Menu
public class HowToController {

    @FXML
    private Button backButton;

    @FXML
    private void handleBack() {
        try {
            SceneManager.show("menu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}