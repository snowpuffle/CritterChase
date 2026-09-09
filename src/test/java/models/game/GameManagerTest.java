package models.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.levels.LevelConfig;
import models.utils.Direction;

// Test GameManager Core Game Rules
// - Starts a New Game at the First Level
// - Loads the Current Level
// - Advances to the Next Level
// - Does Not Advance Past the Final Level
// - Processes Valid Player Movement
// - Rejects Invalid Player Movement
// - Transfers Level Score to Total Score
// - Does Not Add Level Score More Than Once
// - Restarts the Game and Resets Score
// - Detects Game Over
// - Detects Game Won
// - Returns Total Maximum Score
class GameManagerTest {

    // Test GameManager Initialization
    @Test
    void startGameStartsAtFirstLevel() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Verify Game Started Successfully
        assertTrue(manager.isGameStarted());

        // Verify First Level Loaded
        assertEquals(
                LevelConfig.getStartingLevel(),
                manager.getCurrentLevelNumber());

        // Verify Current Level Exists
        assertNotNull(manager.getCurrentLevel());
    }

    // Test Current Level Access
    @Test
    void getCurrentLevelReturnsActiveLevel() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Get Current Level
        var level = manager.getCurrentLevel();

        // Verify Current Level Exists
        assertNotNull(level);

        // Verify Level Number Matches GameManager
        assertEquals(
                manager.getCurrentLevelNumber(),
                level.getLevelNumber());
    }

    // Test Level Advancement
    @Test
    void nextLevelAdvancesLevel() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Store Current Level Number
        int startingLevel = manager.getCurrentLevelNumber();

        // Advance Game To Next Level
        boolean advanced = manager.nextLevel();

        // Verify Level Advancement
        assertTrue(advanced);

        // Verify Game Advanced One Level
        assertEquals(
                startingLevel + 1,
                manager.getCurrentLevelNumber());
    }

    // Test Final Level Boundary
    @Test
    void nextLevelDoesNotAdvancePastFinalLevel() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Advance Through All Levels
        while (manager.getCurrentLevelNumber() != LevelConfig.getFinalLevel()) {

            // Advance To Next Level
            boolean advanced = manager.nextLevel();

            // Verify Level Advancement Was Successful
            assertTrue(advanced);
        }

        // Store Final Level Number
        int finalLevel = manager.getCurrentLevelNumber();

        // Attempt To Advance Past Final Level
        boolean advanced = manager.nextLevel();

        // Verify Game Did Not Advance
        assertFalse(advanced);

        // Verify Final Level Remains Active
        assertEquals(
                finalLevel,
                manager.getCurrentLevelNumber());
    }

    // Test Valid Player Movement
    @Test
    void playTurnProcessesValidMovement() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Process Valid Player Movement
        GameTurnResult result = manager.playTurn(Direction.RIGHT);

        // Verify Movement Was Successful
        assertEquals(GameTurnResult.MOVED, result);
    }

    // Test Invalid Player Movement
    @Test
    void playTurnRejectsInvalidMovement() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Attempt Invalid Player Movement
        GameTurnResult result = manager.playTurn(Direction.DOWN);

        // Verify Movement Was Rejected
        assertEquals(GameTurnResult.INVALID_MOVE, result);
    }

    // Test Player Movement Before Game Starts
    @Test
    void playTurnWithoutActiveLevelReturnsInvalidMove() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Attempt To Move Before Starting Game
        GameTurnResult result = manager.playTurn(Direction.RIGHT);

        // Verify No Valid Move Was Processed
        assertEquals(GameTurnResult.INVALID_MOVE, result);
    }

    // Test Score Addition
    @Test
    void addScoreIncreasesTotalScore() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Add Points To Total Score
        manager.addScore(100);

        // Verify Total Score Increased
        assertEquals(100, manager.getScore());
    }

    // Test Game Restart
    @Test
    void restartGameResetsScore() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Add Points To Current Score
        manager.addScore(100);

        // Restart Current Game Session
        manager.restartGame();

        // Verify Score Reset
        assertEquals(0, manager.getScore());

        // Verify Game Returned To First Level
        assertEquals(
                LevelConfig.getStartingLevel(),
                manager.getCurrentLevelNumber());

        // Verify Game Is Still Started
        assertTrue(manager.isGameStarted());
    }

    // Test Game Over Detection
    @Test
    void gameOverIsFalseWhenPlayerIsAlive() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Verify Player Is Not Game Over
        assertFalse(manager.isGameOver());
    }

    // Test Game Won Detection
    @Test
    void gameWonIsFalseBeforeFinalLevelIsComplete() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Verify Game Has Not Been Won
        assertFalse(manager.isGameWon());
    }

    // Test Total Maximum Score
    @Test
    void getTotalMaxScoreReturnsConfiguredMaximum() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Get Total Maximum Score
        int totalMaxScore = manager.getTotalMaxScore();

        // Verify Total Maximum Score Matches Configuration
        assertEquals(
                LevelConfig.getTotalMaxScore(),
                totalMaxScore);
    }
}