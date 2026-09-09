package models.levels;

import models.entities.Enemy;
import models.game.GameBoard;
import models.objects.Exit;
import models.objects.Food;
import models.objects.Wall;
import models.utils.EnemyManager;

// LevelBuilder Creates Game Objects from Level Maze
public class LevelBuilder {

    // Level Objects
    private final GameBoard gameBoard;
    private final EnemyManager enemyManager;

    // LevelBuilder Constructor
    public LevelBuilder(
            GameBoard gameBoard,
            EnemyManager enemyManager) {

        // Store GameBoard Reference
        this.gameBoard = gameBoard;

        // Store EnemyManager Reference
        this.enemyManager = enemyManager;
    }

    // Create Level Objects from Maze
    public void build(
            char[][] maze,
            String foodImage,
            String enemyImage,
            String wallImage1,
            String wallImage2,
            String exitImage) {

        // Loop Through Each Maze Row
        for (int row = 0; row < maze.length; row++) {

            // Loop Through Each Maze Column
            for (int col = 0; col < maze[row].length; col++) {

                // Create Object Based on Maze Character
                createObject(
                        maze[row][col],
                        row,
                        col,
                        foodImage,
                        enemyImage,
                        wallImage1,
                        wallImage2,
                        exitImage);
            }
        }
    }

    // Create Game Object Based on Maze Character
    private void createObject(
            char type,
            int row,
            int col,
            String foodImage,
            String enemyImage,
            String wallImage1,
            String wallImage2,
            String exitImage) {

        // Check Maze Character Type
        switch (type) {

            // Create Standard Wall
            case '#':
                gameBoard.setGameObjectAt(
                        row,
                        col,
                        new Wall(
                                row,
                                col,
                                wallImage1));
                break;

            // Create Alternate Wall
            case '%':
                gameBoard.setGameObjectAt(
                        row,
                        col,
                        new Wall(
                                row,
                                col,
                                wallImage2));
                break;

            // Create Food
            case 'F':
                gameBoard.setGameObjectAt(
                        row,
                        col,
                        new Food(
                                row,
                                col,
                                foodImage));
                break;

            // Create Enemy
            case 'E':
                enemyManager.addEnemy(
                        new Enemy(
                                row,
                                col,
                                enemyImage));
                break;

            // Create Exit
            case 'X':
                gameBoard.setGameObjectAt(
                        row,
                        col,
                        new Exit(
                                row,
                                col,
                                exitImage));
                break;

            // Leave Position Empty
            case ' ':
                break;

            // Reject Unsupported Maze Characters
            default:
                throw new IllegalArgumentException(
                        "Invalid Maze Character: " + type);
        }
    }
}