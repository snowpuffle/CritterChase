package models.utils;

import models.levels.Level;
import models.levels.Level_1;
import models.levels.Level_2;
import models.objects.Score;

// LevelFactory Creates the Correct Level Based on the Level Number
public class LevelFactory {

    // Create a Level Using the Shared Game Score
    public static Level createLevel(int levelNumber, Score score) {

        switch (levelNumber) {

            // Create Level 1
            case 1:
                return new Level_1(score);

            // Create Level 2
            case 2:
                return new Level_2(score);

            // Level Does Not Exist
            default:
                throw new IllegalArgumentException(
                        "Invalid Level Number: " + levelNumber);
        }
    }
}