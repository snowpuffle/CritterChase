package models.utils;

import java.util.*;

import models.entities.Enemy;

// BFSPathFinder Implements the Breadth-First Search (BFS) Algorithm to Find the Shortest Path in the Maze.
public class BFSPathFinder {

    // Directions for Up, Down, Left, Right Movement
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    // Game Board to Check Valid Positions
    private final GameBoard gameBoard;

    // BFSPathFinder Constructor
    public BFSPathFinder(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    // Find the Shortest Path to the Player by Exploring Adjacent Positions
    // and Tracking Visited Positions while Avoiding Obstacles
    public List<int[]> findPath(int startRow, int startCol, int targetRow, int targetCol, List<Enemy> enemies) {

        // Initialize the Queue, Visited Set, and Previous Map for BFS
        Queue<int[]> queue = createQueue(startRow, startCol);
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();

        // Mark the Starting Position as Visited
        visited.add(key(startRow, startCol));

        // Perform BFS Until the Queue is Empty
        while (!queue.isEmpty()) {

            // Dequeue the Current Position from the Queue
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

    // Create a Queue for BFS Starting from the Given Position
    private Queue<int[]> createQueue(int row, int col) {

        // Initialize a Queue to Store Positions for BFS
        Queue<int[]> queue = new ArrayDeque<>();

        // Add the Starting Position to the Queue
        queue.add(new int[] { row, col });

        return queue;
    }

    // Check if the Given Position is the Target Position
    private boolean isTarget(int[] position, int targetRow, int targetCol) {

        // Check if the Row and Column of the Position Match the Target Position
        return position[0] == targetRow && position[1] == targetCol;
    }

    // Check if the Enemy Can Move to the Specified Position
    private boolean canEnemyMoveTo(int row, int col, List<Enemy> enemies) {

        // Position must be inside the game board
        if (!gameBoard.isValidPosition(row, col)) {
            return false;
        }

        // Position must not contain a game object
        if (gameBoard.getGameObjectAt(row, col) != null) {
            return false;
        }

        // Position must not contain another enemy
        for (Enemy enemy : enemies) {
            if (enemy.getRow() == row && enemy.getCol() == col) {
                return false;
            }
        }

        return true;
    }

    // Explore the Neighbors of the Current Position and Add Them to the Queue
    private void exploreNeighbors(int[] current, Queue<int[]> queue, Set<String> visited, Map<String, String> previous,
            List<Enemy> enemies) {

        // Extract Row and Column from the Current Position
        int row = current[0];
        int col = current[1];

        // Generate a Unique Key for the Current Position
        String currentKey = key(row, col);

        // Explore All Possible Directions (Up, Down, Left, Right)
        for (int[] direction : DIRECTIONS) {

            // Calculate the New Position Based on the Current Position and Direction
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            // Check if the New Position is Valid and Not Visited
            if (!canEnemyMoveTo(newRow, newCol, enemies)) {
                continue;
            }

            // Generate a Unique Key for the New Position
            String newKey = key(newRow, newCol);

            // Check if the New Position has Already Been Visited
            if (visited.contains(newKey)) {
                continue;
            }

            // Mark the New Position as Visited and Record the Previous Position
            visited.add(newKey);
            previous.put(newKey, currentKey);

            // Add the New Position to the Queue for Further Exploration
            queue.add(new int[] { newRow, newCol });
        }
    }

    // Build the Path from the Start Position to the Target Position
    private List<int[]> buildPath(Map<String, String> previous, String startKey, String targetKey) {

        // List to Store the Path from Start to Target
        List<int[]> path = new ArrayList<>();

        // Start from the Target Position and Backtrack to the Start Position
        String currentKey = targetKey;

        // Backtrack Until Reaching the Start Position
        while (!currentKey.equals(startKey)) {

            // Split the Current Key to Get Row and Column
            String[] position = currentKey.split(",");

            // Parse Row and Column from the Position
            int row = Integer.parseInt(position[0]);
            int col = Integer.parseInt(position[1]);

            // Add the Current Position to the Path
            path.add(new int[] { row, col });

            // Move to the Previous Position in the Path
            currentKey = previous.get(currentKey);

            // If the Current Key is Null, it Means There is No Path to the Start Position
            if (currentKey == null) {
                return Collections.emptyList();
            }
        }

        // Add the Start Position to the Path
        Collections.reverse(path);

        // Return the Complete Path from Start to Target
        return path;
    }

    // Generate a Unique Key for a Position Based on Row and Column
    private String key(int row, int col) {
        return row + "," + col;
    }
}
