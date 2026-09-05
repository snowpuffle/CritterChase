package controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.levels.Level;
import models.levels.Level_1;
import models.utils.Direction;

// GameController Controls Gameplay and User Input
public class GameController {

    // Game Pane
    @FXML
    private Pane gamePane;

    // Current Level
    private Level level;

    // Level Renderer
    private LevelRenderer levelRenderer;

    // Initialize the Game
    @FXML
    public void initialize() {
        Platform.runLater(this::startLevel);
    }

    // Start Level 1
    private void startLevel() {

        level = new Level_1();

        levelRenderer = new LevelRenderer(
                gamePane,
                level.getPlayer(),
                level.getEnemyManager(),
                level.getGameBoard());

        // Draw Level First Time
        levelRenderer.drawLevel();

        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();

        gamePane.setOnKeyPressed(
                event -> handleKeyPress(event.getCode()));
    }

    // Handle Keyboard Input
    private void handleKeyPress(KeyCode keyCode) {

        // Convert KeyCode to Direction
        Direction direction = convertKeyToDirection(keyCode);

        // Ignore Invalid Input
        if (direction == null) {
            return;
        }

        // Move the Player
        boolean moved = level.movePlayer(direction);

        // Stop if Player Did Not Move
        if (!moved) {
            return;
        }

        level.moveEnemies();

        // Check if the Player Died
        if (!level.getHealth().isAlive()) {
            handleGameOver();
            return;
        }

        // Redraw Level
        levelRenderer.updateLevel();
    }

    // Convert JavaFX KeyCode into Game Direction
    private Direction convertKeyToDirection(KeyCode keyCode) {

        switch (keyCode) {
            case W:
            case UP:
                return Direction.UP;

            case A:
            case LEFT:
                return Direction.LEFT;

            case S:
            case DOWN:
                return Direction.DOWN;

            case D:
            case RIGHT:
                return Direction.RIGHT;

            default:
                return null;
        }
    }

    // Handle Game Over
    private void handleGameOver() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/views/gameOver.fxml"));

            Stage stage = (Stage) gamePane.getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}