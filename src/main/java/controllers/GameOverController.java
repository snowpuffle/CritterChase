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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

// GameOverController Controls the Game Over / Game Won Screen
public class GameOverController {

    @FXML
    private ImageView resultImage;

    @FXML
    private Label totalScoreLabel;

    @FXML
    private Button backButton;

    // Set the Result Image (Game Over or Game Won)
    @FXML
    public void setResult(String result) {

        boolean won = result != null && result.toLowerCase().contains("won");

        String imagePath = won
                ? "/assets/others/youwon_title.png"
                : "/assets/others/gameover_title.png";

        Image image = new Image(
                getClass().getResourceAsStream(imagePath));

        resultImage.setImage(image);
    }

    // Set the Final Game Score
    @FXML
    public void setScore(int score, int maxScore) {
        totalScoreLabel.setText(score + " / " + maxScore);
    }

    @FXML
    private void handleMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/menu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}