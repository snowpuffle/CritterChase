package models.levels;

import models.entities.Enemy;
import models.objects.Exit;
import models.objects.Food;
import models.objects.Wall;
import models.utils.EnemyManager;
import models.utils.GameBoard;

// LevelBuilder Creates the Game Objects from the Level Maze
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

        validateMaze(maze);

        // Loop Through Each Row
        for (int row = 0; row < maze.length; row++) {

            // Loop Through Each Column
            for (int col = 0; col < maze[row].length; col++) {

                // Create the Object Based on the Maze Character
                createObject(maze[row][col], row, col, foodImage, enemyImage, wallImage1, wallImage2, exitImage);
            }
        }
    }

    // Validate Maze Structure and Characters
    private void validateMaze(char[][] maze) {

        // Check Maze
        validateMazeExists(maze);

        // Check Maze Rows
        int expectedWidth = maze[0].length;
        int exitCount = 0;

        // Loop Through Each Row
        for (int row = 0; row < maze.length; row++) {

            // Check if Row is Valid
            validateRow(maze, row, expectedWidth);

            // Loop Through Each Column
            for (int col = 0; col < maze[row].length; col++) {

                // Get Current Maze Character
                char type = maze[row][col];

                // Count Each Exit
                if (type == 'X') {
                    exitCount++;
                }

                // Check if Character is Valid
                validateCharacter(type);
            }
        }

        // Check if Maze Contains Exactly One Exit
        if (exitCount != 1) {
            throw new IllegalArgumentException("Maze Error!");
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

    // Check if Maze Exists
    private void validateMazeExists(char[][] maze) {

        // Check if Maze is Null or Empty
        if (maze == null || maze.length == 0) {
            throw new IllegalArgumentException("Maze Cannot Be Null or Empty!");
        }

        // Check if First Row is Null or Empty
        if (maze[0] == null || maze[0].length == 0) {
            throw new IllegalArgumentException("Maze First Row Cannot Be Null or Empty!");
        }
    }

    // Check if Row is Valid
    private void validateRow(char[][] maze, int row, int expectedWidth) {

        // Check if Current Row is Null
        if (maze[row] == null) {
            throw new IllegalArgumentException("Maze Row " + row + " Cannot Be Null!");
        }

        // Check if Row Width Matches Expected Width
        if (maze[row].length != expectedWidth) {
            throw new IllegalArgumentException(
                    "Maze Row " + row + " Has Width " + maze[row].length + ", Expected " + expectedWidth + ".");
        }
    }

    // Check if Character is Valid
    private void validateCharacter(char type) {

        // Check Valid Maze Characters
        if (type != '#' && type != '%' && type != 'F' && type != 'E' && type != 'X' && type != ' ' && type != 'P') {
            throw new IllegalArgumentException("Maze Must Contain Exactly One Exit!");
        }
    }
}