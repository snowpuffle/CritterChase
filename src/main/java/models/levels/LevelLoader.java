package models.levels;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;

import models.utils.Direction;

// LevelLoader Class Loads and Validates Level JSON Files
public class LevelLoader {

    // Create Jackson ObjectMapper for JSON Processing
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Load Level Definition from JSON File
    public static LevelDefinition load(int levelNumber) {

        // Create Level Resource Path
        String resourcePath = "/levels/level_" + levelNumber + ".json";

        // Open Level JSON Resource
        try (InputStream inputStream = LevelLoader.class.getResourceAsStream(resourcePath)) {

            // Reject Missing Level Resource
            if (inputStream == null) {
                throw new IllegalArgumentException(
                        "Level File Not Found: " + resourcePath);
            }

            // Read Level JSON into LevelDefinition
            LevelDefinition definition = OBJECT_MAPPER.readValue(
                    inputStream,
                    LevelDefinition.class);

            // Validate Loaded Level Definition
            validate(definition);

            // Validate Requested Level Number
            if (definition.levelNumber() != levelNumber) {
                throw new IllegalArgumentException(
                        "Level Number Does Not Match Requested Level: "
                                + levelNumber);
            }

            // Return Validated Level Definition
            return definition;

        } catch (IOException exception) {

            // Convert JSON Loading Error into IllegalArgumentException
            throw new IllegalArgumentException(
                    "Unable to Load Level: " + levelNumber,
                    exception);
        }
    }

    // Validate LevelDefinition Before Creating Level
    private static void validate(LevelDefinition definition) {

        // Reject Null LevelDefinition
        if (definition == null) {
            throw new IllegalArgumentException(
                    "LevelDefinition Cannot Be Null.");
        }

        // Validate Level Number
        if (definition.levelNumber() <= 0) {
            throw new IllegalArgumentException(
                    "Level Number Must Be Greater Than Zero.");
        }

        // Validate Maximum Score
        if (definition.maxScore() < 0) {
            throw new IllegalArgumentException(
                    "Maximum Score Cannot Be Negative.");
        }

        // Validate Player Definition
        if (definition.player() == null) {
            throw new IllegalArgumentException(
                    "Player Definition Cannot Be Null.");
        }

        // Validate Asset Definition
        if (definition.assets() == null) {
            throw new IllegalArgumentException(
                    "Level Assets Cannot Be Null.");
        }

        // Validate Maze Exists
        if (definition.maze() == null
                || definition.maze().isEmpty()) {

            throw new IllegalArgumentException(
                    "Level Maze Cannot Be Empty.");
        }

        // Get Maze Height
        int height = definition.maze().size();

        // Get Maze Width
        int width = definition.maze().get(0).length();

        // Validate Maze Rows
        for (int row = 0; row < height; row++) {

            // Get Current Maze Row
            String mazeRow = definition.maze().get(row);

            // Reject Null Maze Row
            if (mazeRow == null) {
                throw new IllegalArgumentException(
                        "Maze Row Cannot Be Null.");
            }

            // Validate Maze Row Width
            if (mazeRow.length() != width) {
                throw new IllegalArgumentException(
                        "All Maze Rows Must Have Matching Width.");
            }

            // Validate Maze Characters
            for (int col = 0; col < width; col++) {

                // Get Current Maze Character
                char cell = mazeRow.charAt(col);

                // Check Valid Maze Character
                if ("#%FEX ".indexOf(cell) == -1) {
                    throw new IllegalArgumentException(
                            "Invalid Maze Character '"
                                    + cell
                                    + "' at Row "
                                    + row
                                    + ", Column "
                                    + col
                                    + ".");
                }
            }
        }

        // Validate Player Position
        validatePlayerPosition(
                definition,
                height,
                width);

        // Validate Exit Count
        validateExitCount(definition);

        // Validate Exit Reachability
        validateExitIsReachable(definition);
    }

    // Validate Player Starting Position
    private static void validatePlayerPosition(
            LevelDefinition definition,
            int height,
            int width) {

        // Get Player Row
        int playerRow = definition.player().row();

        // Get Player Column
        int playerCol = definition.player().col();

        // Validate Player Row Boundaries
        if (playerRow < 0 || playerRow >= height) {
            throw new IllegalArgumentException(
                    "Player Row Exists Outside Maze.");
        }

        // Validate Player Column Boundaries
        if (playerCol < 0 || playerCol >= width) {
            throw new IllegalArgumentException(
                    "Player Column Exists Outside Maze.");
        }

        // Get Player Starting Cell
        char playerCell = definition.maze()
                .get(playerRow)
                .charAt(playerCol);

        // Prevent Player Starting Inside Blocking Object
        if (playerCell == '#'
                || playerCell == '%'
                || playerCell == 'X'
                || playerCell == 'E') {

            throw new IllegalArgumentException(
                    "Player Starting Position Cannot Contain '"
                            + playerCell
                            + "' at Row "
                            + playerRow
                            + ", Column "
                            + playerCol
                            + ".");
        }
    }

    // Validate Exactly One Exit Exists
    private static void validateExitCount(
            LevelDefinition definition) {

        // Start Exit Counter
        int exitCount = 0;

        // Loop Through Maze Rows
        for (String row : definition.maze()) {

            // Loop Through Maze Columns
            for (int col = 0; col < row.length(); col++) {

                // Count Exit Characters
                if (row.charAt(col) == 'X') {
                    exitCount++;
                }
            }
        }

        // Require Exactly One Exit
        if (exitCount != 1) {
            throw new IllegalArgumentException(
                    "Level Must Contain Exactly One Exit.");
        }
    }

    // Validate Player Has a Path to Exit
    private static void validateExitIsReachable(
            LevelDefinition definition) {

        // Get Maze Height
        int height = definition.maze().size();

        // Get Maze Width
        int width = definition.maze().get(0).length();

        // Get Player Starting Row
        int startRow = definition.player().row();

        // Get Player Starting Column
        int startCol = definition.player().col();

        // Store Exit Row
        int exitRow = -1;

        // Store Exit Column
        int exitCol = -1;

        // Find Exit Position
        for (int row = 0; row < height; row++) {

            // Check Each Maze Column
            for (int col = 0; col < width; col++) {

                // Find Exit Character
                if (definition.maze().get(row).charAt(col) == 'X') {
                    exitRow = row;
                    exitCol = col;
                }
            }
        }

        // Create Visited Position Tracker
        boolean[][] visited = new boolean[height][width];

        // Create BFS Queue
        Queue<int[]> queue = new ArrayDeque<>();

        // Add Player Starting Position
        queue.add(
                new int[] {
                        startRow,
                        startCol
                });

        // Mark Player Position as Visited
        visited[startRow][startCol] = true;

        // Continue Searching While Positions Remain
        while (!queue.isEmpty()) {

            // Remove Next Position from Queue
            int[] current = queue.remove();

            // Get Current Row
            int row = current[0];

            // Get Current Column
            int col = current[1];

            // Check if Exit Has Been Reached
            if (row == exitRow && col == exitCol) {
                return;
            }

            // Check All Available Directions
            for (Direction direction : Direction.values()) {

                // Calculate New Row
                int newRow = row + direction.getRowChange();

                // Calculate New Column
                int newCol = col + direction.getColChange();

                // Skip Positions Outside Maze
                if (newRow < 0
                        || newRow >= height
                        || newCol < 0
                        || newCol >= width) {

                    continue;
                }

                // Skip Previously Visited Positions
                if (visited[newRow][newCol]) {
                    continue;
                }

                // Get New Maze Cell
                char cell = definition.maze()
                        .get(newRow)
                        .charAt(newCol);

                // Skip Wall Positions
                if (cell == '#' || cell == '%') {
                    continue;
                }

                // Mark Position as Visited
                visited[newRow][newCol] = true;

                // Add Position to BFS Queue
                queue.add(
                        new int[] {
                                newRow,
                                newCol
                        });
            }
        }

        // Reject Level Without Player-to-Exit Path
        throw new IllegalArgumentException(
                "Level "
                        + definition.levelNumber()
                        + " Has No Path From Player to Exit.");
    }
}