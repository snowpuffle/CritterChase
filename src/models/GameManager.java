package models;

import models.levels.Level;
import models.utils.LevelFactory;

// GameManager Controls Game Flow and Level Progression
public class GameManager {

    // Game Settings
    private static final int STARTING_LEVEL = 1;
    private static final int MAX_LEVEL = 2;

    // Current Game Session
    private final GameSession gameSession;

    // GameManager Constructor
    public GameManager() {
        gameSession = new GameSession();
    }

    // Start a New Game
    public void startGame() {
        gameSession.reset();
        startLevel(STARTING_LEVEL);
    }

    // Start a Level
    private void startLevel(int levelNumber) {

        // Create the Level Using the Existing Session Score
        Level level = LevelFactory.createLevel(levelNumber, gameSession.getScore());

        // Store the Level in the Current Session
        gameSession.setCurrentLevel(level);
    }

    // Move to the Next Level
    public boolean nextLevel() {

        // Get the Current Level Number
        int currentLevel = gameSession.getCurrentLevelNumber();

        // Do Not Advance Past the Final Level
        if (currentLevel >= MAX_LEVEL) {
            return false;
        }

        // Start the Next Level
        startLevel(currentLevel + 1);

        return true;
    }

    // Restart the Game
    public void restartGame() {
        startGame();
    }

    // Check if the Player Has Lost
    public boolean isGameOver() {
        Level level = gameSession.getCurrentLevel();

        return level != null && !level.getHealth().isAlive();
    }

    // Check if the Player Has Won
    public boolean isGameWon() {
        Level level = gameSession.getCurrentLevel();

        return level != null
                && level.getLevelNumber() == MAX_LEVEL
                && level.isLevelComplete();
    }

    // Get the Current Level
    public Level getCurrentLevel() {
        return gameSession.getCurrentLevel();
    }

    // Get the Current Game Session
    public GameSession getGameSession() {
        return gameSession;
    }

    // Get the Persistent Score
    public int getScore() {
        return gameSession.getScore().getPoints();
    }

    // Get the Current Level Number
    public int getCurrentLevelNumber() {
        return gameSession.getCurrentLevelNumber();
    }

    // Check if a Game Has Started
    public boolean isGameStarted() {
        return gameSession.isStarted();
    }
}