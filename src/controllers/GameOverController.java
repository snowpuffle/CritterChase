package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

// GameOverController Controls the Game Over / Game Won Screen
public class GameOverController {

    @FXML
    private Button backButton;

    @FXML
    private Label totalScoreLabel;

    @FXML
    private Label resultLabel;

    // Set the Result Message (Game Over or Game Won)
    @FXML
    public void setResult(String result) {
        resultLabel.setText(result);
    }

    @FXML
    public void setScore(int score, int maxScore) {
        totalScoreLabel.setText("TOTAL SCORE: " + score + " / " + maxScore);
    }

    @FXML
    private void handleMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}