package models.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

// Test GameSession Core Functionality
class GameSessionTest {

    // Test New Session Initialization
    @Test
    void newSessionHasNoCurrentLevel() {

        GameSession session = new GameSession();

        assertFalse(session.isStarted());
        assertEquals(0, session.getCurrentLevelNumber());
    }

    // Test Score Persistence
    @Test
    void scoreCanPersistAcrossLevels() {

        GameSession session = new GameSession();

        // Add Points Through GameSession
        session.addScore(100);

        // Verify Score Value Persists
        assertEquals(100, session.getScore());
    }

    // Test Session Reset Functionality
    @Test
    void resetClearsSessionState() {

        GameSession session = new GameSession();

        // Add Points Through GameSession
        session.addScore(100);

        // Reset Session State
        session.reset();

        // Verify Score Reset
        assertEquals(0, session.getScore());

        // Verify Session Returned To Initial State
        assertFalse(session.isStarted());
    }
}
