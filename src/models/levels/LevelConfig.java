package models.levels;

import java.util.List;
import java.util.function.Function;

import models.objects.Score;

// LevelConfig Stores the Available Levels and Their Order
public final class LevelConfig {

    // Private Constructor Prevents the Class from Being Instantiated
    private LevelConfig() {
        // Utility class
    }

    // List of Available Levels in Sequential Order
    private static final List<Function<Score, Level>> LEVELS = List.of(
            Level_1::new,
            Level_2::new);

    // Get the Starting Level Number
    public static int getStartingLevel() {
        return 1;
    }

    // Get the Total Number of Available Levels
    public static int getMaxLevel() {
        return LEVELS.size();
    }

    // Check if a Level Number Exists
    public static boolean exists(int levelNumber) {
        return levelNumber >= 1 && levelNumber <= LEVELS.size();
    }

    // Create a Level Based on the Level Number
    public static Level createLevel(int levelNumber, Score score) {

        // Check if the Level Number is Valid
        if (!exists(levelNumber)) {
            throw new IllegalArgumentException("Invalid Level Number: " + levelNumber);
        }

        // Create the Level Using the Shared Game Score
        return LEVELS.get(levelNumber - 1).apply(score);
    }
}