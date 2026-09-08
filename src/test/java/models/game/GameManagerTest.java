package models.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// Test GameManager Core Functionality
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
        assertEquals(1, manager.getCurrentLevelNumber());
    }

    // Test Level Advancement
    @Test
    void nextLevelAdvancesLevel() {

        // Create GameManager Instance
        GameManager manager = new GameManager();

        // Start New Game Session
        manager.startGame();

        // Advance Game To Next Level
        boolean advanced = manager.nextLevel();

        // Verify Level Advancement
        assertTrue(advanced);

        // Verify Second Level Loaded
        assertEquals(2, manager.getCurrentLevelNumber());
    }

    // Test Game Restart Functionality
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
        assertEquals(1, manager.getCurrentLevelNumber());
    }
}