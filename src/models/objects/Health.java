package models.objects;

// Health Class Represents the Player's Health in the Game
public class Health {

    private final int maxHealth;
    private int currentHealth;

    // Health Constructor
    public Health(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    // Get the Current Health Value
    public int getCurrentHealth() {
        return currentHealth;
    }

    // Get the Maximum Health Value
    public int getMaxHealth() {
        return maxHealth;
    }

    // Decrease the Current Health by a Specified Amount
    public void takeDamage(int damage) {

        // Ensure that the Current Health does not go below Zero
        this.currentHealth = Math.max(0, currentHealth - damage);
    }

    // Check if the Player is Still Alive
    public boolean isAlive() {
        return currentHealth > 0;
    }
}
