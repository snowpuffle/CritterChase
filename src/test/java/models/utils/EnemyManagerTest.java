package models.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import models.entities.Enemy;
import models.entities.Player;
import models.game.GameBoard;
import models.objects.Health;
import models.objects.Wall;

// Test EnemyManager Game Rules
class EnemyManagerTest {

        // Test Enemy Moves Toward Player
        @Test
        void enemyMovesTowardPlayer() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(2, 2, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Enemy
                Enemy enemy = new Enemy(2, 4, "enemy.png");

                // Add Enemy to Enemy Manager
                enemyManager.addEnemy(enemy);

                // Move Enemy Toward Player
                enemyManager.moveEnemies();

                // Verify Enemy Moved One Position Toward Player
                assertEquals(2, enemy.getRow());
                assertEquals(3, enemy.getCol());
        }

        // Test Enemy Does Not Move Through Walls
        @Test
        void enemyDoesNotMoveThroughWalls() {
                GameBoard board = new GameBoard(5, 5);
                Player player = new Player(2, 1, "player.png");
                Health health = new Health(100);

                // Create Wall Between Enemy and Player
                board.setGameObjectAt(new Wall(2, 2, "wall.png"));

                // Create Enemy
                EnemyManager enemyManager = new EnemyManager(player, board, health);
                Enemy enemy = new Enemy(2, 3, "enemy.png");
                enemyManager.addEnemy(enemy);

                // Move Enemy Toward Player
                enemyManager.moveEnemies();

                // Verify Enemy Did Not Move Onto the Wall
                assertFalse(enemy.getRow() == 2 && enemy.getCol() == 2);
        }

        // Test Enemies Do Not Occupy the Same Square
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

                // Add Enemies to Enemy Manager
                enemyManager.addEnemy(enemyOne);
                enemyManager.addEnemy(enemyTwo);

                // Move Enemies Toward Player
                enemyManager.moveEnemies();

                // Create Position Identifier for First Enemy
                int enemyOnePosition = enemyOne.getRow() * board.getWidth() + enemyOne.getCol();

                // Create Position Identifier for Second Enemy
                int enemyTwoPosition = enemyTwo.getRow() * board.getWidth() + enemyTwo.getCol();

                // Verify Enemies Have Different Positions
                assertNotEquals(enemyOnePosition, enemyTwoPosition);
        }

        // Test Adjacent Enemy Damages Player
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

                // Create Adjacent Enemy
                Enemy enemy = new Enemy(2, 3, "enemy.png");

                // Add Enemy to Enemy Manager
                enemyManager.addEnemy(enemy);

                // Move Enemies
                enemyManager.moveEnemies();

                // Verify Player Takes Enemy Damage
                assertEquals(80, health.getCurrentHealth());

                // Verify Enemy Attacks Without Moving
                assertEquals(2, enemy.getRow());
                assertEquals(3, enemy.getCol());
        }

        // Test Multiple Enemies Behave Correctly
        @Test
        void multipleEnemiesMoveCorrectly() {

                // Create Game Board
                GameBoard board = new GameBoard(5, 5);

                // Create Player
                Player player = new Player(2, 2, "player.png");

                // Create Player Health
                Health health = new Health(100);

                // Create Enemy Manager
                EnemyManager enemyManager = new EnemyManager(player, board, health);

                // Create Two Enemies
                Enemy enemyOne = new Enemy(0, 2, "enemy1.png");
                Enemy enemyTwo = new Enemy(4, 2, "enemy2.png");

                // Add Enemies to Enemy Manager
                enemyManager.addEnemy(enemyOne);
                enemyManager.addEnemy(enemyTwo);

                // Move Enemies Toward Player
                enemyManager.moveEnemies();

                // Verify First Enemy Moves Toward Player
                assertEquals(1, enemyOne.getRow());
                assertEquals(2, enemyOne.getCol());

                // Verify Second Enemy Moves Toward Player
                assertEquals(3, enemyTwo.getRow());
                assertEquals(2, enemyTwo.getCol());

                // Verify Enemies Do Not Occupy the Same Position
                assertNotEquals(
                                enemyOne.getRow() + "," + enemyOne.getCol(),
                                enemyTwo.getRow() + "," + enemyTwo.getCol());
        }
}