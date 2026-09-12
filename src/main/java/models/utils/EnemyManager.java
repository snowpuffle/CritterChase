package models.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.entities.Enemy;
import models.entities.Player;
import models.game.GameBoard;

// EnemyManager Owns Enemy Movement and Behavior
public class EnemyManager {

    // Objects Needed for Enemy Behavior
    private final Player player;
    private final GameBoard gameBoard;

    // Level Enemies
    private final List<Enemy> enemies;
    private final Enemy[][] enemyPositions;

    // BFS Path Finder
    private final BFSPathFinder pathFinder;

    // EnemyManager Constructor
    public EnemyManager(Player player, GameBoard gameBoard) {
        this.player = player;
        this.gameBoard = gameBoard;
        this.enemies = new ArrayList<>();
        this.enemyPositions = new Enemy[gameBoard.getHeight()][gameBoard.getWidth()];
        this.pathFinder = new BFSPathFinder(gameBoard);
    }

    /* Enemy Lifecycle */
    // Add an Enemy to the Level
    public void addEnemy(Enemy enemy) {

        // Ignore Null Enemies
        if (enemy == null) {
            return;
        }

        // Get Enemy Row
        int row = enemy.getRow();

        // Get Enemy Column
        int col = enemy.getCol();

        // Reject Positions Outside GameBoard
        if (!gameBoard.isValidPosition(row, col)) {
            throw new IllegalArgumentException("Enemy Position Exists Outside GameBoard.");
        }

        // Reject Positions Occupied by Another Enemy
        if (getEnemyAt(row, col) != null) {
            throw new IllegalArgumentException("Another Enemy Already Occupies This Position.");
        }

        // Add Enemy to Enemy List
        enemies.add(enemy);

        // Store Enemy at Its Current Position
        enemyPositions[row][col] = enemy;
    }

    // Remove an Enemy from the Level
    public void removeEnemy(Enemy enemy) {

        // Ignore Null Enemies
        if (enemy == null) {
            return;
        }

        // Get Enemy Row
        int row = enemy.getRow();

        // Get Enemy Column
        int col = enemy.getCol();

        // Remove Enemy from Current Position
        if (gameBoard.isValidPosition(row, col) && enemyPositions[row][col] == enemy) {
            enemyPositions[row][col] = null;
        }

        // Remove Enemy from Enemy List
        enemies.remove(enemy);
    }

    /* Enemy Behavior */
    // Move All Enemies
    public void moveEnemies() {

        // Move Each Enemy Toward the Player
        for (Enemy enemy : enemies) {

            // Create BFS Distance Map for Current Enemy
            int[][] distanceMap = pathFinder.createDistanceMap(player.getRow(), player.getCol(), this, enemy);

            // Move Current Enemy
            moveEnemy(enemy, distanceMap);
        }
    }

    // Move One Enemy Toward the Player Using the Distance Map
    private void moveEnemy(Enemy enemy, int[][] distanceMap) {

        // Attack the Player if Adjacent
        if (isAdjacentToPlayer(enemy)) {
            damagePlayer(enemy);
            return;
        }

        // Find the Best Neighboring Position
        int[] nextPosition = findNextPosition(enemy, distanceMap);

        // Stop When No Valid Position Is Found
        if (nextPosition == null) {
            return;
        }

        // Get New Enemy Row
        int newRow = nextPosition[0];

        // Get New Enemy Column
        int newCol = nextPosition[1];

        // Move Enemy Through Centralized Movement Method
        moveEnemyTo(enemy, newRow, newCol);
    }

    // Move Enemy to New Position
    private void moveEnemyTo(Enemy enemy, int newRow, int newCol) {

        // Get Current Enemy Row
        int oldRow = enemy.getRow();

        // Get Current Enemy Column
        int oldCol = enemy.getCol();

        // Remove Enemy from Old Position
        enemyPositions[oldRow][oldCol] = null;

        // Update Enemy's Position
        enemy.setPosition(newRow, newCol);

        // Store Enemy at New Position
        enemyPositions[newRow][newCol] = enemy;
    }

    // Find the Best Next Position for an Enemy
    private int[] findNextPosition(Enemy enemy, int[][] distanceMap) {

        // Get Current Enemy Row
        int currentRow = enemy.getRow();

        // Get Current Enemy Column
        int currentCol = enemy.getCol();

        // Get Current Distance from Player
        int currentDistance = distanceMap[currentRow][currentCol];

        // Stop When Enemy Cannot Reach the Player
        if (currentDistance == -1) {
            return null;
        }

        // Store Current Position as Best Position
        int bestRow = currentRow;
        int bestCol = currentCol;
        int bestDistance = currentDistance;

        // Check Each Possible Direction
        for (Direction direction : Direction.values()) {

            // Calculate New Enemy Row
            int newRow = currentRow + direction.getRowChange();

            // Calculate New Enemy Column
            int newCol = currentCol + direction.getColChange();

            // Skip Positions Outside GameBoard
            if (!gameBoard.isValidPosition(newRow, newCol)) {
                continue;
            }

            // Get Distance from New Position to Player
            int newDistance = distanceMap[newRow][newCol];

            // Skip Positions the Enemy Cannot Reach
            if (newDistance == -1) {
                continue;
            }

            // Skip Positions Occupied by Another Enemy
            if (getEnemyAt(newRow, newCol) != null) {
                continue;
            }

            // Choose Position Closer to the Player
            if (newDistance < bestDistance) {
                bestDistance = newDistance;
                bestRow = newRow;
                bestCol = newCol;
            }
        }

        // Stop When No Better Position Is Found
        if (bestRow == currentRow && bestCol == currentCol) {
            return null;
        }

        // Return the Best Next Position
        return new int[] { bestRow, bestCol };
    }

    // Damage the Player
    private void damagePlayer(Enemy enemy) {
        player.getHealth().takeDamage(enemy.getDamage());
    }

    /* Collision / Lookup */

    // Handle a Player Collision with an Enemy
    public boolean handlePlayerCollision(int row, int col) {

        // Find the Enemy at the Position
        Enemy enemy = getEnemyAt(row, col);

        // No Enemy at the Position
        if (enemy == null) {
            return false;
        }

        // Damage the Player When Moving Into an Enemy
        player.getHealth().takeDamage(enemy.getDamage());

        // Stop Player Movement After Collision
        return true;
    }

    // Check if an Enemy is Next to the Player
    private boolean isAdjacentToPlayer(Enemy enemy) {

        // Get Row Difference
        int rowDifference = Math.abs(enemy.getRow() - player.getRow());

        // Get Column Difference
        int colDifference = Math.abs(enemy.getCol() - player.getCol());

        // Return True if the Enemy Is Adjacent to the Player
        return rowDifference + colDifference == 1;
    }

    // Check if Player Can Attack an Enemy
    public boolean canPlayerAttack(int row, int col) {

        // Find the Enemy at the Position
        Enemy enemy = getEnemyAt(row, col);

        // Return True When Player Has a Weapon and Enemy Exists
        return enemy != null && player.hasWeapon();
    }

    // Find an Enemy at a Position
    public Enemy getEnemyAt(int row, int col) {

        // Check if the Position Is Valid
        if (!gameBoard.isValidPosition(row, col)) {
            return null;
        }

        // Return the Enemy at the Position
        return enemyPositions[row][col];
    }

    // Get All Enemies
    public List<Enemy> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }
}