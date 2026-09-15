package models.entities;

import models.objects.Health;
import models.objects.Weapon;

// Player Class Represents the Player Object in the Game
public class Player extends Entity {

    // Player's Equipped Weapon
    private Weapon weapon;

    // Player's Health
    private final Health health;

    // Player Constructor
    public Player(int row, int col, String imagePath) {
        super(row, col, imagePath);
        this.weapon = null;
        this.health = new Health(100);
    }

    // Equip a Weapon
    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    // Remove Equipped Weapon
    public void removeWeapon() {
        this.weapon = null;
    }

    // Check if Player Has a Weapon
    public boolean hasWeapon() {
        return weapon != null;
    }

    // Get Player's Weapon
    public Weapon getWeapon() {
        return weapon;
    }

    // Get Player's Health
    public Health getHealth() {
        return health;
    }
}