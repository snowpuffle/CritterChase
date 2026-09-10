package models.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.levels.Level;

// Test GameSession Core Game Rules
// - Initializes Without an Active Level
// - Starts When a Level Is Set
// - Tracks the Current Level Number
// - Persists Score Across Levels
// - Resets Score and Session State
class GameSessionTest {

    // Test New Session Initialization
    @Test
    void newSessionHasNoCurrentLevel() {

        // Create New GameSession
        GameSession session = new GameSession();

        // Verify Session Has Not Started
        assertFalse(session.isStarted());

        // Verify No Current Level Is Set
        assertEquals(0, session.getCurrentLevelNumber());

        // Verify Current Level Is Null
        assertEquals(null, session.getCurrentLevel());
    }

    // Test Current Level Assignment
    @Test
    void settingCurrentLevelStartsSession() {

        // Create New GameSession
        GameSession session = new GameSession();

        // Create GameManager to Obtain a Valid Level
        GameManager manager = new GameManager();

        // Start Game to Create First Level
        manager.startGame();

        // Get Current Level
        Level level = manager.getCurrentLevel();

        // Store Level in GameSession
        session.setCurrentLevel(level);

        // Verify Session Has Started
        assertTrue(session.isStarted());

        // Verify Current Level Exists
        assertNotNull(session.getCurrentLevel());

        // Verify Current Level Number
        assertEquals(level.getLevelNumber(), session.getCurrentLevelNumber());
    }

    // Test Score Persistence
    @Test
    void scoreCanPersistAcrossLevels() {

        // Create New GameSession
        GameSession session = new GameSession();

        // Add Points Through GameSession
        session.addScore(100);

        // Verify Score Value Persists
        assertEquals(100, session.getScore());

        // Add Additional Points
        session.addScore(50);

        // Verify Score Accumulates
        assertEquals(150, session.getScore());
    }

    // Test Session Reset Functionality
    @Test
    void resetClearsSessionState() {

        // Create New GameSession
        GameSession session = new GameSession();

        // Create GameManager to Obtain a Valid Level
        GameManager manager = new GameManager();

        // Start Game to Create First Level
        manager.startGame();

        // Set Current Level
        session.setCurrentLevel(manager.getCurrentLevel());

        // Add Points Through GameSession
        session.addScore(100);

        // Verify Session Is Active
        assertTrue(session.isStarted());

        // Reset Session State
        session.reset();

        // Verify Score Reset
        assertEquals(0, session.getScore());

        // Verify Session Returned To Initial State
        assertFalse(session.isStarted());

        // Verify Current Level Was Cleared
        assertEquals(null, session.getCurrentLevel());

        // Verify Current Level Number Was Reset
        assertEquals(0, session.getCurrentLevelNumber());
    }
}