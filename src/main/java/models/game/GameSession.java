package models.game;

import models.levels.Level;
import models.objects.Score;

// GameSession Stores the State of the Current Game
public class GameSession {

    // Store the Player's Overall Game Score
    private final Score score;

    // Store the Current Level
    private Level currentLevel;

    // Create a New Game Session
    public GameSession() {
        score = new Score();
    }

    // Reset the Game Session State
    public void reset() {
        score.reset();
        currentLevel = null;
    }

    // Add Points to the Overall Game Score
    public void addScore(int points) {
        score.addPoints(points);
    }

    // Get the Overall Game Score
    public int getScore() {
        return score.getPoints();
    }

    // Get the Current Level
    public Level getCurrentLevel() {
        return currentLevel;
    }

    // Set the Current Level
    public void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    // Get the Current Level Number
    public int getCurrentLevelNumber() {
        return currentLevel != null
                ? currentLevel.getLevelNumber()
                : 0;
    }

    // Check if the Game Has Started
    public boolean isStarted() {
        return currentLevel != null;
    }
}
