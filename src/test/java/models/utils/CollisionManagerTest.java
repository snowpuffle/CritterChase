package models.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.game.GameBoard;
import models.objects.Exit;
import models.objects.Food;
import models.objects.Score;
import models.objects.Wall;

// Test CollisionManager Core Game Rules
// - Blocks Player Movement Through Walls
// - Collects Food and Adds Points
// - Prevents Food from Being Collected More Than Once
// - Allows Player Movement Onto the Exit
// - Allows Player Movement Onto Empty Spaces
class CollisionManagerTest {

    // Test Player Cannot Walk Through a Wall
    @Test
    void playerCannotWalkThroughWall() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create Score
        Score score = new Score();

        // Create Wall
        Wall wall = new Wall(
                2,
                2,
                "wall.png");

        // Place Wall on Game Board
        board.setGameObjectAt(
                2,
                2,
                wall);

        // Create Collision Manager
        CollisionManager collisionManager =
                new CollisionManager(
                        board,
                        score);

        // Attempt to Move Player Onto Wall
        boolean canMove =
                collisionManager.canPlayerMoveTo(
                        2,
                        2);

        // Verify Player Cannot Move Through Wall
        assertFalse(canMove);

        // Verify Score Does Not Increase
        assertEquals(
                0,
                score.getPoints());
    }

    // Test Food is Collected
    @Test
    void collectingFoodAddsPointsAndRemovesFood() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create Score
        Score score = new Score();

        // Create Food
        Food food = new Food(
                2,
                2,
                "food.png");

        // Place Food on Game Board
        board.setGameObjectAt(
                2,
                2,
                food);

        // Create Collision Manager
        CollisionManager collisionManager =
                new CollisionManager(
                        board,
                        score);

        // Move Player Onto Food
        boolean canMove =
                collisionManager.canPlayerMoveTo(
                        2,
                        2);

        // Verify Player Can Move Onto Food
        assertTrue(canMove);

        // Verify Food Points are Added
        assertEquals(
                10,
                score.getPoints());

        // Verify Food is Removed From Game Board
        assertNull(
                board.getGameObjectAt(
                        2,
                        2));
    }

    // Test Score Increases Exactly Once When Food is Collected
    @Test
    void scoreIncreasesExactlyOnceWhenFoodIsCollected() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create Score
        Score score = new Score();

        // Create Food
        Food food = new Food(
                2,
                2,
                "food.png");

        // Place Food on Game Board
        board.setGameObjectAt(
                2,
                2,
                food);

        // Create Collision Manager
        CollisionManager collisionManager =
                new CollisionManager(
                        board,
                        score);

        // Collect Food for the First Time
        assertTrue(
                collisionManager.canPlayerMoveTo(
                        2,
                        2));

        // Verify Food Points are Added
        assertEquals(
                10,
                score.getPoints());

        // Verify Food Was Removed
        assertNull(
                board.getGameObjectAt(
                        2,
                        2));

        // Move Onto Same Position Again
        assertTrue(
                collisionManager.canPlayerMoveTo(
                        2,
                        2));

        // Verify Score Does Not Increase a Second Time
        assertEquals(
                10,
                score.getPoints());
    }

    // Test Player Can Move Onto Exit
    @Test
    void playerCanMoveOntoExit() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create Score
        Score score = new Score();

        // Create Exit
        Exit exit = new Exit(
                2,
                2,
                "exit.png");

        // Place Exit on Game Board
        board.setGameObjectAt(
                2,
                2,
                exit);

        // Create Collision Manager
        CollisionManager collisionManager =
                new CollisionManager(
                        board,
                        score);

        // Attempt to Move Player Onto Exit
        boolean canMove =
                collisionManager.canPlayerMoveTo(
                        2,
                        2);

        // Verify Player Can Move Onto Exit
        assertTrue(canMove);

        // Verify Exit Does Not Add Points
        assertEquals(
                0,
                score.getPoints());
    }

    // Test Player Can Move Onto Empty Space
    @Test
    void playerCanMoveOntoEmptySpace() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create Score
        Score score = new Score();

        // Create Collision Manager
        CollisionManager collisionManager =
                new CollisionManager(
                        board,
                        score);

        // Attempt to Move Player Onto Empty Space
        boolean canMove =
                collisionManager.canPlayerMoveTo(
                        2,
                        2);

        // Verify Player Can Move
        assertTrue(canMove);

        // Verify Score Does Not Increase
        assertEquals(
                0,
                score.getPoints());
    }
}