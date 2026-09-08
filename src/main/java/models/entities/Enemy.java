package models.entities;

// Enemy Class Represents an Enemy Entity in the Game
public class Enemy extends Entity {

    // Enemy Damage Value
    private final int damage = 20;

    // Enemy Constructor
    public Enemy(int row, int col, String imagePath) {
        super(row, col, imagePath);
    }

    // Get the Enemy's Damage Value
    public int getDamage() {
        return damage;
    }
}