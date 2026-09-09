package models.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import models.game.GameBoard;
import models.objects.Wall;

// Test BFSPathFinder Core Game Rules
// - Finds the Shortest Path Between Two Positions
// - Blocks Paths Through Walls
// - Returns -1 for Unreachable Positions
// - Calculates a Distance of Zero for the Player's Position
// - Does Not Go Outside the Board
// - Finds a Path Around Walls
class BFSPathFinderTest {

    // Test Shortest Path Calculation
    @Test
    void findsShortestPath() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create BFS Path Finder
        BFSPathFinder pathFinder = new BFSPathFinder(board);

        // Create Distance Map From Player Position
        int[][] distances =
                pathFinder.createDistanceMap(0, 0, null);

        // Verify Shortest Distance to Position (2,2)
        assertEquals(
                4,
                distances[2][2]);
    }

    // Test Walls Block Enemy Path
    @Test
    void wallsBlockPath() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Place Wall Between Player and Destination
        board.setGameObjectAt(
                0,
                1,
                new Wall(
                        0,
                        1,
                        "wall.png"));

        // Create BFS Path Finder
        BFSPathFinder pathFinder = new BFSPathFinder(board);

        // Create Distance Map From Player Position
        int[][] distances =
                pathFinder.createDistanceMap(0, 0, null);

        // Verify Wall Position Is Unreachable
        assertEquals(
                -1,
                distances[0][1]);

        // Verify Path Can Continue Around Wall
        assertEquals(
                4,
                distances[0][2]);
    }

    // Test Unreachable Area
    @Test
    void unreachableAreaReturnsNegativeOne() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Surround Position (2,2) With Walls
        board.setGameObjectAt(
                1,
                2,
                new Wall(
                        1,
                        2,
                        "wall.png"));

        board.setGameObjectAt(
                3,
                2,
                new Wall(
                        3,
                        2,
                        "wall.png"));

        board.setGameObjectAt(
                2,
                1,
                new Wall(
                        2,
                        1,
                        "wall.png"));

        board.setGameObjectAt(
                2,
                3,
                new Wall(
                        2,
                        3,
                        "wall.png"));

        // Create BFS Path Finder
        BFSPathFinder pathFinder = new BFSPathFinder(board);

        // Create Distance Map From Player Position
        int[][] distances =
                pathFinder.createDistanceMap(0, 0, null);

        // Verify Surrounded Position Is Unreachable
        assertEquals(
                -1,
                distances[2][2]);
    }

    // Test Player Starting Position
    @Test
    void playerPositionHasDistanceZero() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create BFS Path Finder
        BFSPathFinder pathFinder = new BFSPathFinder(board);

        // Create Distance Map From Player Position
        int[][] distances =
                pathFinder.createDistanceMap(3, 4, null);

        // Verify Player Position Has Distance Zero
        assertEquals(
                0,
                distances[3][4]);
    }

    // Test Board Boundary Handling
    @Test
    void doesNotGoOutsideBoard() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create BFS Path Finder
        BFSPathFinder pathFinder = new BFSPathFinder(board);

        // Create Distance Map From Top-Left Corner
        int[][] distances =
                pathFinder.createDistanceMap(0, 0, null);

        // Verify Distance Map Has Correct Number of Rows
        assertEquals(
                5,
                distances.length);

        // Verify Distance Map Has Correct Number of Columns
        assertEquals(
                5,
                distances[0].length);

        // Verify Top-Left Position Is Starting Position
        assertEquals(
                0,
                distances[0][0]);

        // Verify Bottom-Right Position Is Reachable
        assertEquals(
                8,
                distances[4][4]);

        // Verify No Extra Rows Exist Outside Board
        assertEquals(
                5,
                distances.length);

        // Verify No Extra Columns Exist Outside Board
        assertEquals(
                5,
                distances[0].length);
    }

    // Test Finding a Path Around a Wall
    @Test
    void findsPathAroundWall() {

        // Create Game Board
        GameBoard board = new GameBoard(5, 5);

        // Create Wall Directly to Right of Player
        board.setGameObjectAt(
                0,
                1,
                new Wall(
                        0,
                        1,
                        "wall.png"));

        // Create BFS Path Finder
        BFSPathFinder pathFinder = new BFSPathFinder(board);

        // Create Distance Map From Player Position
        int[][] distances =
                pathFinder.createDistanceMap(0, 0, null);

        // Verify Wall Position Is Unreachable
        assertEquals(
                -1,
                distances[0][1]);

        // Verify BFS Finds Alternate Path Around Wall
        assertEquals(
                4,
                distances[0][2]);

        // Verify Position Below Player Is Reachable
        assertEquals(
                1,
                distances[1][0]);
    }
}