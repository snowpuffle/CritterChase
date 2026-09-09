package models.game;

import models.levels.Level;
import models.levels.LevelConfig;
import models.levels.LevelFactory;
import models.objects.Score;
import models.utils.Direction;

// GameManager Controls Game Flow and Level Progression
// GameManager Class Controls Overall Game Flow
public class GameManager {

    private final GameSession gameSession;
    private boolean currentLevelScoreAdded;

    // GameManager Constructor
    public GameManager() {
        this.gameSession = new GameSession();
        this.currentLevelScoreAdded = false;
    }

    // Start New Game
    public void startGame() {

        // Reset Previous Game Session
        gameSession.reset();

        // Create Starting Level
        int startingLevel = LevelConfig.getStartingLevel();

        // Create Starting Level Score
        Level level = LevelFactory.createLevel(
                startingLevel,
                new Score());

        // Store Starting Level in Game Session
        gameSession.setCurrentLevel(level);

        // Reset Level Score Tracking
        currentLevelScoreAdded = false;
    }

    // Process One Player Turn
    public GameTurnResult playTurn(Direction direction) {

        // Get Current Level
        Level level = gameSession.getCurrentLevel();

        // Reject Turns When No Level Exists
        if (level == null) {
            return GameTurnResult.INVALID_MOVE;
        }

        // Reject Turns After Game Over
        if (isGameOver()) {
            return GameTurnResult.INVALID_MOVE;
        }

        // Reject Turns After Game Won
        if (isGameWon()) {
            return GameTurnResult.INVALID_MOVE;
        }

        // Process Player Turn
        boolean moved = level.takeTurn(direction);

        // Check Game Over After Turn
        if (isGameOver()) {

            // Add Current Level Score Before Ending Game
            addCurrentLevelScore();

            // Return Game Over Result
            return GameTurnResult.GAME_OVER;
        }

        // Return Invalid Move When Player Cannot Move
        if (!moved) {
            return GameTurnResult.INVALID_MOVE;
        }

        // Check Level Completion
        if (level.isLevelComplete()) {

            // Add Completed Level Score
            addCurrentLevelScore();

            // Check Final Level Completion
            if (level.getLevelNumber() == LevelConfig.getFinalLevel()) {
                return GameTurnResult.GAME_WON;
            }

            // Move to Next Level
            nextLevel();

            // Return Level Complete Result
            return GameTurnResult.LEVEL_COMPLETE;
        }

        // Return Successful Movement Result
        return GameTurnResult.MOVED;
    }

    // Add Current Level Score to Overall Game Score
    private void addCurrentLevelScore() {

        // Prevent Duplicate Score Transfers
        if (currentLevelScoreAdded) {
            return;
        }

        // Get Current Level
        Level level = gameSession.getCurrentLevel();

        // Stop When No Current Level Exists
        if (level == null) {
            return;
        }

        // Add Current Level Score to Game Session
        gameSession.addScore(
                level.getScore().getPoints());

        // Mark Current Level Score as Added
        currentLevelScoreAdded = true;
    }

    // Move Game to Next Level
    private void nextLevel() {

        // Get Current Level
        Level currentLevel = gameSession.getCurrentLevel();

        // Stop When No Current Level Exists
        if (currentLevel == null) {
            return;
        }

        // Get Next Level Number
        int nextLevelNumber = LevelConfig.getNextLevel(
                currentLevel.getLevelNumber());

        // Stop When No Next Level Exists
        if (nextLevelNumber == -1) {
            return;
        }

        // Create Next Level
        Level nextLevel = LevelFactory.createLevel(
                nextLevelNumber,
                new Score());

        // Store Next Level in Game Session
        gameSession.setCurrentLevel(nextLevel);

        // Reset Level Score Tracking
        currentLevelScoreAdded = false;
    }

    // Check Game Over Status
    public boolean isGameOver() {

        // Get Current Level
        Level level = gameSession.getCurrentLevel();

        // Game Cannot Be Over Without Active Level
        if (level == null) {
            return false;
        }

        // Check Player Health
        return !level.getHealth().isAlive();
    }

    // Check Game Won Status
    public boolean isGameWon() {

        // Get Current Level
        Level level = gameSession.getCurrentLevel();

        // Game Cannot Be Won Without Active Level
        if (level == null) {
            return false;
        }

        // Check Final Level Number
        boolean finalLevel = level.getLevelNumber() == LevelConfig.getFinalLevel();

        // Check Level Completion
        boolean levelComplete = level.isLevelComplete();

        // Return Final Level Completion Status
        return finalLevel && levelComplete;
    }

    // Get Game Session
    public GameSession getGameSession() {
        return gameSession;
    }

    // Get Current Level
    public Level getCurrentLevel() {
        return gameSession.getCurrentLevel();
    }

    // Get Overall Game Score
    public int getScore() {

        // Return Total Game Session Score
        return gameSession.getScore();
    }

    // Get Total Maximum Score Across All Levels
    public int getTotalMaxScore() {

        // Return Total Maximum Score from Level Configuration
        return LevelConfig.getTotalMaxScore();
    }
}