package models.levels;

import models.objects.Score;

// LevelFactory Creates Levels Using the Central Level Configuration
public class LevelFactory {

    // Creates a Level Based on the Level Number
    public static Level createLevel(int levelNumber, Score score) {
        return LevelConfig.createLevel(levelNumber, score);
    }
}