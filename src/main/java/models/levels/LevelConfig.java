package models.levels;

import java.util.List;

// LevelConfig Stores Level Configuration and Level Order
public final class LevelConfig {

    // Define Starting Level Number
    private static final int STARTING_LEVEL = 1;

    // Define Available Levels
    private static final List<Integer> AVAILABLE_LEVELS = List.of(1);
    // private static final List<Integer> AVAILABLE_LEVELS = List.of(1, 2, 3, 4, 5);

    // Private Constructor Prevents Class Instantiation
    private LevelConfig() {
        // Utility Class
    }

    // Get Level Definition for Given Level Number
    public static LevelDefinition getLevel(int levelNumber) {

        // Check if Requested Level Exists
        if (!exists(levelNumber)) {
            throw new IllegalArgumentException(
                    "Invalid Level Number: " + levelNumber);
        }

        // Load Requested Level Definition
        return LevelLoader.load(levelNumber);
    }

    // Get Starting Level Number
    public static int getStartingLevel() {
        return STARTING_LEVEL;
    }

    // Get Final Level Number
    public static int getFinalLevel() {
        return AVAILABLE_LEVELS.get(
                AVAILABLE_LEVELS.size() - 1);
    }

    // Check if Level Exists
    public static boolean exists(int levelNumber) {
        return AVAILABLE_LEVELS.contains(levelNumber);
    }

    // Get Total Maximum Score Across All Levels
    public static int getTotalMaxScore() {

        // Start Total Maximum Score
        int total = 0;

        // Loop Through Available Levels
        for (int levelNumber : AVAILABLE_LEVELS) {

            // Add Current Level Maximum Score
            total += getLevel(levelNumber).maxScore();
        }

        // Return Total Maximum Score
        return total;
    }

    // Get Next Level Number
    public static int getNextLevel(int currentLevel) {

        // Find Current Level Position
        int currentIndex = AVAILABLE_LEVELS.indexOf(currentLevel);

        // Reject Invalid Current Level
        if (currentIndex == -1) {
            throw new IllegalArgumentException(
                    "Invalid Level Number: " + currentLevel);
        }

        // Check if Current Level Is Final Level
        if (currentIndex + 1 >= AVAILABLE_LEVELS.size()) {
            return -1;
        }

        // Return Next Level Number
        return AVAILABLE_LEVELS.get(currentIndex + 1);
    }
}