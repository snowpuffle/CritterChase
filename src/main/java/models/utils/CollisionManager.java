package models.utils;

import models.game.GameBoard;
import models.objects.Food;
import models.objects.GameObject;
import models.objects.Score;

// CollisionManager Class Controls Player Collision Rules
public class CollisionManager {

    private final GameBoard gameBoard;
    private final Score score;

    // CollisionManager Constructor
    public CollisionManager(GameBoard gameBoard, Score score) {
        this.gameBoard = gameBoard;
        this.score = score;
    }

    // Check if Player Can Move to Requested Position
    public boolean canPlayerMoveTo(int row, int col) {

        // Get GameObject at Requested Position
        GameObject object = gameBoard.getGameObjectAt(row, col);

        // Allow Movement When Position Contains No GameObject
        if (object == null) {
            return true;
        }

        // Check Collision Based on GameObject Type
        switch (object.getType()) {

            // Collect Food When Player Enters Food Position
            case FOOD:
                collectFood((Food) object);
                return true;

            // Prevent Player Movement Through Wall
            case WALL:
                return false;

            // Allow Player Movement Onto Exit
            case EXIT:
                return true;

            // Allow Player Movement Onto Weapon
            case WEAPON:
                return true;

            // Prevent Movement Through Unknown Object Types
            default:
                return false;
        }
    }

    // Collect Food and Add Food Points to Level Score
    private void collectFood(Food food) {

        // Add Food Point Value to Current Score
        score.addPoints(food.getPoints());

        // Remove Collected Food from GameBoard
        gameBoard.removeGameObjectAt(food.getRow(), food.getCol());
    }
}