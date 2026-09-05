package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import models.levels.Level;
import models.levels.Level_1;
import models.utils.Direction;

// GameController Controls Gameplay and User Input
public class GameController {

    // Game Pane
    @FXML
    private Pane gamePane;

    // HUD Labels
    @FXML
    private Label scoreLabel;

    @FXML
    private Label healthLabel;

    @FXML
    private Label levelLabel;

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

        // Update the Health and Score Immediately
        updateHUD();

        // Check if the Player Died from Enemy Collision
        if (!level.getHealth().isAlive()) {
            handleGameOver();
            return;
        }

        // Stop if Player Did Not Move
        if (!moved) {
            return;
        }

        // Move Enemies
        level.moveEnemies();

        // Check if the Player Died from Enemy Attack
        if (!level.getHealth().isAlive()) {
            updateHUD();
            handleGameOver();
            return;
        }

        // Redraw Level
        levelRenderer.updateLevel();

        // Update the Health and Score
        updateHUD();
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

        // Show Game Over Scene
        try {
            SceneManager.show("gameOver.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateHUD() {
        scoreLabel.setText("SCORE: " + level.getScore().getPoints());
        levelLabel.setText("LEVEL: " + level.getLevelNumber());
        healthLabel.setText("HEALTH: " + level.getHealth().getCurrentHealth());
    }
}