package models.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

// Test Health Core Functionality
class HealthTest {

    // Test Maximum Health Initialization
    @Test
    void healthStartsAtMaximum() {
        // Create Health Instance
        Health health = new Health(100);

        // Verify Current Health Value
        assertEquals(100, health.getCurrentHealth());

        // Verify Maximum Health Value
        assertEquals(100, health.getMaxHealth());

        // Verify Health Status
        assertTrue(health.isAlive());
    }

    // Test Health Damage Functionality
    @Test
    void takeDamageReducesHealth() {
        // Create Health Instance
        Health health = new Health(100);

        // Apply Damage To Health
        health.takeDamage(30);

        // Verify Reduced Health Value
        assertEquals(70, health.getCurrentHealth());
    }

    // Test Minimum Health Limit
    @Test
    void healthCannotGoBelowZero() {
        // Create Health Instance
        Health health = new Health(100);

        // Apply Damage Exceeding Current Health
        health.takeDamage(150);

        // Verify Minimum Health Value
        assertEquals(0, health.getCurrentHealth());

        // Verify Defeated Health Status
        assertFalse(health.isAlive());
    }
}