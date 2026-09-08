package models.levels;

import models.entities.Player;
import models.objects.Score;

// Level 2 Contains the Maze Layout and Objects for Level 2 of the Game.
public class Level_2 extends Level {

    // Level Emojis
    private static final String playerImage = "file:lib/assets/animals/mouse.png";
    private static final String foodImage = "file:lib/assets/food/cheese.png";
    private static final String enemyImage = "file:lib/assets/animals/cat.png";
    private static final String wallImage1 = "file:lib/assets/walls/wood_wall.png";
    private static final String wallImage2 = "file:lib/assets/walls/clay_wall.png";
    private static final String exitImage = "file:lib/assets/others/exit.png";

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
        super(new Player(1, 1, playerImage), 2, score);

        // Create the Level Objects and Place Them on the Game Board
        createLevelObjects(MAZE, foodImage, enemyImage, wallImage1, wallImage2, exitImage);
    }
}