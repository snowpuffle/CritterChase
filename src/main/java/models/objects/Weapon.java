package models.objects;

import models.game.GameObjectType;

// Weapon Class Represents a Weapon Object in the Game
public class Weapon extends GameObject {

    // Maximum Number of Times Weapon Can Be Used
    private static final int MAX_USES = 5;

    // Weapon Attributes
    private final String name;
    private final int damage;
    private int usesRemaining;

    // Weapon Constructor
    public Weapon(int row, int col, String imagePath) {
        super(row, col, imagePath);
        this.name = "Sword";
        this.damage = 20;
        this.usesRemaining = MAX_USES;
    }

    // Use Weapon One Time
    public boolean use() {
        if (usesRemaining <= 0) {
            return false;
        }

        usesRemaining--;
        return true;
    }

    // Check if Weapon Has No Uses Remaining
    public boolean isBroken() {
        return usesRemaining == 0;
    }

    // Get Weapon Damage
    public int getDamage() {
        return damage;
    }

    // Get Weapon Name
    public String getName() {
        return name;
    }

    // Get Weapon Type
    @Override
    public GameObjectType getType() {
        return GameObjectType.WEAPON;
    }
}