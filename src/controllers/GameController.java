package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import models.GameManager;
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

    // Game Manager
    private final GameManager gameManager = new GameManager();

    // Level Renderer
    private LevelRenderer levelRenderer;

    // Initialize the Game
    @FXML
    public void initialize() {
        Platform.runLater(this::startGame);
    }

    // Start the Level
    private void startGame() {

        // Start a New Game
        gameManager.startGame();

        // Display the Current Level
        displayCurrentLevel();

        // Set Focus to the Game Pane for Keyboard Input
        gamePane.setFocusTraversable(true);
        gamePane.requestFocus();

        // Set Key Press Event Handler
        gamePane.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
    }

    // Display the Current Level
    private void displayCurrentLevel() {
        // Get the Current Level from the Game Manager
        Level level = gameManager.getCurrentLevel();

        // Create the Level Renderer
        levelRenderer = new LevelRenderer(gamePane, level.getPlayer(), level.getEnemyManager(),
                level.getGameBoard());

        // Draw the Level
        levelRenderer.drawLevel();
        // Update the HUD
        updateHUD();
    }

    // Handle Keyboard Input
    private void handleKeyPress(KeyCode keyCode) {

        // Convert KeyCode to Direction
        Direction direction = convertKeyToDirection(keyCode);

        // Ignore Invalid Input
        if (direction == null) {
            return;
        }

        // Get the Current Level
        Level level = gameManager.getCurrentLevel();

        // Process the Turn
        boolean moved = level.takeTurn(direction);

        // Stop if the Player Did Not Move
        if (!moved) {
            return;
        }

        // Check if the Player Died During the Turn
        if (gameManager.isGameOver()) {
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
            handleLevelComplete();
        }
    }

    // Handle Level Completion
    private void handleLevelComplete() {

        // Try to Move to the Next Level
        boolean hasNextLevel = gameManager.nextLevel();

        // If Therem Is No Next Level, the Game Is Won
        if (!hasNextLevel) {
            handleGameWon();
            return;
        }

        // Display the Next Level
        displayCurrentLevel();

        // Keep Keyboard Focus on the Game Pane
        gamePane.requestFocus();
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

    // Update the HUD Labels
    private void updateHUD() {
        Level level = gameManager.getCurrentLevel();
        scoreLabel.setText("SCORE: " + gameManager.getScore());
        levelLabel.setText("LEVEL: " + gameManager.getCurrentLevelNumber());
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