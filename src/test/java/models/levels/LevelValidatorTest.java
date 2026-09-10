package models.levels;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import models.game.GameBoardConfig;
import models.utils.PlayerStart;

class LevelValidatorTest {

    @Test
    void validateNullDefinitionThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(null));
    }

    @Test
    void validateInvalidLevelNumberThrowsException() {
        LevelDefinition definition = createValidDefinition();

        LevelDefinition invalid = new LevelDefinition(
                0,
                definition.maxScore(),
                definition.player(),
                definition.assets(),
                definition.maze());

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateNegativeMaxScoreThrowsException() {
        LevelDefinition definition = createValidDefinition();

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                -1,
                definition.player(),
                definition.assets(),
                definition.maze());

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateMissingPlayerThrowsException() {
        LevelDefinition definition = createValidDefinition();

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                null,
                definition.assets(),
                definition.maze());

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateMissingAssetsThrowsException() {
        LevelDefinition definition = createValidDefinition();

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                definition.player(),
                null,
                definition.maze());

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateEmptyMazeThrowsException() {
        // LevelDefinition itself rejects an empty maze,
        // so this verifies that invalid maze data cannot be created.
        assertThrows(
                IllegalArgumentException.class,
                () -> new LevelDefinition(
                        1,
                        100,
                        new PlayerStart(1, 1),
                        createValidDefinition().assets(),
                        List.of()));
    }

    @Test
    void validateIncorrectMazeHeightThrowsException() {
        LevelDefinition definition = createValidDefinition();

        List<String> maze = new ArrayList<>(definition.maze());
        maze.remove(maze.size() - 1);

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                definition.player(),
                definition.assets(),
                maze);

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateIncorrectMazeWidthThrowsException() {
        LevelDefinition definition = createValidDefinition();

        List<String> maze = new ArrayList<>(definition.maze());

        String firstRow = maze.get(0);
        maze.set(0, firstRow.substring(0, firstRow.length() - 1));

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                definition.player(),
                definition.assets(),
                maze);

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateNullMazeRowThrowsException() {
        LevelDefinition definition = createValidDefinition();

        List<String> maze = new ArrayList<>(definition.maze());
        maze.set(0, null);

        // List.copyOf() in LevelDefinition rejects null rows.
        assertThrows(
                NullPointerException.class,
                () -> new LevelDefinition(
                        definition.levelNumber(),
                        definition.maxScore(),
                        definition.player(),
                        definition.assets(),
                        maze));
    }

    @Test
    void validateInvalidMazeCharacterThrowsException() {
        LevelDefinition definition = createValidDefinition();

        List<String> maze = new ArrayList<>(definition.maze());
        String row = maze.get(0);

        maze.set(0, "@" + row.substring(1));

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                definition.player(),
                definition.assets(),
                maze);

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validatePlayerRowOutsideMazeThrowsException() {
        LevelDefinition definition = createValidDefinition();

        PlayerStart player = new PlayerStart(-1, 1);

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                player,
                definition.assets(),
                definition.maze());

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validatePlayerColumnOutsideMazeThrowsException() {
        LevelDefinition definition = createValidDefinition();

        PlayerStart player = new PlayerStart(
                1,
                GameBoardConfig.WIDTH);

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                player,
                definition.assets(),
                definition.maze());

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validatePlayerStartingOnWallThrowsException() {
        LevelDefinition definition = createValidDefinition();

        List<String> maze = new ArrayList<>(definition.maze());

        int playerRow = definition.player().row();
        int playerCol = definition.player().col();

        maze.set(
                playerRow,
                replaceCharacter(
                        maze.get(playerRow),
                        playerCol,
                        '#'));

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                definition.player(),
                definition.assets(),
                maze);

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateNoExitThrowsException() {
        LevelDefinition definition = createValidDefinition();

        List<String> maze = new ArrayList<>(definition.maze());

        int[] exit = findExit(maze);

        maze.set(
                exit[0],
                replaceCharacter(
                        maze.get(exit[0]),
                        exit[1],
                        ' '));

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                definition.player(),
                definition.assets(),
                maze);

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateMultipleExitsThrowsException() {
        LevelDefinition definition = createValidDefinition();

        List<String> maze = new ArrayList<>(definition.maze());

        int[] extraExit = findOpenCell(
                maze,
                definition.player().row(),
                definition.player().col());

        maze.set(
                extraExit[0],
                replaceCharacter(
                        maze.get(extraExit[0]),
                        extraExit[1],
                        'X'));

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                definition.player(),
                definition.assets(),
                maze);

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateUnreachableExitThrowsException() {
        LevelDefinition definition = createValidDefinition();

        List<String> maze = new ArrayList<>(definition.maze());

        int[] exit = findExit(maze);

        int exitRow = exit[0];
        int exitCol = exit[1];

        // Surround the exit with walls.
        if (exitRow > 0) {
            maze.set(
                    exitRow - 1,
                    replaceCharacter(
                            maze.get(exitRow - 1),
                            exitCol,
                            '#'));
        }

        if (exitRow < maze.size() - 1) {
            maze.set(
                    exitRow + 1,
                    replaceCharacter(
                            maze.get(exitRow + 1),
                            exitCol,
                            '#'));
        }

        if (exitCol > 0) {
            maze.set(
                    exitRow,
                    replaceCharacter(
                            maze.get(exitRow),
                            exitCol - 1,
                            '#'));
        }

        if (exitCol < maze.get(exitRow).length() - 1) {
            maze.set(
                    exitRow,
                    replaceCharacter(
                            maze.get(exitRow),
                            exitCol + 1,
                            '#'));
        }

        LevelDefinition invalid = new LevelDefinition(
                definition.levelNumber(),
                definition.maxScore(),
                definition.player(),
                definition.assets(),
                maze);

        assertThrows(
                IllegalArgumentException.class,
                () -> LevelValidator.validate(invalid));
    }

    @Test
    void validateValidDefinitionDoesNotThrowException() {
        LevelDefinition definition = createValidDefinition();

        assertDoesNotThrow(
                () -> LevelValidator.validate(definition));
    }

    /**
     * Loads an existing valid level to use as the base
     * for validator tests.
     */
    private LevelDefinition createValidDefinition() {
        return LevelLoader.load(1);
    }

    /**
     * Finds the exit represented by 'X'.
     */
    private int[] findExit(List<String> maze) {
        for (int row = 0; row < maze.size(); row++) {
            int col = maze.get(row).indexOf('X');

            if (col >= 0) {
                return new int[] { row, col };
            }
        }

        throw new IllegalStateException("Test level does not contain an exit.");
    }

    /**
     * Finds an open cell that can be changed into an additional exit.
     */
    private int[] findOpenCell(
            List<String> maze,
            int playerRow,
            int playerCol) {
        for (int row = 0; row < maze.size(); row++) {
            for (int col = 0; col < maze.get(row).length(); col++) {
                char cell = maze.get(row).charAt(col);

                if (row == playerRow && col == playerCol) {
                    continue;
                }

                if (cell == ' ') {
                    return new int[] { row, col };
                }
            }
        }

        throw new IllegalStateException("Test level does not contain an open cell.");
    }

    /**
     * Replaces one character in a maze row.
     */
    private String replaceCharacter(
            String row,
            int column,
            char replacement) {
        StringBuilder builder = new StringBuilder(row);
        builder.setCharAt(column, replacement);
        return builder.toString();
    }
}