package models.levels;

import models.objects.Score;

// LevelFactory Creates Levels Using the Central Level Configuration
public class LevelFactory {

    // Prevents Instantiation of the Factory Class
    private LevelFactory() {
    }

    // Creates a Level Using Its Level Number and Score
    public static Level createLevel(int levelNumber, Score score) {

        // Gets the Level Definition From the Central Configuration
        LevelDefinition definition = LevelConfig.getLevel(levelNumber);

        // Creates and Returns the Level Using Its Definition
        return new Level(definition, score);
    }
}