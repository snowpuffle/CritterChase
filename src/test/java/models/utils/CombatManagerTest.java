package models.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.entities.Enemy;
import models.entities.Player;
import models.game.GameBoard;
import models.objects.Weapon;

// CombatManagerTest Tests Player Combat and Weapon Attacks
public class CombatManagerTest {

    private GameBoard gameBoard;
    private Player player;
    private EnemyManager enemyManager;
    private CombatManager combatManager;
    private Enemy enemy;
    private Weapon weapon;

    @BeforeEach
    void setUp() {

        // Create GameBoard
        gameBoard = new GameBoard(5, 5);

        // Create Player
        player = new Player(2, 1, "player.png");

        // Create Enemy
        enemy = new Enemy(2, 2, "enemy.png", 40);

        // Create Weapon
        weapon = new Weapon(0, 0, "weapon.png");

        // Create EnemyManager
        enemyManager = new EnemyManager(player, gameBoard);

        // Add Enemy to EnemyManager
        enemyManager.addEnemy(enemy);

        // Create CombatManager
        combatManager = new CombatManager(player, enemyManager);
    }

    // Test Attack Fails Without Weapon
    @Test
    void attackFailsWithoutWeapon() {

        // Attempt to Attack Without a Weapon
        boolean attacked = combatManager.attack(2, 2);

        // Attack Should Fail
        assertFalse(attacked);

        // Enemy Should Remain at Full Health
        assertEquals(40, enemy.getHealth().getCurrentHealth());
    }

    // Test Attack Fails When No Enemy Exists
    @Test
    void attackFailsWhenNoEnemyExists() {

        // Equip Weapon
        player.equipWeapon(weapon);

        // Attempt to Attack Empty Position
        boolean attacked = combatManager.attack(2, 3);

        // Attack Should Fail
        assertFalse(attacked);

        // Enemy Should Remain at Full Health
        assertEquals(40, enemy.getHealth().getCurrentHealth());
    }

    // Test Player Damages Enemy with Weapon
    @Test
    void playerDamagesEnemyWithWeapon() {

        // Equip Weapon
        player.equipWeapon(weapon);

        // Attack Enemy
        boolean attacked = combatManager.attack(2, 2);

        // Attack Should Succeed
        assertTrue(attacked);

        // Enemy Should Lose 20 Health
        assertEquals(20, enemy.getHealth().getCurrentHealth());

        // Enemy Should Still Be Alive
        assertTrue(enemy.getHealth().isAlive());

        // Enemy Should Remain in EnemyManager
        assertTrue(enemyManager.getEnemies().contains(enemy));
    }

    // Test Player Defeats Enemy with Two Attacks
    @Test
    void playerDefeatsEnemyWithTwoAttacks() {

        // Equip Weapon
        player.equipWeapon(weapon);

        // First Attack
        assertTrue(combatManager.attack(2, 2));

        // Enemy Should Still Be Alive
        assertTrue(enemy.getHealth().isAlive());

        // Second Attack
        assertTrue(combatManager.attack(2, 2));

        // Enemy Should Be Defeated
        assertFalse(enemy.getHealth().isAlive());

        // Defeated Enemy Should Be Removed
        assertFalse(enemyManager.getEnemies().contains(enemy));

        // Enemy Should No Longer Exist at Its Position
        assertNull(enemyManager.getEnemyAt(2, 2));
    }
}