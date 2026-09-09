package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

// Test GameOverController Core Screen Rules
// - Displays Game Won Image
// - Displays Game Over Image
// - Displays Final Score
class GameOverControllerTest {

    // Test Game Won Result
    @Test
    void setResultDisplaysGameWonImage() {

        // Create GameOverController Instance
        GameOverController controller = new GameOverController();

        // Create ImageView For Result
        ImageView resultImage = new ImageView();

        // Set Result Image Field
        setField(controller, "resultImage", resultImage);

        // Set Game Won Result
        controller.setResult("GAME WON!");

        // Verify Result Image Was Set
        assertNotNull(resultImage.getImage());
    }

    // Test Game Over Result
    @Test
    void setResultDisplaysGameOverImage() {

        // Create GameOverController Instance
        GameOverController controller = new GameOverController();

        // Create ImageView For Result
        ImageView resultImage = new ImageView();

        // Set Result Image Field
        setField(controller, "resultImage", resultImage);

        // Set Game Over Result
        controller.setResult("GAME OVER");

        // Verify Result Image Was Set
        assertNotNull(resultImage.getImage());
    }

    // Test Final Score
    @Test
    void setScoreDisplaysFinalScore() {

        // Create GameOverController Instance
        GameOverController controller = new GameOverController();

        // Create Label For Score
        Label scoreLabel = new Label();

        // Set Score Label Field
        setField(controller, "totalScoreLabel", scoreLabel);

        // Set Final Game Score
        controller.setScore(500, 1000);

        // Verify Final Score Is Displayed
        assertEquals("500 / 1000", scoreLabel.getText());
    }

    // Set Private JavaFX Field For Testing
    private void setField(
            GameOverController controller,
            String fieldName,
            Object value) {

        try {
            var field = GameOverController.class
                    .getDeclaredField(fieldName);

            field.setAccessible(true);
            field.set(controller, value);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}