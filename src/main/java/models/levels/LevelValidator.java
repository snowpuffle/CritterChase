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

        if (definition == null) {
            throw new IllegalArgumentException(
                    "LevelDefinition Cannot Be Null.");
        }

        if (definition.levelNumber() <= 0) {
            throw new IllegalArgumentException(
                    "Level Number Must Be Greater Than Zero.");
        }

        if (definition.maxScore() < 0) {
            throw new IllegalArgumentException(
                    "Maximum Score Cannot Be Negative.");
        }

        if (definition.player() == null) {
            throw new IllegalArgumentException(
                    "Player Definition Cannot Be Null.");
        }

        if (definition.assets() == null) {
            throw new IllegalArgumentException(
                    "Level Assets Cannot Be Null.");
        }

        if (definition.maze() == null
                || definition.maze().isEmpty()) {

            throw new IllegalArgumentException(
                    "Level Maze Cannot Be Empty.");
        }
    }

    // Validate Maze Dimensions and Characters
    private static void validateMaze(LevelDefinition definition) {

        int height = definition.maze().size();
        int width = definition.maze().get(0).length();

        if (height != GameBoardConfig.HEIGHT) {
            throw new IllegalArgumentException(
                    "Level Must Have Exactly "
                            + GameBoardConfig.HEIGHT
                            + " Rows.");
        }

        if (width != GameBoardConfig.WIDTH) {
            throw new IllegalArgumentException(
                    "Level Must Have Exactly "
                            + GameBoardConfig.WIDTH
                            + " Columns.");
        }

        for (int row = 0; row < height; row++) {

            String mazeRow = definition.maze().get(row);

            if (mazeRow == null) {
                throw new IllegalArgumentException(
                        "Maze Row Cannot Be Null.");
            }

            if (mazeRow.length() != width) {
                throw new IllegalArgumentException(
                        "All Maze Rows Must Have Matching Width.");
            }

            for (int col = 0; col < width; col++) {

                char cell = mazeRow.charAt(col);

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
    }

    // Validate Player Starting Position
    private static void validatePlayerPosition(
            LevelDefinition definition) {

        int height = definition.maze().size();
        int width = definition.maze().get(0).length();

        int playerRow = definition.player().row();
        int playerCol = definition.player().col();

        if (playerRow < 0 || playerRow >= height) {
            throw new IllegalArgumentException(
                    "Player Row Exists Outside Maze.");
        }

        if (playerCol < 0 || playerCol >= width) {
            throw new IllegalArgumentException(
                    "Player Column Exists Outside Maze.");
        }

        char playerCell = definition.maze()
                .get(playerRow)
                .charAt(playerCol);

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

        int exitCount = 0;

        for (String row : definition.maze()) {

            for (int col = 0; col < row.length(); col++) {

                if (row.charAt(col) == 'X') {
                    exitCount++;
                }
            }
        }

        if (exitCount != 1) {
            throw new IllegalArgumentException(
                    "Level Must Contain Exactly One Exit.");
        }
    }

    // Validate Player Has a Path to Exit
    private static void validateExitIsReachable(
            LevelDefinition definition) {

        int height = definition.maze().size();
        int width = definition.maze().get(0).length();

        int startRow = definition.player().row();
        int startCol = definition.player().col();

        int exitRow = -1;
        int exitCol = -1;

        // Find Exit Position
        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {

                if (definition.maze()
                        .get(row)
                        .charAt(col) == 'X') {

                    exitRow = row;
                    exitCol = col;
                }
            }
        }

        boolean[][] visited = new boolean[height][width];

        Queue<int[]> queue = new ArrayDeque<>();

        queue.add(new int[] {
                startRow,
                startCol
        });

        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {

            int[] current = queue.remove();

            int row = current[0];
            int col = current[1];

            // Exit Has Been Reached
            if (row == exitRow && col == exitCol) {
                return;
            }

            for (Direction direction : Direction.values()) {

                int newRow = row + direction.getRowChange();

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

                char cell = definition.maze()
                        .get(newRow)
                        .charAt(newCol);

                // Walls Block the Path
                if (cell == '#' || cell == '%') {
                    continue;
                }

                visited[newRow][newCol] = true;

                queue.add(new int[] {
                        newRow,
                        newCol
                });
            }
        }

        throw new IllegalArgumentException(
                "Level "
                        + definition.levelNumber()
                        + " Has No Path From Player to Exit.");
    }
}
