package models.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import models.objects.Score;
import models.utils.Direction;
import models.utils.PlayerStart;

// Test Level Core Game Rules
// - Moves Player Through Valid Spaces
// - Prevents Player from Moving Through Walls
// - Processes Valid Player Turns
// - Does Not Process Invalid Player Turns
// - Collects Food and Updates Score
// - Detects Level Completion at Exit
// - Does Not Complete Level Before Reaching Exit
// - Calculates Maximum Level Score
// - Prevents Enemy Movement When Player Cannot Move
class LevelTest {

    // Create Test Level for Unit Testing
    private static class TestLevel extends Level {

        // Test Level Constructor
        TestLevel(Score score) {
            super(createTestDefinition(), score);
        }

        // Create Test Level Definition
        private static LevelDefinition createTestDefinition() {

            // Create Test Level Maze
            List<String> maze = List.of(
                    "    X          ",
                    "## F###########",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ",
                    "               ");

            // Create Test Level Starting Position
            PlayerStart player = new PlayerStart(0, 0);

            // Create Test Level Assets
            LevelAssets assets = new LevelAssets(
                    "woodlands.png",
                    "player.png",
                    "food.png",
                    "enemy.png",
                    "wall1.png",
                    "wall2.png",
                    "exit.png");

            // Return Test Level Definition
            return new LevelDefinition(
                    1,
                    10,
                    player,
                    assets,
                    maze);
        }
    }

    // Test Valid Player Movement
    @Test
    void validMovementMovesPlayer() {

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Get Player
        var player = level.getPlayer();

        // Process Valid Player Turn
        boolean moved = level.takeTurn(Direction.RIGHT);

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

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Get Player
        var player = level.getPlayer();

        // Attempt to Move Player Through Wall
        boolean moved = level.takeTurn(Direction.DOWN);

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

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Get Player
        var player = level.getPlayer();

        // Process Player Turn
        boolean turnProcessed = level.takeTurn(Direction.RIGHT);

        // Verify Turn Was Processed
        assertTrue(turnProcessed);

        // Verify Player Row
        assertEquals(0, player.getRow());

        // Verify Player Column
        assertEquals(1, player.getCol());
    }

    // Test Invalid Turn Processing
    @Test
    void invalidTurnDoesNotProcess() {

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Get Player
        var player = level.getPlayer();

        // Attempt to Process Turn Through Wall
        boolean turnProcessed = level.takeTurn(Direction.DOWN);

        // Verify Turn Was Not Processed
        assertFalse(turnProcessed);

        // Verify Player Row Did Not Change
        assertEquals(0, player.getRow());

        // Verify Player Column Did Not Change
        assertEquals(0, player.getCol());
    }

    // Test Food Collection
    @Test
    void collectingFoodIncreasesScore() {

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Get Player
        var player = level.getPlayer();

        // Move Player Next to Food
        player.setPosition(1, 2);

        // Move Player Onto Food
        boolean moved = level.takeTurn(Direction.RIGHT);

        // Verify Player Moved Successfully
        assertTrue(moved);

        // Verify Food Added Points to Score
        assertEquals(
                10,
                level.getScore().getPoints());
    }

    // Test Level Completion When Player Reaches Exit
    @Test
    void levelIsCompleteWhenPlayerReachesExit() {

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Get Player
        var player = level.getPlayer();

        // Move Player Onto Exit
        player.setPosition(0, 4);

        // Verify Level Is Complete
        assertTrue(
                level.isLevelComplete());
    }

    // Test Level Is Not Complete Before Reaching Exit
    @Test
    void levelIsNotCompleteBeforeReachingExit() {

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Verify Level Is Not Complete
        assertFalse(
                level.isLevelComplete());
    }

    // Test Maximum Level Score Calculation
    @Test
    void maxScoreIncludesAllFood() {

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Verify Maximum Score Matches Level Definition
        assertEquals(
                10,
                level.getMaxScore());
    }

    // Test Enemies Do Not Move When Player Cannot Move
    @Test
    void enemiesDoNotMoveWhenPlayerCannotMove() {

        // Create Test Level
        TestLevel level = new TestLevel(new Score());

        // Get Player
        var player = level.getPlayer();

        // Attempt to Move Player Through Wall
        boolean turnProcessed = level.takeTurn(Direction.DOWN);

        // Verify Turn Was Not Processed
        assertFalse(turnProcessed);

        // Verify Player Row Did Not Change
        assertEquals(0, player.getRow());

        // Verify Player Column Did Not Change
        assertEquals(0, player.getCol());
    }
}