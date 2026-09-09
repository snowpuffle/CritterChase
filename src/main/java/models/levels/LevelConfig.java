package models.levels;

// LevelConfig Stores the Level Configuration and Level Order
public final class LevelConfig {

    // Define the Starting Level Number
    private static final int STARTING_LEVEL = 1;

    // Define the Total Number of Available Levels
    private static final int MAX_LEVEL = 3;

    // Private Constructor Prevents the Class from Being Instantiated
    private LevelConfig() {
        // Utility class
    }

    // Get a Level Definition for the Given Level Number
    public static LevelDefinition getLevel(int levelNumber) {

        // Check if the Requested Level Number Exists
        if (!exists(levelNumber)) {
            throw new IllegalArgumentException("Invalid Level Number: " + levelNumber);
        }

        // Load and Return the Level Definition from the JSON Resource
        return LevelLoader.load(levelNumber);
    }

    // Get the Starting Level Number
    public static int getStartingLevel() {
        return STARTING_LEVEL;
    }

    // Get the Total Number of Available Levels
    public static int getMaxLevel() {
        return MAX_LEVEL;
    }

    // Check if a Level Number Exists
    public static boolean exists(int levelNumber) {

        // Check if the Level Number is Within the Valid Level Range
        return levelNumber >= STARTING_LEVEL
                && levelNumber <= MAX_LEVEL;
    }

    // Get the Total Maximum Score Across All Levels
    public static int getTotalMaxScore() {

        // Initialize the Total Maximum Score
        int total = 0;

        // Loop Through Each Available Level
        for (int levelNumber = STARTING_LEVEL; levelNumber <= MAX_LEVEL; levelNumber++) {

            // Add the Current Level's Maximum Score to the Total
            total += getLevel(levelNumber).maxScore();
        }

        // Return the Total Maximum Score
        return total;
    }
}