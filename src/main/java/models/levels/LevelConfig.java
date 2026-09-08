package models.levels;

import java.util.List;

// LevelConfig Stores the Available Levels and Their Order
public final class LevelConfig {

    // Private Constructor Prevents the Class from Being Instantiated
    private LevelConfig() {
        // Utility class
    }

    private static final List<LevelDefinition> LEVELS = List.of(

            new LevelDefinition(
                    1,
                    110,
                    1,
                    1,
                    "file:lib/assets/animals/mouse.png",
                    "file:lib/assets/food/cheese.png",
                    "file:lib/assets/animals/cat.png",
                    "file:lib/assets/walls/brick_wall.png",
                    "file:lib/assets/walls/wood.png",
                    "file:lib/assets/others/exit.png",

                    new char[][] {
                            { '#', '#', '#', '%', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', '#', ' ', ' ', 'F', ' ', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '%', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '%', ' ', '#' },
                            { '#', ' ', '%', ' ', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#', ' ', ' ', '%', ' ', 'F', '#' },
                            { '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '%', ' ', '#' },
                            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '#', '%', ' ', '#', '#', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', '%', ' ', ' ', 'F', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '#', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#' },
                            { '#', '%', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', 'F', ' ', ' ', ' ', ' ', 'E', 'X' },
                            { '#', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#', '%', '#', '#' }
                    }),
            new LevelDefinition(
                    2,
                    100,
                    1,
                    1,
                    "file:lib/assets/animals/bunny.png",
                    "file:lib/assets/food/carrot.png",
                    "file:lib/assets/animals/fox.png",
                    "file:lib/assets/walls/tree_2.png",
                    "file:lib/assets/walls/tree_1.png",
                    "file:lib/assets/others/exit.png",

                    new char[][] {
                            { '#', '#', '#', '%', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#' },
                            { '#', 'P', ' ', ' ', 'F', ' ', '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', 'F', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#', 'F', '#' },
                            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '%', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', 'E', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#', ' ', 'F', ' ', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', 'F', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '%', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', 'F', ' ', ' ', ' ', ' ', 'E', 'X' },
                            { '#', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#', '%', '#', '#' }
                    }),
            new LevelDefinition(
                    3,
                    160,
                    1,
                    1,
                    "file:lib/assets/animals/frog.png",
                    "file:lib/assets/food/butterfly.png",
                    "file:lib/assets/animals/raccoon.png",
                    "file:lib/assets/walls/wood.png",
                    "file:lib/assets/walls/lilypad.png",
                    "file:lib/assets/others/exit.png",

                    new char[][] {
                            { '#', '#', '#', '%', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#' },
                            { '#', 'P', ' ', 'F', ' ', ' ', '#', ' ', ' ', 'F', ' ', ' ', 'F', ' ', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', 'F', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', 'F', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '%', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#', ' ', 'F', ' ', ' ', 'E', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', 'F', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#', ' ', 'F', ' ', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', 'F', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', 'E', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '%', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', 'F', ' ', ' ', ' ', ' ', 'F', ' ', ' ', 'F', ' ', 'E', 'X' },
                            { '#', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#', '%', '#', '#' }
                    }));

    public static LevelDefinition getLevel(int levelNumber) {
        if (levelNumber < 1 || levelNumber > LEVELS.size()) {
            throw new IllegalArgumentException(
                    "Invalid Level Number: " + levelNumber);
        }

        return LEVELS.get(levelNumber - 1);
    }

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

    // Get the Total Max Score Across All Levels
    public static int getTotalMaxScore() {
        int total = 0;

        for (LevelDefinition level : LEVELS) {
            total += level.getMaxScore();
        }

        return total;
    }
}