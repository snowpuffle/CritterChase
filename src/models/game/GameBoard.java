package models.game;

import models.objects.Food;
import models.objects.GameObject;

public class GameBoard {

    // Board Dimensions
    private final int width;
    private final int height;

    // Board Objects
    private final GameObject[][] gameBoard;

    // GameBoard Constructor
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.gameBoard = new GameObject[height][width];
    }

    // Get the GameObject at a Specific Position
    public GameObject getGameObjectAt(int row, int col) {

        // Check if the Position is Valid
        if (!isValidPosition(row, col)) {
            return null; // Out of Bounds
        }

        // Return the GameObject at the Position
        return gameBoard[row][col];
    }

    // Place an Object at a Specific Position
    public void setGameObjectAt(GameObject gameObject) {

        // Check for Null Object
        if (gameObject == null) {
            return; // Null Object
        }

        // Get the Position of the Object
        int row = gameObject.getRow();
        int col = gameObject.getCol();

        // Check if the Position is Valid and Place the Object
        if (isValidPosition(row, col)) {
            gameBoard[row][col] = gameObject;
        }
    }

    // Remove an Object from a Specific Position
    public void removeGameObjectAt(int row, int col) {

        // Check if the Position is Valid
        if (isValidPosition(row, col)) {
            gameBoard[row][col] = null; // Remove the Object
        }
    }

    // Check if a Position is Inside the Board
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    
    public int getMaxScore() {
        int maxScore = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                GameObject object = getGameObjectAt(row, col);

                if (object instanceof Food) {
                    maxScore += ((Food) object).getPoints();
                }
            }
        }

        return maxScore;
    }

    // Getters
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}