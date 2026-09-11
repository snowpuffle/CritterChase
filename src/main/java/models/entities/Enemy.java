package models.entities;

import models.objects.Health;

// Enemy Class Represents an Enemy Entity in the Game
public class Enemy extends Entity {

    // Enemy Damage Value
    private final int damage = 20;

    // Enemy Health
    private final Health health;

    // Enemy Constructor
    public Enemy(int row, int col, String imagePath, int maxHealth) {
        super(row, col, imagePath);
        this.health = new Health(maxHealth);
    }

    // Get the Enemy's Damage Value
    public int getDamage() {
        return damage;
    }

    // Reduce Enemy Health
    public void takeDamage(int damage) {
        health.takeDamage(damage);
    }

    // Get the Enemy's Health
    public Health getHealth() {
        return health;
    }
}