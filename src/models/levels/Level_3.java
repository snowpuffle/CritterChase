package models.levels;

import models.entities.Player;
import models.objects.Score;

// Level 3 Contains the Maze Layout and Objects for Level 3 of the Game.
public class Level_3 extends Level {

    // Level 3 Assets
    private static final String PLAYER_IMAGE = "file:lib/assets/animals/frog.png";
    private static final String FOOD_IMAGE = "file:lib/assets/food/butterfly.png";
    private static final String ENEMY_IMAGE = "file:lib/assets/animals/raccoon.png";
    private static final String WALL_IMAGE_1 = "file:lib/assets/walls/bush_wall.png";
    private static final String WALL_IMAGE_2 = "file:lib/assets/walls/tree_wall.png";
    private static final String EXIT_IMAGE = "file:lib/assets/others/exit.png";

    // Level 3 Maze Layout - 3 Enemies
    private static final char[][] MAZE = {
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
            { '#', ' ', 'F', ' ', ' ', ' ', ' ', 'F', ' ', ' ', ' ', ' ', '#', 'E', '#' },
            { '#', '#', '#', ' ', '#', '#', '#', ' ', '#', '%', '#', ' ', '#', ' ', '#' },
            { '#', ' ', ' ', 'F', ' ', ' ', ' ', ' ', 'F', ' ', ' ', 'F', ' ', 'E', 'X' },
            { '#', '#', '#', '#', '#', '%', '#', '#', '#', '#', '#', '#', '%', '#', '#' }

    };

    // Level Constructor
    public Level_3(Score score) {

        // Create the Level Player and Pass It to the Parent Level
        super(new Player(1, 1, PLAYER_IMAGE), 3, score);

        // Create the Level Objects and Place Them on the Game Board
        createLevelObjects(
                MAZE,
                FOOD_IMAGE,
                ENEMY_IMAGE,
                WALL_IMAGE_1,
                WALL_IMAGE_2,
                EXIT_IMAGE);

        // Calculate Max Score
        calculateAndStoreMaxScore();
    }
}