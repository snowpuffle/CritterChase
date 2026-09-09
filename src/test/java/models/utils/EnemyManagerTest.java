package models.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
// - Prevents Enemies from Occupying the Same Position
// - Damages the Player When an Enemy Is Adjacent
// - Damages the Player During Player-Enemy Collision
// - Supports Multiple Enemies Moving Independently
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

                // Verify Enemy Moved One Space Toward Player
                assertEquals(2, enemy.getRow());
                assertEquals(3, enemy.getCol());
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
                board.setGameObjectAt(new Wall(2, 2, "wall.png"));

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy on the Other Side of the Wall
                Enemy enemy = new Enemy(2, 3, "enemy.png");

                // Add Enemy to Enemy Manager
                enemyManager.addEnemy(enemy);

                // Move Enemy Toward Player
                enemyManager.moveEnemies();

                // Verify Enemy Moved Around the Wall
                assertEquals(1, enemy.getRow());
                assertEquals(3, enemy.getCol());
        }

        // Test Multiple Enemies Do Not Occupy the Same Square
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

                // Verify Enemies Do Not Occupy the Same Position
                assertFalse(enemyOne.getRow() == enemyTwo.getRow() && enemyOne.getCol() == enemyTwo.getCol());
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

                // Verify Enemy Remains in Its Original Position
                assertEquals(2, enemy.getRow());
                assertEquals(3, enemy.getCol());
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

                // Move Both Enemies Toward Player
                enemyManager.moveEnemies();

                // Verify First Enemy Moved One Space Toward Player
                assertEquals(1, enemyOne.getRow());
                assertEquals(2, enemyOne.getCol());

                // Verify Second Enemy Moved One Space Toward Player
                assertEquals(3, enemyTwo.getRow());
                assertEquals(2, enemyTwo.getCol());

                // Verify Enemies Do Not Occupy the Same Position
                assertFalse(enemyOne.getRow() == enemyTwo.getRow() && enemyOne.getCol() == enemyTwo.getCol());
        }
}