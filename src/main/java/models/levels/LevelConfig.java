package models.levels;

import java.util.List;

// LevelConfig Stores the Level Configuration and Level Order
public final class LevelConfig {

    // Define the Starting Level Number
    private static final int STARTING_LEVEL = 1;

    // Define the Available Levels
    private static final List<Integer> AVAILABLE_LEVELS = List.of(
            1,
            2,
            3,
            4);

    // Private Constructor Prevents the Class from Being Instantiated
    private LevelConfig() {
        // Utility class
    }

    // Get a Level Definition for the Given Level Number
    public static LevelDefinition getLevel(int levelNumber) {

        if (!exists(levelNumber)) {
            throw new IllegalArgumentException(
                    "Invalid Level Number: " + levelNumber);
        }

        return LevelLoader.load(levelNumber);
    }

    // Get the Starting Level Number
    public static int getStartingLevel() {
        return STARTING_LEVEL;
    }

    // Get the Final Level Number
    public static int getFinalLevel() {
        return AVAILABLE_LEVELS.get(AVAILABLE_LEVELS.size() - 1);
    }

    // Check if a Level Exists
    public static boolean exists(int levelNumber) {
        return AVAILABLE_LEVELS.contains(levelNumber);
    }

    // Get the Total Maximum Score Across All Levels
    public static int getTotalMaxScore() {

        int total = 0;

        for (int levelNumber : AVAILABLE_LEVELS) {
            total += getLevel(levelNumber).maxScore();
        }

        return total;
    }

    public static int getNextLevel(int currentLevel) {

        int currentIndex = AVAILABLE_LEVELS.indexOf(currentLevel);

        if (currentIndex == -1) {
            throw new IllegalArgumentException(
                    "Invalid Level Number: " + currentLevel);
        }

        if (currentIndex + 1 >= AVAILABLE_LEVELS.size()) {
            return -1;
        }

        return AVAILABLE_LEVELS.get(currentIndex + 1);
    }
}