package models.objects;

// Health Class Represents Health in Game
public class Health {

    private final int maxHealth;
    private int currentHealth;

    // Health Constructor
    public Health(int maxHealth) {

        // Validate Maximum Health Value
        if (maxHealth <= 0) {
            throw new IllegalArgumentException(
                    "Maximum Health Must Be Greater Than Zero."
            );
        }

        // Set Maximum Health Value
        this.maxHealth = maxHealth;

        // Set Current Health to Maximum Health
        this.currentHealth = maxHealth;
    }

    // Get Current Health Value
    public int getCurrentHealth() {
        return currentHealth;
    }

    // Get Maximum Health Value
    public int getMaxHealth() {
        return maxHealth;
    }

    // Decrease Current Health by Specified Amount
    public void takeDamage(int damage) {

        // Reject Negative Damage Values
        if (damage < 0) {
            throw new IllegalArgumentException(
                    "Damage Cannot Be Negative."
            );
        }

        // Decrease Current Health Without Going Below Zero
        this.currentHealth = Math.max(0, currentHealth - damage);
    }

    // Check Player Alive Status
    public boolean isAlive() {
        return currentHealth > 0;
    }
}