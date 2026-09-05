package models.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

// BFSPathFinder Finds the Shortest Path through the Maze.
public class BFSPathFinder {

    // Game Board
    private final GameBoard gameBoard;

    // BFSPathFinder Constructor
    public BFSPathFinder(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    // Find the Shortest Path to the Target
    public List<int[]> findPath(int startRow, int startCol, int targetRow, int targetCol,
            EnemyManager enemyManager) {

        int height = gameBoard.getHeight();
        int width = gameBoard.getWidth();

        // Track Visited Positions
        boolean[][] visited = new boolean[height][width];

        // Track the Previous Position for Each Cell to Reconstruct the Path
        int[][] previousRow = new int[height][width];
        int[][] previousCol = new int[height][width];

        // Initialize Previous Positions to -1 (indicating no previous position)
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                previousRow[row][col] = -1;
                previousCol[row][col] = -1;
            }
        }

        // Create BFS Queue
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] { startRow, startCol });
        visited[startRow][startCol] = true;

        // Perform BFS
        while (!queue.isEmpty()) {

            // Get Current Position
            int[] current = queue.remove();
            int row = current[0];
            int col = current[1];

            // Check if Target is Reached
            if (row == targetRow && col == targetCol) {
                return buildPath(startRow, startCol, targetRow, targetCol, previousRow, previousCol);
            }

            // Explore Neighbors
            for (Direction direction : Direction.values()) {

                int newRow = row + direction.getRowChange();
                int newCol = col + direction.getColChange();

                // Check if Enemy Can Move to the New Position
                if (!canEnemyMoveTo(newRow, newCol, enemyManager)) {
                    continue;
                }

                // Check if Already Visited
                if (visited[newRow][newCol]) {
                    continue;
                }

                // Mark as Visited and Store Previous Position
                visited[newRow][newCol] = true;

                // Store the Previous Position for Path Reconstruction
                previousRow[newRow][newCol] = row;
                previousCol[newRow][newCol] = col;

                // Add to Queue
                queue.add(new int[] { newRow, newCol });
            }
        }

        // No Path Found
        return Collections.emptyList();
    }

    // Check if Enemy Can Move to a Position
    private boolean canEnemyMoveTo(int row, int col, EnemyManager enemyManager) {

        // Check if Position is Valid
        if (!gameBoard.isValidPosition(row, col)) {
            return false;
        }

        // Check if Position is Occupied by a Game Object
        if (gameBoard.getGameObjectAt(row, col) != null) {
            return false;
        }

        // Check if Position is Occupied by Another Enemy
        if (enemyManager.getEnemyAt(row, col) != null) {
            return false;
        }

        return true;
    }

    // Build the Path from Start to Target
    private List<int[]> buildPath(int startRow, int startCol, int targetRow, int targetCol, int[][] previousRow,
            int[][] previousCol) {

        // List to Store the Path
        List<int[]> path = new ArrayList<>();

        // Start from the Target Position
        int currentRow = targetRow;
        int currentCol = targetCol;

        // Backtrack from Target to Start Using Previous Positions
        while (currentRow != startRow || currentCol != startCol) {

            // Add Current Position to Path
            path.add(new int[] { currentRow, currentCol });

            // Get Previous Position
            int nextRow = previousRow[currentRow][currentCol];
            int nextCol = previousCol[currentRow][currentCol];

            // No Path Found (Should Not Happen if BFS is Correct)
            if (nextRow == -1 || nextCol == -1) {
                return Collections.emptyList();
            }

            // Move to Previous Position
            currentRow = nextRow;
            currentCol = nextCol;
        }

        // Reverse to get Start -> Target
        Collections.reverse(path);

        // Return the Path
        return path;
    }
}
