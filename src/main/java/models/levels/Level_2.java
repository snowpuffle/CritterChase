package models.levels;

import models.entities.Player;
import models.objects.Score;

// Level 2 Contains the Maze Layout and Objects for Level 2 of the Game.
public class Level_2 extends Level {

    // Level 2 Assets
    private static final String PLAYER_IMAGE = "file:lib/assets/animals/bunny.png";
    private static final String FOOD_IMAGE = "file:lib/assets/food/carrot.png";
    private static final String ENEMY_IMAGE = "file:lib/assets/animals/fox.png";
    private static final String WALL_IMAGE_1 = "file:lib/assets/walls/bush_wall.png";
    private static final String WALL_IMAGE_2 = "file:lib/assets/walls/wood_wall.png";
    private static final String EXIT_IMAGE = "file:lib/assets/others/exit.png";

    // Level 2 Maze Layout - 2 Enemies
    private static final char[][] MAZE = {

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

    };

    // Level Constructor
    public Level_2(Score score) {

        // Create the Level Player and Pass It to the Parent Level
        super(new Player(1, 1, PLAYER_IMAGE), 2, score);

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