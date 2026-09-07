package models;

import models.levels.Level;
import models.utils.LevelFactory;

// GameManager Manages the Game State, Levels, and Progression
public class GameManager {

    // Game Settings
    private static final int STARTING_LEVEL = 1;
    private static final int MAX_LEVEL = 2;

    // Current Game State
    private int currentLevelNumber;
    private Level currentLevel;

    // Start the Game
    public void startGame() {
        currentLevelNumber = STARTING_LEVEL;
        startLevel();
    }

    // Start the Current Level
    private void startLevel() {
        currentLevel = LevelFactory.createLevel(currentLevelNumber);
    }

    // Move to the Next Level
    public boolean nextLevel() {

        // Check if the Final Level is Complete
        if (currentLevelNumber >= MAX_LEVEL) {
            return false;
        }

        // Move to the Next Level
        currentLevelNumber++;

        // Create the Next Level
        startLevel();
        return true;
    }

    // Restart the Game
    public void restartGame() {
        startGame();
    }

    // Check if the Player has Completed the Final Level
    public boolean isGameWon() {
        return currentLevelNumber >= MAX_LEVEL && currentLevel != null && currentLevel.isLevelComplete();
    }

    // Check if the Player Has Lost
    public boolean isGameOver() {
        return currentLevel != null && !currentLevel.getHealth().isAlive();
    }

    // Get the Current Level
    public Level getCurrentLevel() {
        return currentLevel;
    }

    // Get the Current Level Number
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    // Check if a Game Has Started
    public boolean isGameStarted() {
        return currentLevel != null;
    }
}