package models.levels;

import java.util.ArrayDeque;
import java.util.Queue;

import models.game.GameBoardConfig;
import models.utils.Direction;

// LevelValidator Validates Level Definitions Before Gameplay
public class LevelValidator {

    // Validate a Complete Level Definition
    public static void validate(LevelDefinition definition) {

        // Validate Basic Level Information
        validateDefinition(definition);

        // Validate Maze Structure
        validateMaze(definition);

        // Validate Player Starting Position
        validatePlayerPosition(definition);

        // Validate Exit Configuration
        validateExitCount(definition);

        // Validate Player Can Reach Exit
        validateExitIsReachable(definition);
    }

    // Validate Level Definition Exists and Contains Required Data
    private static void validateDefinition(LevelDefinition definition) {

        // Check that the Level Definition Exists
        if (definition == null) {
            throw new IllegalArgumentException("LevelDefinition Cannot Be Null.");
        }

        // Check that the Level Number Is Valid
        if (definition.levelNumber() <= 0) {
            throw new IllegalArgumentException("Level Number Must Be Greater Than Zero.");
        }

        // Check that the Maximum Score Is Valid
        if (definition.maxScore() < 0) {
            throw new IllegalArgumentException("Maximum Score Cannot Be Negative.");
        }

        // Check that the Player Definition Exists
        if (definition.player() == null) {
            throw new IllegalArgumentException("Player Definition Cannot Be Null.");
        }

        // Check that the Level Assets Exist
        if (definition.assets() == null) {
            throw new IllegalArgumentException("Level Assets Cannot Be Null.");
        }

        // Check that the Maze Exists and Contains at Least One Row
        if (definition.maze() == null || definition.maze().isEmpty()) {
            throw new IllegalArgumentException("Level Maze Cannot Be Empty.");
        }
    }

    // Validate Maze Dimensions and Characters
    private static void validateMaze(LevelDefinition definition) {

        // Get Maze Dimensions
        int height = definition.maze().size();
        int width = definition.maze().get(0).length();

        // Check that the Maze Has the Required Number of Rows
        if (height != GameBoardConfig.HEIGHT) {
            throw new IllegalArgumentException("Level Must Have Exactly " + GameBoardConfig.HEIGHT + " Rows.");
        }

        // Check that the Maze Has the Required Number of Columns
        if (width != GameBoardConfig.WIDTH) {
            throw new IllegalArgumentException("Level Must Have Exactly " + GameBoardConfig.WIDTH + " Columns.");
        }

        // Validate Each Maze Row
        for (int row = 0; row < height; row++) {

            String mazeRow = definition.maze().get(row);

            // Check that the Current Maze Row Exists
            if (mazeRow == null) {
                throw new IllegalArgumentException("Maze Row Cannot Be Null.");
            }

            // Check that the Current Maze Row Has the Correct Width
            if (mazeRow.length() != width) {
                throw new IllegalArgumentException("All Maze Rows Must Have Matching Width.");
            }

            // Validate Each Maze Cell
            for (int col = 0; col < width; col++) {

                char cell = mazeRow.charAt(col);

                // # = Standard Wall
                // % = Alternate Wall
                // F = Food
                // E = Enemy
                // X = Exit
                // W = Weapon
                // ' ' = Empty Position
                if ("#%FEXW ".indexOf(cell) == -1) {
                    throw new IllegalArgumentException(
                            "Invalid Maze Character '" + cell + "' at Row " + row + ", Column " + col + ".");
                }
            }
        }
    }

    // Validate Player Starting Position
    private static void validatePlayerPosition(LevelDefinition definition) {

        // Get Maze Dimensions
        int height = definition.maze().size();
        int width = definition.maze().get(0).length();

        // Get Player Starting Position
        int playerRow = definition.player().row();
        int playerCol = definition.player().col();

        // Check that the Player Row Is Inside the Maze
        if (playerRow < 0 || playerRow >= height) {
            throw new IllegalArgumentException("Player Row Exists Outside Maze.");
        }

        // Check that the Player Column Is Inside the Maze
        if (playerCol < 0 || playerCol >= width) {
            throw new IllegalArgumentException("Player Column Exists Outside Maze.");
        }

        // Get the Maze Cell at the Player Starting Position
        char playerCell = definition.maze().get(playerRow).charAt(playerCol);

        // Prevent the Player from Starting on an Occupied Position
        if (playerCell == '#' || playerCell == '%' || playerCell == 'X' || playerCell == 'E') {
            throw new IllegalArgumentException(
                    "Player Starting Position Cannot Contain '" + playerCell + "' at Row " + playerRow + ", Column "
                            + playerCol + ".");
        }
    }

    // Validate Exactly One Exit Exists
    private static void validateExitCount(LevelDefinition definition) {

        // Track the Number of Exits Found
        int exitCount = 0;

        // Check Each Maze Row
        for (String row : definition.maze()) {

            // Check Each Cell in the Current Row
            for (int col = 0; col < row.length(); col++) {

                // Count Exit Characters
                if (row.charAt(col) == 'X') {
                    exitCount++;
                }
            }
        }

        // Require Exactly One Exit
        if (exitCount != 1) {
            throw new IllegalArgumentException("Level Must Contain Exactly One Exit.");
        }
    }

    // Validate Player Has a Path to Exit
    private static void validateExitIsReachable(LevelDefinition definition) {

        // Get Maze Dimensions
        int height = definition.maze().size();
        int width = definition.maze().get(0).length();

        // Get Player Starting Position
        int startRow = definition.player().row();
        int startCol = definition.player().col();

        // Store Exit Position
        int exitRow = -1;
        int exitCol = -1;

        // Search Maze for the Exit
        for (int row = 0; row < height; row++) {

            // Search Each Cell in the Current Row
            for (int col = 0; col < width; col++) {

                // Store the Exit Position When Found
                if (definition.maze().get(row).charAt(col) == 'X') {
                    exitRow = row;
                    exitCol = col;
                }
            }
        }

        // Track Maze Positions Already Visited
        boolean[][] visited = new boolean[height][width];

        // Create Queue for Breadth-First Search
        Queue<int[]> queue = new ArrayDeque<>();

        // Add Player Starting Position to the Queue
        queue.add(new int[] { startRow, startCol });

        // Mark Player Starting Position as Visited
        visited[startRow][startCol] = true;

        // Continue Searching Until All Reachable Positions Are Checked
        while (!queue.isEmpty()) {

            // Get the Next Position from the Queue
            int[] current = queue.remove();

            int row = current[0];
            int col = current[1];

            // Stop When the Exit Is Reached
            if (row == exitRow && col == exitCol) {
                return;
            }

            // Check Each Possible Movement Direction
            for (Direction direction : Direction.values()) {

                // Calculate the New Row
                int newRow = row + direction.getRowChange();

                // Calculate the New Column
                int newCol = col + direction.getColChange();

                // Skip Positions Outside the Maze
                if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
                    continue;
                }

                // Skip Positions That Have Already Been Visited
                if (visited[newRow][newCol]) {
                    continue;
                }

                // Get the Maze Cell at the New Position
                char cell = definition.maze().get(newRow).charAt(newCol);

                // Walls Block Movement
                if (cell == '#' || cell == '%') {
                    continue;
                }

                // Mark the New Position as Visited
                visited[newRow][newCol] = true;

                // Add the New Position to the Search Queue
                queue.add(new int[] { newRow, newCol });
            }
        }

        // Throw an Error When the Exit Cannot Be Reached
        throw new IllegalArgumentException(
                "Level " + definition.levelNumber() + " Has No Path From Player to Exit.");
    }
}