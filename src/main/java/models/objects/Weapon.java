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
    public Weapon(int row, int col, String imagePath, String name, int damage) {
        super(row, col, imagePath);
        this.name = name;
        this.damage = damage;
        this.usesRemaining = MAX_USES;
    }

    // Check if Weapon Can Be Used
    public boolean canBeUsed() {
        return usesRemaining > 0;
    }

    // Consume One Weapon Use
    public void consumeUse() {
        if (usesRemaining <= 0) {
            return;
        }

        usesRemaining--;
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

    // Get Weapon Uses Remaining
    public int getUsesRemaining() {
        return usesRemaining;
    }

    // Get Weapon Image Path
    public String getImagePath() {
        return super.getImagePath();
    }

    // Get Weapon Type
    @Override
    public GameObjectType getType() {
        return GameObjectType.WEAPON;
    }
}