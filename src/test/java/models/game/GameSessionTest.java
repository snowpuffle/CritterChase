package models.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

// Test GameSession Core Functionality
class GameSessionTest {

    // Test New Session Initialization
    @Test
    void newSessionHasNoCurrentLevel() {

        // Create GameSession Instance
        GameSession session = new GameSession();

        // Verify Session Has Not Started
        assertFalse(session.isStarted());

        // Verify No Current Level Exists
        assertEquals(0, session.getCurrentLevelNumber());
    }

    // Test Score Persistence
    @Test
    void scoreCanPersistAcrossLevels() {

        // Create GameSession Instance
        GameSession session = new GameSession();

        // Add Points to Session Score
        session.getScore().addPoints(100);

        // Verify Score Value Persists
        assertEquals(100, session.getScore().getPoints());
    }

    // Test Session Reset Functionality
    @Test
    void resetClearsSessionState() {

        // Create GameSession Instance
        GameSession session = new GameSession();

        // Add Points To Session Score
        session.getScore().addPoints(100);

        // Reset Session State
        session.reset();

        // Verify Score Reset
        assertEquals(0, session.getScore().getPoints());

        // Verify Session Returned To Initial State
        assertFalse(session.isStarted());
    }
}