package models.utils;

import models.entities.Enemy;
import models.objects.Exit;
import models.objects.Food;
import models.objects.Wall;

// LevelBuilder Creates the Game Objects from the Level Maze.
public class LevelBuilder {

    // Level Objects
    private final GameBoard gameBoard;
    private final EnemyManager enemyManager;

    // LevelBuilder Constructor
    public LevelBuilder(GameBoard gameBoard, EnemyManager enemyManager) {
        this.gameBoard = gameBoard;
        this.enemyManager = enemyManager;
    }

    // Create the Level Objects from the Maze
    public void build(char[][] maze, String foodImage, String enemyImage, String wallImage1, String wallImage2,
            String exitImage) {

        // Loop Through Each Row
        for (int row = 0; row < maze.length; row++) {

            // Loop Through Each Column
            for (int col = 0; col < maze[row].length; col++) {

                // Create the Object Based on the Maze Character
                createObject(maze[row][col], row, col, foodImage, enemyImage, wallImage1, wallImage2, exitImage);
            }
        }
    }

    // Create the Object Based on the Maze Character
    private void createObject(char type, int row, int col, String foodImage, String enemyImage, String wallImage1,
            String wallImage2, String exitImage) {

        // Create the Object Based on the Maze Character
        switch (type) {
            // Create a Wall
            case '#':
                gameBoard.setGameObjectAt(new Wall(row, col, wallImage1));
                break;

            // Create a Wall
            case '%':
                gameBoard.setGameObjectAt(new Wall(row, col, wallImage2));
                break;

            // Create Food
            case 'F':
                gameBoard.setGameObjectAt(new Food(row, col, foodImage));
                break;

            // Create an Enemy
            case 'E':
                enemyManager.addEnemy(new Enemy(row, col, enemyImage));
                break;

            // Create the Exit
            case 'X':
                gameBoard.setGameObjectAt(new Exit(row, col, exitImage));
                break;

            // Leave the Position Empty
            default:
                break;
        }
    }
}