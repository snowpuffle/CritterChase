package controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import models.game.GameManager;
import models.game.GameTurnResult;
import models.levels.Level;
import models.utils.Direction;

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

    @FXML
    private ImageView levelBackground;

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

        // Update the Level Background
        updateLevelBackground(level.getBackgroundPath());

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

        // Process Turn Through Game Manager
        GameTurnResult result = gameManager.playTurn(direction);

        // Handle Turn Result
        handleTurnResult(result);
    }

    // Handle Turn Result
    private void handleTurnResult(GameTurnResult result) {

        switch (result) {

            // Nothing Changed
            case INVALID_MOVE:
                updateHUD();
                return;

            // Update Level and HUD After Valid Move
            case MOVED:
                levelRenderer.updateLevel();
                updateHUD();
                return;

            // Display the Next Level
            case LEVEL_COMPLETE:
                displayCurrentLevel();
                gamePane.requestFocus();
                return;

            // Update HUD and Display Game Over Screen
            case GAME_OVER:
                updateHUD();
                showGameResult("GAME OVER");
                return;

            // Update HUD and Display Game Won Screen
            case GAME_WON:
                updateHUD();
                showGameResult("GAME WON!");
                return;
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

    // Update the HUD Labels
    private void updateHUD() {
        Level level = gameManager.getCurrentLevel();
        scoreLabel.setText("SCORE: " + level.getScore().getPoints() + " / " + level.getMaxScore());
        levelLabel.setText("LEVEL: " + gameManager.getCurrentLevelNumber());
        healthLabel.setText("HEALTH: " + level.getHealth().getCurrentHealth());
    }

    // Show Game Over / Won Screen
    private void showGameResult(String result) {
        try {
            GameOverController controller = SceneManager.show("gameover.fxml");

            controller.setResult(result);

            controller.setScore(
                    gameManager.getScore(),
                    gameManager.getTotalMaxScore());

        } catch (IOException e) {
            System.err.println("Failed to Load the Game Result Screen.");
            e.printStackTrace();
        }
    }

    private void updateLevelBackground(String backgroundPath) {

        Image image = new Image(
                getClass().getResourceAsStream(backgroundPath));

        levelBackground.setImage(image);
    }
}