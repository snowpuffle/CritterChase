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

        // Move Each Enemy Toward the Player
        for (Enemy enemy : enemies) {
            moveEnemy(enemy);
        }
    }

    // Move One Enemy Toward the Player
    private void moveEnemy(Enemy enemy) {

        // Attack the Player if Adjacent
        if (isAdjacentToPlayer(enemy)) {
            hitPlayer(enemy);
            return;
        }

        // Find the Shortest Path to the Player
        List<int[]> path = pathFinder.findPath(enemy.getRow(), enemy.getCol(), player.getRow(), player.getCol(),
                enemies);

        // No Path Found
        if (path.isEmpty()) {
            return;
        }

        // Get the Next Position
        int[] nextPosition = path.get(0);

        // Move the Enemy to the Next Position
        int oldRow = enemy.getRow();
        int oldCol = enemy.getCol();

        // Get the New Position
        int newRow = nextPosition[0];
        int newCol = nextPosition[1];

        // Remove Enemy from Its Old Position
        enemyPositions[oldRow][oldCol] = null;

        // Move the Enemy
        enemy.setPosition(newRow, newCol);

        // Store Enemy at Its New Position
        enemyPositions[newRow][newCol] = enemy;
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