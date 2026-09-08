package models.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import models.game.GameBoard;
import models.objects.Wall;

// Test BFSPathFinder Game Rules
class BFSPathFinderTest {

        // Test BFS Finds the Shortest Path
        @Test
        void findsShortestPath() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create BFS Path Finder
                BFSPathFinder pathFinder = new BFSPathFinder(board);

                // Create Distance Map Starting at Player Position
                int[][] distances = pathFinder.createDistanceMap(0, 0, null);

                // Verify Shortest Distance From (0,0) to (2,2)
                assertEquals(4, distances[2][2]);
        }

        // Test Walls Block the Path
        @Test
        void wallsBlockPath() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Place Wall Next to Player
                board.setGameObjectAt(
                                new Wall(0, 1, "wall.png"));

                // Create BFS Path Finder
                BFSPathFinder pathFinder = new BFSPathFinder(board);

                // Create Distance Map Starting at Player Position
                int[][] distances = pathFinder.createDistanceMap(0, 0, null);

                // Verify Wall Cannot Be Reached
                assertEquals(-1, distances[0][1]);

                // Verify BFS Finds the Shortest Available Path Around the Wall
                assertEquals(4, distances[0][2]);
        }

        // Test Unreachable Area Returns Negative One
        @Test
        void unreachableAreaReturnsNegativeOne() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Surround Position With Walls
                board.setGameObjectAt(
                                new Wall(1, 2, "wall.png"));

                board.setGameObjectAt(
                                new Wall(3, 2, "wall.png"));

                board.setGameObjectAt(
                                new Wall(2, 1, "wall.png"));

                board.setGameObjectAt(
                                new Wall(2, 3, "wall.png"));

                // Create BFS Path Finder
                BFSPathFinder pathFinder = new BFSPathFinder(board);

                // Create Distance Map Starting at Player Position
                int[][] distances = pathFinder.createDistanceMap(0, 0, null);

                // Verify Surrounded Position is Unreachable
                assertEquals(-1, distances[2][2]);
        }

        // Test Player Position Has a Distance of Zero
        @Test
        void playerPositionHasDistanceZero() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create BFS Path Finder
                BFSPathFinder pathFinder = new BFSPathFinder(board);

                // Create Distance Map Starting at Player Position
                int[][] distances = pathFinder.createDistanceMap(3, 4, null);

                // Verify Player Position Has Distance Zero
                assertEquals(0, distances[3][4]);
        }
}