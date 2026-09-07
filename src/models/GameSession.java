package models;

import models.levels.Level;

// GameSession Stores the State of the Current Game
public class GameSession {

    // Game State
    private Level currentLevel;
    private int currentLevelNumber;
    private int score;
    private int health;

    // Create a New Game Session
    public GameSession() {
        reset();
    }

    // Reset the Entire Game Session
    public void reset() {
        currentLevel = null;
        currentLevelNumber = 1;
        score = 0;
        health = 100;
    }

    // Set the Current Level
    public void setCurrentLevel(Level level) {
        this.currentLevel = level;
        this.currentLevelNumber = level.getLevelNumber();
    }

    // Get the Current Level
    public Level getCurrentLevel() {
        return currentLevel;
    }

    // Get the Current Level Number
    public int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    // Get the Current Score
    public int getScore() {
        return score;
    }

    // Set the Score
    public void setScore(int score) {
        this.score = score;
    }

    // Add Points to the Score
    public void addScore(int points) {
        this.score += points;
    }

    // Get the Current Health
    public int getHealth() {
        return health;
    }

    // Set the Health
    public void setHealth(int health) {
        this.health = health;
    }

    // Check if the Player is Alive
    public boolean isAlive() {
        return health > 0;
    }

    // Check if the Game Has Started
    public boolean isStarted() {
        return currentLevel != null;
    }
}