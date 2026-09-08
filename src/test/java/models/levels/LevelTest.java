package models.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.entities.Player;
import models.objects.Score;
import models.utils.Direction;

// Test Level Game Rules
class LevelTest {

    // Create Test Level for Unit Testing
    private static class TestLevel extends Level {

        // Test Level Constructor
        TestLevel(Player player, Score score) {
            super(player, 1, score);

            // Create Test Level Maze
            char[][] maze = {
                    {'P', ' ', 'F', ' ', 'X'},
                    {'#', '#', 'F', '#', '#'},
                    {' ', ' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', ' ', ' '}
            };

            // Build Test Level Objects
            createLevelObjects(
                    maze,
                    "food.png",
                    "enemy.png",
                    "wall1.png",
                    "wall2.png",
                    "exit.png");

            // Calculate Maximum Level Score
            calculateAndStoreMaxScore();
        }
    }

    // Test Valid Player Movement
    @Test
    void validMovementMovesPlayer() {

        // Create Player
        Player player = new Player(0, 0, "player.png");

        // Create Test Level
        TestLevel level = new TestLevel(player, new Score());

        // Move Player Right
        boolean moved = level.movePlayer(Direction.RIGHT);

        // Verify Player Moved Successfully
        assertTrue(moved);

        // Verify Player Row
        assertEquals(0, player.getRow());

        // Verify Player Column
        assertEquals(1, player.getCol());
    }

    // Test Invalid Player Movement
    @Test
    void invalidMovementDoesNotMovePlayer() {

        // Create Player
        Player player = new Player(0, 0, "player.png");

        // Create Test Level
        TestLevel level = new TestLevel(player, new Score());

        // Attempt to Move Player Through Wall
        boolean moved = level.movePlayer(Direction.DOWN);

        // Verify Player Did Not Move
        assertFalse(moved);

        // Verify Player Row Did Not Change
        assertEquals(0, player.getRow());

        // Verify Player Column Did Not Change
        assertEquals(0, player.getCol());
    }

    // Test Complete Turn Processing
    @Test
    void turnProcessesPlayerMovement() {

        // Create Player
        Player player = new Player(0, 0, "player.png");

        // Create Test Level
        TestLevel level = new TestLevel(player, new Score());

        // Process Player Turn
        boolean turnProcessed =
                level.takeTurn(Direction.RIGHT);

        // Verify Turn Was Processed
        assertTrue(turnProcessed);

        // Verify Player Row
        assertEquals(0, player.getRow());

        // Verify Player Column
        assertEquals(1, player.getCol());
    }

    // Test Level Completion When Player Reaches Exit
    @Test
    void levelIsCompleteWhenPlayerReachesExit() {

        // Create Player
        Player player = new Player(0, 0, "player.png");

        // Create Test Level
        TestLevel level = new TestLevel(player, new Score());

        // Move Player Onto Exit
        player.setPosition(0, 4);

        // Verify Level is Complete
        assertTrue(level.isLevelComplete());
    }

    // Test Level is Not Complete Before Reaching Exit
    @Test
    void levelIsNotCompleteBeforeReachingExit() {

        // Create Player
        Player player = new Player(0, 0, "player.png");

        // Create Test Level
        TestLevel level = new TestLevel(player, new Score());

        // Verify Level is Not Complete
        assertFalse(level.isLevelComplete());
    }

    // Test Maximum Level Score Calculation
    @Test
    void maxScoreIncludesAllFood() {

        // Create Player
        Player player = new Player(0, 0, "player.png");

        // Create Test Level
        TestLevel level = new TestLevel(player, new Score());

        // Verify Maximum Score Includes Both Food Objects
        assertEquals(20, level.getMaxScore());
    }

    // Test Enemies Do Not Move When Player Cannot Move
    @Test
    void enemiesDoNotMoveWhenPlayerCannotMove() {

        // Create Player
        Player player = new Player(0, 0, "player.png");

        // Create Test Level
        TestLevel level = new TestLevel(player, new Score());

        // Attempt to Move Player Through Wall
        boolean turnProcessed =
                level.takeTurn(Direction.DOWN);

        // Verify Turn Was Not Processed
        assertFalse(turnProcessed);

        // Verify Player Row Did Not Change
        assertEquals(0, player.getRow());

        // Verify Player Column Did Not Change
        assertEquals(0, player.getCol());
    }
}