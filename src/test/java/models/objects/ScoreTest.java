package models.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// Test Score Core Functionality
class ScoreTest {

    // Test New Score Initialization
    @Test
    void newScoreStartsAtZero() {

        // Create Score Instance
        Score score = new Score();

        // Verify Initial Score Value
        assertEquals(0, score.getPoints());
    }

    // Test Score Point Addition
    @Test
    void addPointsIncreasesScore() {

        // Create Score Instance
        Score score = new Score();

        // Add First & Second Point Value
        score.addPoints(10);
        score.addPoints(25);

        // Verify Combined Score Value
        assertEquals(35, score.getPoints());
    }

    // Test Score Reset Functionality
    @Test
    void resetSetsScoreToZero() {

        // Create Score Instance
        Score score = new Score();

        // Add Points To Current Score
        score.addPoints(50);

        // Reset Current Score
        score.reset();

        // Verify Score Reset
        assertEquals(0, score.getPoints());
    }
}