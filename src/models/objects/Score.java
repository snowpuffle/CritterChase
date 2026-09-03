package models.objects;

// Score Manages the Player's Score in the Game
public class Score {

    // Player's Current Score
    private int points;

    // Score Constructor
    public Score() {
        this.points = 0;
    }

    // Get the Player's Current Score
    public int getPoints() {
        return points;
    }

    // Add Points to the Player's Score
    public void addPoints(int points) {
        this.points += points;
    }

    // Reset the Player's Score to Zero
    public void reset() {
        this.points = 0;
    }
}
