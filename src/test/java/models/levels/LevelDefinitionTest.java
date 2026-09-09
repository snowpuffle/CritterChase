package models.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import models.utils.PlayerStart;

// Test LevelDefinition Core Game Rules
// - Creates a Valid Level Definition
// - Stores the Level Number
// - Stores the Maximum Score
// - Stores the Player Starting Position
// - Stores the Level Assets
// - Stores the Maze
// - Rejects a Null Maze
// - Rejects an Empty Maze
// - Protects the Maze From External Changes
class LevelDefinitionTest {

    // Create Test Player Starting Position
    private PlayerStart createTestPlayerStart() {

        // Create Player Starting Position
        return new PlayerStart(0, 0);
    }

    // Create Test Level Assets
    private LevelAssets createTestAssets() {

        // Create Level Assets
        return new LevelAssets(
                "woodlands.png",
                "player.png",
                "food.png",
                "enemy.png",
                "wall1.png",
                "wall2.png",
                "exit.png");
    }

    // Create Test Maze
    private List<String> createTestMaze() {

        // Create Valid Test Maze
        return List.of(
                "    X",
                "## F#",
                "     ",
                "     ",
                "     ");
    }

    // Create Valid Level Definition
    private LevelDefinition createTestDefinition() {

        // Create Test Level Definition
        return new LevelDefinition(
                1,
                20,
                createTestPlayerStart(),
                createTestAssets(),
                createTestMaze());
    }

    // Test Valid Level Definition
    @Test
    void validLevelDefinitionIsCreated() {

        // Create Test Level Definition
        LevelDefinition definition = createTestDefinition();

        // Verify Level Definition Exists
        assertNotNull(definition);

        // Verify Level Number
        assertEquals(1, definition.levelNumber());

        // Verify Maximum Score
        assertEquals(20, definition.maxScore());

        // Verify Player Starting Position
        assertEquals(0, definition.player().row());
        assertEquals(0, definition.player().col());

        // Verify Level Assets Exist
        assertNotNull(definition.assets());

        // Verify Maze Exists
        assertNotNull(definition.maze());
    }

    // Test Level Number
    @Test
    void levelNumberIsStoredCorrectly() {

        // Create Test Level Definition
        LevelDefinition definition = createTestDefinition();

        // Verify Level Number
        assertEquals(1, definition.levelNumber());
    }

    // Test Maximum Score
    @Test
    void maxScoreIsStoredCorrectly() {

        // Create Test Level Definition
        LevelDefinition definition = createTestDefinition();

        // Verify Maximum Score
        assertEquals(20, definition.maxScore());
    }

    // Test Player Starting Position
    @Test
    void playerStartIsStoredCorrectly() {

        // Create Test Level Definition
        LevelDefinition definition = createTestDefinition();

        // Verify Player Starting Row
        assertEquals(0, definition.player().row());

        // Verify Player Starting Column
        assertEquals(0, definition.player().col());
    }

    // Test Level Assets
    @Test
    void assetsAreStoredCorrectly() {

        // Create Test Level Definition
        LevelDefinition definition = createTestDefinition();

        // Verify Level Assets Exist
        assertNotNull(definition.assets());

        // Verify Player Image
        assertEquals(
                "player.png",
                definition.assets().player());

        // Verify Food Image
        assertEquals(
                "food.png",
                definition.assets().food());

        // Verify Enemy Image
        assertEquals(
                "enemy.png",
                definition.assets().enemy());

        // Verify Wall Images
        assertEquals(
                "wall1.png",
                definition.assets().wall1());

        assertEquals(
                "wall2.png",
                definition.assets().wall2());

        // Verify Exit Image
        assertEquals(
                "exit.png",
                definition.assets().exit());
    }

    // Test Maze Storage
    @Test
    void mazeIsStoredCorrectly() {

        // Create Test Level Definition
        LevelDefinition definition = createTestDefinition();

        // Verify Maze Contains Expected Number of Rows
        assertEquals(5, definition.maze().size());

        // Verify First Maze Row
        assertEquals(
                "    X",
                definition.maze().get(0));

        // Verify Second Maze Row
        assertEquals(
                "## F#",
                definition.maze().get(1));
    }

    // Test Null Maze Validation
    @Test
    void nullMazeIsRejected() {

        // Attempt to Create Level Definition With Null Maze
        assertThrows(
                IllegalArgumentException.class,
                () -> new LevelDefinition(
                        1,
                        20,
                        createTestPlayerStart(),
                        createTestAssets(),
                        null));
    }

    // Test Empty Maze Validation
    @Test
    void emptyMazeIsRejected() {

        // Create Empty Maze
        List<String> emptyMaze = List.of();

        // Attempt to Create Level Definition With Empty Maze
        assertThrows(
                IllegalArgumentException.class,
                () -> new LevelDefinition(
                        1,
                        20,
                        createTestPlayerStart(),
                        createTestAssets(),
                        emptyMaze));
    }

    // Test Maze Immutability
    @Test
    void mazeIsProtectedFromExternalChanges() {

        // Create Mutable Maze
        List<String> maze = new ArrayList<>(
                createTestMaze());

        // Create Level Definition
        LevelDefinition definition = new LevelDefinition(
                1,
                20,
                createTestPlayerStart(),
                createTestAssets(),
                maze);

        // Modify Original Maze
        maze.set(0, "XXXXX");

        // Verify Level Definition Maze Did Not Change
        assertEquals(
                "    X",
                definition.maze().get(0));
    }
}