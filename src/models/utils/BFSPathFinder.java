package models.utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import models.entities.Enemy;

// BFSPathFinder Finds the Shortest Path Through the Maze.
public class BFSPathFinder {

    private final GameBoard gameBoard;

    public BFSPathFinder(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    // Find the Shortest Path to the Target
    public List<int[]> findPath(int startRow, int startCol, int targetRow, int targetCol, List<Enemy> enemies) {

        // Create a Queue for BFS and a Set to Track Visited Positions
        Queue<int[]> queue = createQueue(startRow, startCol);
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();

        // Mark the Starting Position as Visited
        visited.add(key(startRow, startCol));

        // Perform BFS to Find the Shortest Path
        while (!queue.isEmpty()) {

            // Get the Current Position from the Queue
            int[] current = queue.remove();

            // Check if the Current Position is the Target Position
            if (isTarget(current, targetRow, targetCol)) {
                return buildPath(previous, key(startRow, startCol), key(targetRow, targetCol));
            }

            // Explore the Neighbors of the Current Position
            exploreNeighbors(current, queue, visited, previous, enemies);
        }

        // Return an Empty List if No Path is Found
        return Collections.emptyList();
    }

    // Check if Enemy Can Move to a Position
    private boolean canEnemyMoveTo(int row, int col, List<Enemy> enemies) {

        // Check if the Position is Valid and Not Occupied by a Wall or Another Enemy
        if (!gameBoard.isValidPosition(row, col)) {
            return false;
        }

        // Check if the Position is Occupied by a Wall
        if (gameBoard.getGameObjectAt(row, col) != null) {
            return false;
        }

        // Prevent Enemies from Walking Through Each Other
        for (Enemy enemy : enemies) {

            // Check if the Position is Occupied by Another Enemy
            if (enemy.getRow() == row && enemy.getCol() == col) {
                return false;
            }
        }

        return true;
    }

    // Build the Path from the Start Position to the Target
    private List<int[]> buildPath(Map<String, String> previous, String startKey, String targetKey) {

        // Create a List to Store the Path
        List<int[]> path = new ArrayList<>();

        // Start Backtracking from the Target Position
        String currentKey = targetKey;

        // Backtrack from the Target to the Start
        while (!currentKey.equals(startKey)) {

            // Split the Current Key into Row and Column
            String[] position = currentKey.split(",");

            // Convert Row and Column to Integers
            int row = Integer.parseInt(position[0]);
            int col = Integer.parseInt(position[1]);

            // Add the Current Position to the Path
            path.add(new int[] { row, col });

            // Move to the Previous Position
            currentKey = previous.get(currentKey);

            // No Path Found
            if (currentKey == null) {
                return Collections.emptyList();
            }
        }

        // Reverse the Path to Get the Correct Order from Start to Target
        Collections.reverse(path);

        return path;
    }

    // Create a Queue for BFS Starting from the Given Position
    private Queue<int[]> createQueue(int row, int col) {

        // Create a Queue for BFS
        Queue<int[]> queue = new ArrayDeque<>();

        // Add the Starting Position to the Queue
        queue.add(new int[] { row, col });

        // Return the Queue
        return queue;
    }

    // Check if the Given Position is the Target Position
    private boolean isTarget(int[] position, int targetRow, int targetCol) {
        return position[0] == targetRow && position[1] == targetCol;
    }

    // Explore the Neighbors of the Current Position
    private void exploreNeighbors(int[] current, Queue<int[]> queue, Set<String> visited, Map<String, String> previous,
            List<Enemy> enemies) {

        // Get the Current Row and Column
        int row = current[0];
        int col = current[1];

        // Generate a Unique Key for the Current Position
        String currentKey = key(row, col);

        // Explore Each Direction (Up, Down, Left, Right)
        for (Direction direction : Direction.values()) {

            // Calculate the New Position Based on the Direction
            int newRow = row + direction.getRowChange();
            int newCol = col + direction.getColChange();

            // Check if the New Position is Valid and Not Visited
            if (!canEnemyMoveTo(newRow, newCol, enemies)) {
                continue;
            }

            // Generate a Unique Key for the New Position
            String newKey = key(newRow, newCol);

            // Skip if the New Position has Already Been Visited
            if (visited.contains(newKey)) {
                continue;
            }

            // Mark the New Position as Visited and Record the Previous Position
            visited.add(newKey);

            // Record the Previous Position for Backtracking
            previous.put(newKey, currentKey);

            // Add the New Position to the Queue for Further Exploration
            queue.add(new int[] { newRow, newCol });
        }
    }

    // Generate a Unique Key for a Position
    private String key(int row, int col) {
        return row + "," + col;
    }
}