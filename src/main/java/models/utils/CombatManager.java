package models.utils;

import models.entities.Enemy;
import models.entities.Player;
import models.objects.Weapon;

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

    // Attack Enemy at Requested Position
    public boolean attack(int row, int col) {

        // Check if Player Has a Weapon
        if (!player.hasWeapon()) {
            return false;
        }

        // Get Enemy at Requested Position
        Enemy enemy = enemyManager.getEnemyAt(row, col);

        // Check if Enemy Exists
        if (enemy == null) {
            return false;
        }

        // Get Equipped Weapon
        Weapon weapon = player.getWeapon();

        // Check if Weapon Can Be Used
        if (!weapon.canBeUsed()) {
            return false;
        }

        // Attack Enemy
        enemy.takeDamage(weapon.getDamage());

        // Consume One Weapon Use
        weapon.consumeUse();

        // Remove Defeated Enemy
        if (enemy.getHealth().getCurrentHealth() <= 0) {
            enemyManager.removeEnemy(enemy);
        }

        // Return Successful Attack
        return true;
    }
}