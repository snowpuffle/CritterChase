package models.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.entities.Enemy;
import models.entities.Player;
import models.game.GameBoard;
import models.objects.Health;
import models.objects.Wall;

// Test EnemyManager Core Game Rules
// - Moves Enemies Toward the Player
// - Prevents Enemies from Moving Through Walls
// - Prevents Enemies from Moving Through Other Enemies
// - Prevents Enemies from Occupying the Same Position
// - Damages the Player When an Enemy Is Adjacent
// - Damages the Player During Player-Enemy Collision
// - Supports Multiple Enemies Moving Independently
// - Keeps Enemy Positions Synchronized
// - Rejects Invalid Enemy Positions
// - Removes Enemies from Their Positions
class EnemyManagerTest {

        // Test Enemy Movement Toward the Player
        @Test
        void enemyMovesTowardPlayer() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player at Center of Board
                Player player = new Player(2, 2, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy Two Spaces Away from Player
                Enemy enemy = new Enemy(2, 4, "enemy.png");

                // Add Enemy to Enemy Manager
                enemyManager.addEnemy(enemy);

                // Move Enemy Toward Player
                enemyManager.moveEnemies();

                // Calculate Enemy Distance from Player
                int distanceFromPlayer = Math.abs(enemy.getRow() - player.getRow())
                                + Math.abs(enemy.getCol() - player.getCol());

                // Verify Enemy Moved One Space Closer to Player
                assertEquals(1, distanceFromPlayer);

                // Verify Enemy Position Was Updated
                assertEquals(enemy, enemyManager.getEnemyAt(enemy.getRow(), enemy.getCol()));
        }

        // Test Enemy Movement Around Walls
        @Test
        void enemyMovesAroundWalls() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(2, 1, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Place Wall Between Enemy and Player
                board.setGameObjectAt(2, 2, new Wall(2, 2, "wall.png"));

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy on Other Side of Wall
                Enemy enemy = new Enemy(2, 3, "enemy.png");

                // Add Enemy to Enemy Manager
                enemyManager.addEnemy(enemy);

                // Move Enemy Toward Player
                enemyManager.moveEnemies();

                // Verify Enemy Did Not Move onto Wall
                assertFalse(board.getGameObjectAt(enemy.getRow(), enemy.getCol()) instanceof Wall);

                // Calculate Enemy Distance from Player
                int distanceFromPlayer = Math.abs(enemy.getRow() - player.getRow())
                                + Math.abs(enemy.getCol() - player.getCol());

                // Verify Enemy Moved Closer to Player
                assertEquals(3, distanceFromPlayer);

                // Verify Enemy Position Was Updated
                assertEquals(enemy, enemyManager.getEnemyAt(enemy.getRow(), enemy.getCol()));
        }

        // Test Enemy Movement Around Another Enemy
        @Test
        void enemyDoesNotMoveThroughAnotherEnemy() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(0, 0, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create First Enemy Between Second Enemy and Player
                Enemy enemyOne = new Enemy(0, 1, "enemy1.png");

                // Create Second Enemy Behind First Enemy
                Enemy enemyTwo = new Enemy(0, 2, "enemy2.png");

                // Add Both Enemies to Enemy Manager
                enemyManager.addEnemy(enemyOne);
                enemyManager.addEnemy(enemyTwo);

                // Move Both Enemies Toward Player
                enemyManager.moveEnemies();

                // Verify First Enemy Position Is Synchronized
                assertEquals(enemyOne, enemyManager.getEnemyAt(enemyOne.getRow(), enemyOne.getCol()));

                // Verify Second Enemy Did Not Move Through First Enemy
                assertFalse(enemyTwo.getRow() == enemyOne.getRow()
                                && enemyTwo.getCol() == enemyOne.getCol());

                // Verify Second Enemy Position Is Synchronized
                assertEquals(enemyTwo, enemyManager.getEnemyAt(enemyTwo.getRow(), enemyTwo.getCol()));
        }

        // Test Multiple Enemies Do Not Occupy Same Square
        @Test
        void enemiesDoNotOccupySameSquare() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(0, 0, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Two Enemies
                Enemy enemyOne = new Enemy(0, 2, "enemy1.png");
                Enemy enemyTwo = new Enemy(1, 1, "enemy2.png");

                // Add Both Enemies to Enemy Manager
                enemyManager.addEnemy(enemyOne);
                enemyManager.addEnemy(enemyTwo);

                // Move Both Enemies Toward Player
                enemyManager.moveEnemies();

                // Verify Enemies Do Not Occupy Same Position
                assertFalse(enemyOne.getRow() == enemyTwo.getRow()
                                && enemyOne.getCol() == enemyTwo.getCol());
        }

        // Test Adjacent Enemy Damage
        @Test
        void adjacentEnemyDamagesPlayer() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(2, 2, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy Directly Next to Player
                Enemy enemy = new Enemy(2, 3, "enemy.png");

                // Add Enemy to Enemy Manager
                enemyManager.addEnemy(enemy);

                // Move Enemies
                // Adjacent Enemy Should Attack Instead of Moving
                enemyManager.moveEnemies();

                // Verify Player Lost Enemy Damage
                assertEquals(80, health.getCurrentHealth());

                // Verify Enemy Remains in Original Position
                assertEquals(2, enemy.getRow());
                assertEquals(3, enemy.getCol());

                // Verify Enemy Position Was Not Changed
                assertEquals(enemy, enemyManager.getEnemyAt(2, 3));
        }

        // Test Player-Enemy Collision Damage
        @Test
        void playerCollisionWithEnemyCausesDamage() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(2, 2, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy
                Enemy enemy = new Enemy(2, 3, "enemy.png");

                // Add Enemy to Enemy Manager
                enemyManager.addEnemy(enemy);

                // Trigger Player-Enemy Collision
                boolean collision = enemyManager.handlePlayerCollision(2, 3);

                // Verify Collision Was Detected
                assertTrue(collision);

                // Verify Player Lost Enemy Damage
                assertEquals(80, health.getCurrentHealth());
        }

        // Test Multiple Enemy Movement
        @Test
        void multipleEnemiesMoveCorrectly() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player at Center of Board
                Player player = new Player(2, 2, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy Above Player
                Enemy enemyOne = new Enemy(0, 2, "enemy1.png");

                // Create Enemy Below Player
                Enemy enemyTwo = new Enemy(4, 2, "enemy2.png");

                // Add Both Enemies to Enemy Manager
                enemyManager.addEnemy(enemyOne);
                enemyManager.addEnemy(enemyTwo);

                // Store Original Enemy Positions
                int enemyOneStartRow = enemyOne.getRow();
                int enemyOneStartCol = enemyOne.getCol();
                int enemyTwoStartRow = enemyTwo.getRow();
                int enemyTwoStartCol = enemyTwo.getCol();

                // Move Both Enemies Toward Player
                enemyManager.moveEnemies();

                // Calculate First Enemy Distance from Player
                int enemyOneDistance = Math.abs(enemyOne.getRow() - player.getRow())
                                + Math.abs(enemyOne.getCol() - player.getCol());

                // Calculate Second Enemy Distance from Player
                int enemyTwoDistance = Math.abs(enemyTwo.getRow() - player.getRow())
                                + Math.abs(enemyTwo.getCol() - player.getCol());

                // Calculate Original First Enemy Distance
                int enemyOneStartDistance = Math.abs(enemyOneStartRow - player.getRow())
                                + Math.abs(enemyOneStartCol - player.getCol());

                // Calculate Original Second Enemy Distance
                int enemyTwoStartDistance = Math.abs(enemyTwoStartRow - player.getRow())
                                + Math.abs(enemyTwoStartCol - player.getCol());

                // Verify First Enemy Moved Closer to Player
                assertTrue(enemyOneDistance < enemyOneStartDistance);

                // Verify Second Enemy Moved Closer to Player
                assertTrue(enemyTwoDistance < enemyTwoStartDistance);

                // Verify Enemies Do Not Occupy Same Position
                assertFalse(enemyOne.getRow() == enemyTwo.getRow()
                                && enemyOne.getCol() == enemyTwo.getCol());

                // Verify First Enemy Position Was Updated
                assertEquals(
                                enemyOne,
                                enemyManager.getEnemyAt(enemyOne.getRow(), enemyOne.getCol()));

                // Verify Second Enemy Position Was Updated
                assertEquals(
                                enemyTwo,
                                enemyManager.getEnemyAt(enemyTwo.getRow(), enemyTwo.getCol()));
        }

        // Test Duplicate Enemy Position Is Rejected
        @Test
        void duplicateEnemyPositionIsRejected() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(0, 0, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create First Enemy
                Enemy enemyOne = new Enemy(2, 2, "enemy1.png");

                // Create Second Enemy at Same Position
                Enemy enemyTwo = new Enemy(2, 2, "enemy2.png");

                // Add First Enemy to Enemy Manager
                enemyManager.addEnemy(enemyOne);

                // Verify Duplicate Position Is Rejected
                assertThrows(IllegalArgumentException.class, () -> enemyManager.addEnemy(enemyTwo));

                // Verify Original Enemy Remains at Position
                assertEquals(enemyOne, enemyManager.getEnemyAt(2, 2));
        }

        // Test Invalid Enemy Position Is Rejected
        @Test
        void invalidEnemyPositionIsRejected() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(0, 0, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy Outside GameBoard
                Enemy enemy = new Enemy(5, 5, "enemy.png");

                // Verify Invalid Position Is Rejected
                assertThrows(IllegalArgumentException.class, () -> enemyManager.addEnemy(enemy));
        }

        // Test Enemy Removal Clears Its Position
        @Test
        void removingEnemyClearsPosition() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(0, 0, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy
                Enemy enemy = new Enemy(2, 2, "enemy.png");

                // Add Enemy to Enemy Manager
                enemyManager.addEnemy(enemy);

                // Remove Enemy from Enemy Manager
                enemyManager.removeEnemy(enemy);

                // Verify Enemy Was Removed from Position
                assertEquals(null, enemyManager.getEnemyAt(2, 2));

                // Verify Enemy Was Removed from Enemy List
                assertFalse(enemyManager.getEnemies().contains(enemy));
        }
}