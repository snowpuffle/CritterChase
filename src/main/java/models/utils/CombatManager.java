package models.utils;

import models.entities.Enemy;
import models.entities.Player;

// CombatManager Handles Player Combat and Weapon Attacks
public class CombatManager {

    // Objects Needed for Combat
    private final Player player;
    private final EnemyManager enemyManager;

    // CombatManager Constructor
    public CombatManager(Player player, EnemyManager enemyManager) {
        this.player = player;
        this.enemyManager = enemyManager;
    }

    // Attempt to Attack an Enemy at the Target Position
    public boolean attack(int row, int col) {

        // Player Must Have a Weapon to Attack
        if (!player.hasWeapon()) {
            return false;
        }

        // Get Enemy at Target Position
        Enemy enemy = enemyManager.getEnemyAt(row, col);

        // Return False When No Enemy Is at Target Position
        if (enemy == null) {
            return false;
        }

        // Deal Weapon Damage to Enemy
        int damage = player.getWeapon().getDamage();

        // Use One Weapon Charge
        if (!player.useWeapon()) {
            return false;
        }

        enemy.takeDamage(damage);

        // Remove Enemy When Health Reaches Zero
        if (!enemy.getHealth().isAlive()) {
            enemyManager.removeEnemy(enemy);
        }

        // Return True When Attack Is Successful
        return true;
    }
}