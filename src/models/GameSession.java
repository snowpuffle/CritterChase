package models;

import models.levels.Level;
import models.objects.Score;

// GameSession Stores the State of the Current Game
public class GameSession {

    // Persistent Game State
    private final Score score;

    // Current Level
    private Level currentLevel;

    // GameSession Constructor
    public GameSession() {
        this.score = new Score();
        this.currentLevel = null;
    }

    // Get the Persistent Score
    public Score getScore() {
        return score;
    }

    // Get the Current Level
    public Level getCurrentLevel() {
        return currentLevel;
    }

    // Set the Current Level
    public void setCurrentLevel(Level level) {
        this.currentLevel = level;
    }

    // Get the Current Level Number
    public int getCurrentLevelNumber() {
        if (currentLevel == null) {
            return 0;
        }

        return currentLevel.getLevelNumber();
    }

    // Check if a Game Has Started
    public boolean isStarted() {
        return currentLevel != null;
    }

    // Reset the Game Session
    public void reset() {
        score.reset();
        currentLevel = null;
    }
}