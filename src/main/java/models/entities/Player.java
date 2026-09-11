package models.entities;

import models.objects.Health;
import models.objects.Weapon;

// Player Class Represents the Player Object in the Game
public class Player extends Entity {

    private Weapon weapon;
    private int weaponMoves;
    private final Health health;

    // Player Constructor
    public Player(int row, int col, String imagePath) {
        super(row, col, imagePath);
        this.weapon = null;
        this.weaponMoves = 0;
        this.health = new Health(100);
    }

    // Give the Player a Weapon
    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.weaponMoves = 0;
    }

    // Increase Weapon Move Counter
    public void incrementWeaponMoves() {
        if (weapon != null) {
            weaponMoves++;
        }
    }

    // Check if Weapon Has Expired
    public boolean isWeaponExpired() {
        return weapon != null && weaponMoves >= 5;
    }

    // Remove Expired Weapon
    public void removeWeapon() {
        weapon = null;
        weaponMoves = 0;
    }

    // Check if the Player has a Weapon
    public boolean hasWeapon() {
        return weapon != null;
    }

    // Get the Player's Weapon
    public Weapon getWeapon() {
        return weapon;
    }

    // Get the Player's Health
    public Health getHealth() {
        return health;
    }
}