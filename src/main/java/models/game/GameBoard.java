package models.game;

import models.objects.GameObject;

// GameBoard Class Represents Game Grid Used for Level Layout
public class GameBoard {

    private final int width;
    private final int height;
    private final GameObject[][] gameObjects;

    // GameBoard Constructor
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.gameObjects = new GameObject[height][width];
    }

    // Get GameBoard Width
    public int getWidth() {
        return width;
    }

    // Get GameBoard Height
    public int getHeight() {
        return height;
    }

    // Check if Board Position Exists Within Valid Boundaries
    public boolean isValidPosition(int row, int col) {
        return row >= 0
                && row < height
                && col >= 0
                && col < width;
    }

    // Get GameObject Located at Board Position
    public GameObject getGameObjectAt(int row, int col) {

        // Return Null When Position Exists Outside Board
        if (!isValidPosition(row, col)) {
            return null;
        }

        return gameObjects[row][col];
    }

    // Place GameObject at Board Position
    public void setGameObjectAt(int row, int col, GameObject gameObject) {

        // Reject Null GameObject Values
        if (gameObject == null) {
            throw new IllegalArgumentException(
                    "GameObject Cannot Be Null."
            );
        }

        // Reject Positions Outside Board Boundaries
        if (!isValidPosition(row, col)) {
            throw new IllegalArgumentException(
                    "GameObject Position Exists Outside GameBoard."
            );
        }

        // Store GameObject at Requested Position
        gameObjects[row][col] = gameObject;
    }

    // Remove GameObject from Board Position
    public void removeGameObjectAt(int row, int col) {

        // Ignore Invalid Board Positions
        if (!isValidPosition(row, col)) {
            return;
        }

        // Remove GameObject from Requested Position
        gameObjects[row][col] = null;
    }
}