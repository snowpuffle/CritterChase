package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import models.levels.Level;
import models.utils.Direction;
import models.utils.LevelFactory;

// GameController Controls Gameplay and User Input
public class GameController {

    // JavaFX UI Elements
    @FXML
    private Pane gamePane;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label healthLabel;

    @FXML
    private Label levelLabel;

    // Game Controller Attributes
    private Level level;
    private LevelRenderer levelRenderer;
    private int currentLevelNumber = 1;
    private static final int MAX_LEVEL = 2;

    // Initialize the Game
    @FXML
    public void initialize() {
        Platform.runLater(this::startLevel);
    }

    // Start the Level
    private void startLevel() {

        // Create the Current Level
        level = LevelFactory.createLevel(currentLevelNumber);

        // Create the Level Renderer
        levelRenderer = new LevelRenderer(gamePane, level.getPlayer(), level.getEnemyManager(), level.getGameBoard());

        // Draw Level First Time
        levelRenderer.drawLevel();

        // Update the HUD
        updateHUD();

        // Set Focus to the Game Pane for Keyboard Input
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();

        // Set Key Press Event Handler
        gamePane.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    // Handle Keyboard Input
    private void handleKeyPress(KeyCode keyCode) {

        // Convert KeyCode to Direction
        Direction direction = convertKeyToDirection(keyCode);

        // Ignore Invalid Input
        if (direction == null) {
            return;
        }

        // Process the Turn
        boolean moved = level.takeTurn(direction);

        // Stop if the Player Did Not Move
        if (!moved) {
            return;
        }

        // Check if the Player Died During the Turn
        if (!level.getHealth().isAlive()) {
            updateHUD();
            handleGameOver();
            return;
        }

        // Update the Level Renderer
        levelRenderer.updateLevel();

        // Update the HUD
        updateHUD();

        // Check if the Level is Complete
        if (level.isLevelComplete()) {
            startNextLevel();
        }
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

    // Start the Next Level or Complete the Game
    private void startNextLevel() {

        // Check if the Player Completed the Final Level
        if (currentLevelNumber >= MAX_LEVEL) {
            handleGameWon();
            return;
        }

        // Move to the Next Level
        currentLevelNumber++;

        // Start the Next Level
        startLevel();
    }

    // Update the HUD Labels
    private void updateHUD() {
        scoreLabel.setText("SCORE: " + level.getScore().getPoints());
        levelLabel.setText("LEVEL: " + level.getLevelNumber());
        healthLabel.setText("HEALTH: " + level.getHealth().getCurrentHealth());
    }

    // Handle Game Over
    private void handleGameOver() {
        try {
            GameOverController controller = (GameOverController) SceneManager.show("gameover.fxml");

            controller.setResult("GAME OVER");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Handle Game Won
    private void handleGameWon() {
        try {
            GameOverController controller = (GameOverController) SceneManager.show("gameover.fxml");

            controller.setResult("GAME WON!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}