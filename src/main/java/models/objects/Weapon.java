package models.objects;

import models.game.GameObjectType;

// Weapon Class Represents a Weapon Object in the Game
public class Weapon extends GameObject {

    // Weapon Attributes
    private final String name;
    private final int damage;

    // Weapon Constructor
    public Weapon(int row, int col, String imagePath) {
        super(row, col, imagePath);
        this.name = "Sword";
        this.damage = 20;
    }

    // Get the Weapon's Type
    public GameObjectType getType() {
        return GameObjectType.WEAPON;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }
}