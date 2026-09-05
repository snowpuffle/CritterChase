package models.utils;

import models.levels.Level;
import models.levels.Level_1;

// LevelFactory Creates the Correct Level Based on the Level Number
public class LevelFactory {

    // Create a Level Based on the Level Number
    public static Level createLevel(int levelNumber) {
        switch (levelNumber) {
            // Create Level 1
            case 1:
                return new Level_1();
            // Level Does Not Exist
            default:
                throw new IllegalArgumentException("Invalid Level Number: " + levelNumber);
        }
    }
}
