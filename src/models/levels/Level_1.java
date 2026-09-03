package models.levels;

import models.entities.Player;

// Level 1 Contains the Maze Layout and Objects for Level 1 of the Game.
public class Level_1 extends Level {

    // Level Emojis
    private static final String playerImage = "file:lib/assets/animals/mouse.png";
    private static final String foodImage = "file:lib/assets/food/cheese.png";
    private static final String enemyImage = "file:lib/assets/animals/cat.png";
    private static final String wallImage1 = "file:lib/assets/walls/wood_wall.png";
    private static final String wallImage2 = "file:lib/assets/walls/clay_wall.png";
    private static final String exitImage = "file:lib/assets/others/exit.png";

    // Level Information
    private static final String LEVEL_NAME = "CHEESE CHASE";

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
    public Level_1() {

        // Create the Level Player and Pass It to the Parent Level
        super(new Player(12, 13, playerImage), LEVEL_NAME);

        // Create the Level Objects and Place Them on the Game Board
        createLevelObjects(MAZE, foodImage, enemyImage, wallImage1, wallImage2, exitImage);
    }

}