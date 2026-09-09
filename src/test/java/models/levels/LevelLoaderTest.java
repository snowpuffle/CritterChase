package models.levels;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// LevelLoaderTest Tests Level Loading and Validation
public class LevelLoaderTest {

    // Test that a Valid Level Loads Successfully
    @Test
    void testLoadValidLevel() {

        LevelDefinition definition = LevelLoader.load(1);

        assertNotNull(definition);
        assertEquals(1, definition.levelNumber());
        assertNotNull(definition.player());
        assertNotNull(definition.assets());
        assertNotNull(definition.maze());
    }

    // Test that Loaded Level Has Correct Maze Size
    @Test
    void testLoadedLevelHasCorrectMazeSize() {

        LevelDefinition definition = LevelLoader.load(1);

        assertEquals(15, definition.maze().size());

        for (String row : definition.maze()) {
            assertEquals(15, row.length());
        }
    }

    // Test that Multiple Valid Levels Can Load
    @Test
    void testLoadMultipleLevels() {

        LevelDefinition levelOne = LevelLoader.load(1);

        LevelDefinition levelTwo = LevelLoader.load(2);

        assertEquals(1, levelOne.levelNumber());
        assertEquals(2, levelTwo.levelNumber());
    }

    // Test that Missing Level Throws Exception
    @Test
    void testLoadMissingLevel() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> LevelLoader.load(999));

        assertTrue(
                exception.getMessage().contains(
                        "Level File Not Found"));
    }

    // Test that Level Number Must Match Requested Level
    @Test
    void testLevelNumberMatchesRequestedLevel() {

        LevelDefinition definition = LevelLoader.load(1);

        assertEquals(
                1,
                definition.levelNumber());
    }

    // Test that Empty Maze Is Rejected
    @Test
    void testEmptyMazeIsRejected() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new LevelDefinition(
                        1,
                        10,
                        null,
                        null,
                        java.util.List.of()));
    }
}