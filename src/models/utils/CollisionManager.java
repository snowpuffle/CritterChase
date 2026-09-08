package models.utils;

import models.game.GameBoard;
import models.objects.Food;
import models.objects.GameObject;
import models.objects.Score;

// 
public class CollisionManager {

    private final GameBoard gameBoard;
    private final Score score;

    // Collision Manager Constructor
    public CollisionManager(GameBoard gameBoard, Score score) {
        this.gameBoard = gameBoard;
        this.score = score;
    }

    // Check if Player Can Move to Position
    public boolean canPlayerMoveTo(int row, int col) {

        // Get Game Object at Position
        GameObject object = gameBoard.getGameObjectAt(row, col);

        // Allow Movement When Position is Empty
        if (object == null) {
            return true;
        }

        // Handle Collision based on Object Type
        switch (object.getType()) {

            case FOOD: // Collect Food and Allow Movement
                collectFood((Food) object);
                return true;

            case WALL: // Block Movement through Wall
                return false;

            case EXIT: // Allow Movement through Exit
                return true;

            default: // Allow Movement for Other Object Types
                return true;
        }
    }

    // Collect Food and Add Points
    private void collectFood(Food food) {
        score.addPoints(food.getPoints());
        gameBoard.removeGameObjectAt(food.getRow(), food.getCol());
    }
}