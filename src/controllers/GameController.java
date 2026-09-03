package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
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
        // gamePane.setFocusTraversable(true);
        // gamePane.requestFocus();

        // Handle Keyboard Input
        // gamePane.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    // Handle Keyboard Input
    // private void handleKeyPress(KeyCode keyCode) {

    // boolean moved = false;

    // switch (keyCode) {

    // // Move Player Up
    // case W:
    // case UP:
    // moved = level.movePlayer(
    // Level.UP,
    // Level.NO_MOVEMENT);
    // break;

    // // Move Player Left
    // case A:
    // case LEFT:
    // moved = level.movePlayer(
    // Level.NO_MOVEMENT,
    // Level.LEFT);
    // break;

    // // Move Player Down
    // case S:
    // case DOWN:
    // moved = level.movePlayer(
    // Level.DOWN,
    // Level.NO_MOVEMENT);
    // break;

    // // Move Player Right
    // case D:
    // case RIGHT:
    // moved = level.movePlayer(
    // Level.NO_MOVEMENT,
    // Level.RIGHT);
    // break;

    // default:
    // return;
    // }

    // // Stop if Player Did Not Move
    // if (!moved) {
    // return;
    // }

    // // Check if Level is Complete
    // if (level.isLevelComplete()) {
    // return;
    // }

    // // Move Enemies
    // level.moveEnemies();

    // // Redraw Level
    // levelRenderer.drawLevel();
    // }
}