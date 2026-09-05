package models.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.entities.Enemy;
import models.entities.Player;
import models.objects.Health;

// EnemyManager Owns Enemy Movement and Behavior.
public class EnemyManager {

    // Objects Needed for Enemy Behavior
    private final Player player;
    private final Health health;
    private final GameBoard gameBoard;

    // Level Enemies
    private final List<Enemy> enemies;
    private final Enemy[][] enemyPositions;

    // BFS Path Finder
    private final BFSPathFinder pathFinder;

    // EnemyManager Constructor
    public EnemyManager(Player player, GameBoard gameBoard, Health health) {
        this.player = player;
        this.health = health;
        this.gameBoard = gameBoard;
        this.enemies = new ArrayList<>();
        this.enemyPositions = new Enemy[gameBoard.getHeight()][gameBoard.getWidth()];
        this.pathFinder = new BFSPathFinder(gameBoard);
    }

    // Add an Enemy
    public void addEnemy(Enemy enemy) {

        // Ignore Null Enemies
        if (enemy == null) {
            return;
        }

        // Add the Enemy to the List of Enemies
        enemies.add(enemy);

        // Store Enemy at its Current Position
        if (gameBoard.isValidPosition(enemy.getRow(), enemy.getCol())) {
            enemyPositions[enemy.getRow()][enemy.getCol()] = enemy;
        }
    }

    // Remove an Enemy
    public void removeEnemy(Enemy enemy) {

        // Ignore Null Enemies
        if (enemy == null) {
            return;
        }

        // Remove Enemy from its Current Position
        if (gameBoard.isValidPosition(enemy.getRow(), enemy.getCol())) {
            enemyPositions[enemy.getRow()][enemy.getCol()] = null;
        }

        // Remove the Enemy from the List of Enemies
        enemies.remove(enemy);
    }

    // Move All Enemies
    public void moveEnemies() {

        // Create One BFS Distance Map from the Player
        int[][] distanceMap = pathFinder.createDistanceMap(
                player.getRow(),
                player.getCol(),
                this);

        // Move Each Enemy Using the Same Distance Map
        for (Enemy enemy : enemies) {
            moveEnemy(enemy, distanceMap);
        }
    }

    // Move One Enemy Toward the Player Using the Shared Distance Map
    private void moveEnemy(Enemy enemy, int[][] distanceMap) {

        // Attack the Player if Adjacent
        if (isAdjacentToPlayer(enemy)) {
            hitPlayer(enemy);
            return;
        }

        // Find the Best Neighboring Position
        int[] nextPosition = findNextPosition(enemy, distanceMap);

        // No Valid Position Found
        if (nextPosition == null) {
            return;
        }

        // Get Current Position
        int oldRow = enemy.getRow();
        int oldCol = enemy.getCol();

        // Get New Position
        int newRow = nextPosition[0];
        int newCol = nextPosition[1];

        // Remove Enemy from Its Old Position
        enemyPositions[oldRow][oldCol] = null;

        // Move Enemy
        enemy.setPosition(newRow, newCol);

        // Store Enemy at Its New Position
        enemyPositions[newRow][newCol] = enemy;
    }

    // Find the Best Next Position for an Enemy
    private int[] findNextPosition(Enemy enemy, int[][] distanceMap) {

        int currentRow = enemy.getRow();
        int currentCol = enemy.getCol();

        int currentDistance = distanceMap[currentRow][currentCol];

        // If the Enemy Cannot Reach the Player
        if (currentDistance == -1) {
            return null;
        }

        int bestRow = currentRow;
        int bestCol = currentCol;
        int bestDistance = currentDistance;

        // Check Each Possible Direction
        for (Direction direction : Direction.values()) {

            int newRow = currentRow + direction.getRowChange();
            int newCol = currentCol + direction.getColChange();

            // Skip Invalid Positions
            if (!gameBoard.isValidPosition(newRow, newCol)) {
                continue;
            }

            int newDistance = distanceMap[newRow][newCol];

            // Skip Unreachable Positions
            if (newDistance == -1) {
                continue;
            }

            // Skip Positions Occupied by Another Enemy
            Enemy otherEnemy = getEnemyAt(newRow, newCol);

            if (otherEnemy != null && otherEnemy != enemy) {
                continue;
            }

            // Move Toward the Player
            if (newDistance < bestDistance) {
                bestDistance = newDistance;
                bestRow = newRow;
                bestCol = newCol;
            }
        }

        // No Better Position Found
        if (bestRow == currentRow && bestCol == currentCol) {
            return null;
        }

        return new int[] { bestRow, bestCol };
    }

    // Damage the Player
    private void hitPlayer(Enemy enemy) {
        health.takeDamage(enemy.getDamage());
    }

    // Handle a Player Collision with an Enemy
    public boolean handlePlayerCollision(int row, int col) {

        // Find the Enemy at the Position
        Enemy enemy = getEnemyAt(row, col);

        // No Enemy at the Position
        if (enemy == null) {
            return false;
        }

        // Damage the Player
        health.takeDamage(enemy.getDamage());
        return true;
    }

    // Check if an Enemy is Next to the Player
    private boolean isAdjacentToPlayer(Enemy enemy) {

        // Check if the Enemy is Adjacent to the Player
        int rowDifference = Math.abs(enemy.getRow() - player.getRow());

        // Check if the Enemy is Adjacent to the Player
        int colDifference = Math.abs(enemy.getCol() - player.getCol());

        // Return True if the Enemy is Adjacent to the Player
        return rowDifference + colDifference == 1;
    }

    // Find an Enemy at a Position
    public Enemy getEnemyAt(int row, int col) {

        // Check if the Position is Valid
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