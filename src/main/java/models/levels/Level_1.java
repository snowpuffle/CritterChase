package models.levels;

import models.entities.Player;
import models.objects.Score;

// Level 1 Contains the Maze Layout and Objects for Level 1 of the Game.
public class Level_1 extends Level {

    // Level Assets
    private static final String PLAYER_IMAGE = "file:lib/assets/animals/mouse.png";
    private static final String FOOD_IMAGE = "file:lib/assets/food/cheese.png";
    private static final String ENEMY_IMAGE = "file:lib/assets/animals/cat.png";
    private static final String WALL_IMAGE_1 = "file:lib/assets/walls/wood_wall.png";
    private static final String WALL_IMAGE_2 = "file:lib/assets/walls/clay_wall.png";
    private static final String EXIT_IMAGE = "file:lib/assets/others/exit.png";

    // Level 1 Maze Layout - 1 Enemy
    private static final char[][] MAZE = {
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
    };

    // Level Constructor
    public Level_1(Score score) {

        // Create the Level Player and Pass It to the Parent Level
        super(new Player(1, 1, PLAYER_IMAGE), 1, score);

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