package models.game;

import models.levels.Level;
import models.levels.LevelConfig;
import models.levels.LevelFactory;
import models.objects.Score;
import models.utils.Direction;

// GameManager Controls Game Flow and Level Progression
public class GameManager {

    // Game Settings
    private static final int STARTING_LEVEL = LevelConfig.getStartingLevel();

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

        // Create a New Score for the Current Level
        Level level = LevelFactory.createLevel(levelNumber, new Score());

        // Store the Level in the Current Session
        gameSession.setCurrentLevel(level);
    }

    // Move to the Next Level
    public boolean nextLevel() {

        // Get the Current Level Number
        int currentLevel = gameSession.getCurrentLevelNumber();

        // Do Not Advance Past the Final Level
        if (currentLevel >= LevelConfig.getMaxLevel()) {
            return false;
        }

        // Start the Next Level
        startLevel(currentLevel + 1);

        return true;
    }

    // Process the Player's Turn
    public GameTurnResult playTurn(Direction direction) {

        // Get the Current Level
        Level level = gameSession.getCurrentLevel();

        // No Active Level Means No Valid Move
        if (level == null) {
            return GameTurnResult.INVALID_MOVE;
        }

        // Process the Player's Turn
        boolean moved = level.takeTurn(direction);

        // Invalid Movement Does Not Consume a Turn
        if (!moved) {
            return GameTurnResult.INVALID_MOVE;
        }

        // Check if the Player Died During the Turn
        if (isGameOver()) {
            return GameTurnResult.GAME_OVER;
        }

        // Check if the Player Reached the Exit
        if (level.isLevelComplete()) {

            // Add This Level's Score to the Total Score
            addCurrentLevelScore();

            // Check if the Final Level Was Completed
            if (level.getLevelNumber() == LevelConfig.getMaxLevel()) {
                return GameTurnResult.GAME_WON;
            }

            // Advance to the Next Level
            nextLevel();

            return GameTurnResult.LEVEL_COMPLETE;
        }

        // Player Moved Without Completing the Level
        return GameTurnResult.MOVED;
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
                && level.getLevelNumber() == LevelConfig.getMaxLevel()
                && level.isLevelComplete();
    }

    // Get Total Max Score
    public int getTotalMaxScore() {
        int totalMaxScore = 0;

        for (int levelNumber = 1; levelNumber <= LevelConfig.getMaxLevel(); levelNumber++) {

            Level level = LevelConfig.createLevel(levelNumber, new Score());
            totalMaxScore += level.getMaxScore();
        }

        return totalMaxScore;
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

    // Transfer the Completed Level's Score into the Persistent Total
    public void addCurrentLevelScore() {
        gameSession.getScore().addPoints(
                gameSession.getCurrentLevel().getScore().getPoints());
    }
}