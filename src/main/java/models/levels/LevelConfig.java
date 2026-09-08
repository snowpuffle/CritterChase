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
                    "/assets/backgrounds/house.png",
                    100,
                    1,
                    1,
                    "/assets/animals/mouse.png",
                    "/assets/food/cheese.png",
                    "/assets/animals/cat.png",
                    "/assets/walls/brick_wall.png",
                    "/assets/walls/wood.png",
                    "/assets/others/exit.png",

                    new char[][] {
                            { '#', '#', '#', '%', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#' },
                            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', 'F', '#' },
                            { '#', ' ', '#', '%', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', '#', 'F', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '%', ' ', '#' },
                            { '#', ' ', '#', ' ', '#', '#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#', ' ', ' ', ' ', '%', 'F', '#' },
                            { '#', '#', '#', '#', '#', '#', '#', ' ', '#', '#', '#', '#', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', '#', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', '%', 'F', ' ', ' ', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '#', '#', '#', '#', '#', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
                            { '#', '%', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', 'F', ' ', ' ', ' ', ' ', 'E', 'X' },
                            { '#', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#', '%', '#', '#' }
                    }),
            new LevelDefinition(
                    2,
                    "/assets/backgrounds/woodlands.png",
                    100,
                    1,
                    1,
                    "/assets/animals/bunny.png",
                    "/assets/food/carrot.png",
                    "/assets/animals/fox.png",
                    "/assets/walls/tree_2.png",
                    "/assets/walls/tree_1.png",
                    "/assets/others/exit.png",

                    new char[][] {
                            { '#', '#', '#', '%', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#' },
                            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', 'F', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', 'F', '#', ' ', ' ', ' ', '#', 'F', ' ', ' ', '#', ' ', '#' },
                            { '#', '#', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '%', '#', ' ', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', 'E', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '%', ' ', '%', ' ', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', 'F', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '%', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', 'F', ' ', ' ', ' ', 'F', ' ', ' ', ' ', ' ', 'E', 'X' },
                            { '#', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#', '%', '#', '#' }
                    }),
            new LevelDefinition(
                    3,
                    "/assets/backgrounds/pond.png",
                    100,
                    1,
                    1,
                    "/assets/animals/frog.png",
                    "/assets/food/butterfly.png",
                    "/assets/animals/raccoon.png",
                    "/assets/walls/wood.png",
                    "/assets/walls/lilypad.png",
                    "/assets/others/exit.png",

                    new char[][] {
                            { '#', '#', '#', '%', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#' },
                            { '#', ' ', ' ', 'F', ' ', ' ', '#', ' ', ' ', 'F', ' ', ' ', ' ', 'F', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', 'F', '#', ' ', ' ', ' ', '#', 'F', ' ', ' ', '#', ' ', '#' },
                            { '#', '#', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', ' ', ' ', '%', ' ', ' ', ' ', '#', ' ', ' ', ' ', '#' },
                            { '#', ' ', '#', '%', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', 'F', ' ', ' ', 'E', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '#', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', ' ', ' ', 'F', ' ', ' ', ' ', '#', ' ', '%', ' ', '#' },
                            { '#', ' ', '#', '#', '#', ' ', '#', '%', '#', ' ', '#', ' ', '#', ' ', '#' },
                            { '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#' },
                            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '%', '#', ' ', '#', ' ', '#' },
                            { '#', 'F', ' ', ' ', ' ', ' ', ' ', ' ', 'F', ' ', ' ', ' ', ' ', 'E', 'X' },
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