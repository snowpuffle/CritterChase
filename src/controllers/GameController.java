package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import models.levels.Level;
import models.levels.Level_1;

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

        // Create Level 1
        level = new Level_1();

        // Create Level Renderer
        levelRenderer = new LevelRenderer(gamePane, level.getPlayer(), level.getEnemyManager(), level.getGameBoard());

        // Draw Level
        levelRenderer.drawLevel();

        // Enable Keyboard Input
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();

        // Handle Keyboard Input
        gamePane.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    // Convert JavaFX KeyCode into Player Input
    private String convertKeyToInput(KeyCode keyCode) {

        switch (keyCode) {

            // Move the Player Up
            case W:
            case UP:
                return "w";

            // Move the Player Left
            case A:
            case LEFT:
                return "a";

            // Move the Player Down
            case S:
            case DOWN:
                return "s";

            // Move the Player Right
            case D:
            case RIGHT:
                return "d";

            // Invalid Input
            default:
                return null;
        }
    }

    // Handle Keyboard Input
    private void handleKeyPress(KeyCode keyCode) {

        // Convert JavaFX KeyCode into Player Input
        String playerInput = convertKeyToInput(keyCode);

        // Ignore Invalid Input
        if (playerInput == null) {
            return;
        }

        // Move the Player
        boolean moved = handleMovement(playerInput);

        // Stop if Player Did Not Move
        if (!moved) {
            return;
        }

        // Redraw Level
        levelRenderer.drawLevel();
    }

    private boolean handleMovement(String playerInput) {
        switch (playerInput) {
            case "w":
                return level.movePlayer(Level.UP, Level.NO_MOVEMENT);
            case "a":
                return level.movePlayer(Level.NO_MOVEMENT, Level.LEFT);
            case "s":
                return level.movePlayer(Level.DOWN, Level.NO_MOVEMENT);
            case "d":
                return level.movePlayer(Level.NO_MOVEMENT, Level.RIGHT);
            default:
                return false;
        }
    }
}