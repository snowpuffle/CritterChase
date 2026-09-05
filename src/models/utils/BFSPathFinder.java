package models.utils;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// BFSPathFinder Finds the Shortest Distance from the Player to Every Reachable Position.
public class BFSPathFinder {

    // Game Board
    private final GameBoard gameBoard;

    // BFSPathFinder Constructor
    public BFSPathFinder(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    // Create a Distance Map Starting from the Player
    public int[][] createDistanceMap(int playerRow, int playerCol, EnemyManager enemyManager) {

        int height = gameBoard.getHeight();
        int width = gameBoard.getWidth();

        // Distance to Each Position
        int[][] distances = new int[height][width];

        // Initialize All Distances as -1
        for (int row = 0; row < height; row++) {
            Arrays.fill(distances[row], -1);
        }

        // BFS Queue
        Queue<int[]> queue = new ArrayDeque<>();

        // Start BFS at the Player's Position
        distances[playerRow][playerCol] = 0;
        queue.add(new int[] { playerRow, playerCol });

        // Perform BFS
        while (!queue.isEmpty()) {

            int[] current = queue.remove();

            int row = current[0];
            int col = current[1];

            // Explore Neighboring Positions
            for (Direction direction : Direction.values()) {

                int newRow = row + direction.getRowChange();
                int newCol = col + direction.getColChange();

                // Skip Positions the Enemy Cannot Enter
                if (!canEnemyMoveTo(newRow, newCol, enemyManager, playerRow, playerCol)) {
                    continue;
                }

                // Skip Positions Already Visited
                if (distances[newRow][newCol] != -1) {
                    continue;
                }

                // Set Distance from Player
                distances[newRow][newCol] = distances[row][col] + 1;

                // Add Position to Queue
                queue.add(new int[] { newRow, newCol });
            }
        }

        return distances;
    }

    // Check if an Enemy Can Move Through a Position
    private boolean canEnemyMoveTo(int row, int col, EnemyManager enemyManager, int playerRow, int playerCol) {

        // Check if Position is Valid
        if (!gameBoard.isValidPosition(row, col)) {
            return false;
        }

        // Always Allow the Player's Position
        if (row == playerRow && col == playerCol) {
            return true;
        }

        // Check if Position is Occupied by a Game Object
        if (gameBoard.getGameObjectAt(row, col) != null) {
            return false;
        }

        // Enemy positions are intentionally allowed during BFS.
        // Enemy occupancy is checked later when choosing the actual movement position.
        return true;
    }
}
