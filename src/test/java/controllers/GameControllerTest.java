package controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import javafx.scene.input.KeyCode;
import models.utils.Direction;

// Test GameController Core Gameplay Rules
// - Converts Keyboard Input to Movement Direction
// - Supports Arrow Keys
// - Rejects Invalid Keyboard Input
class GameControllerTest {

    // Test Keyboard Movement
    @Test
    void convertKeyToDirectionReturnsCorrectDirection() throws Exception {

        // Create GameController Instance
        GameController controller = new GameController();

        // Access Private Key Conversion Method
        Method method = GameController.class
                .getDeclaredMethod("convertKeyToDirection", KeyCode.class);

        // Allow Access To Private Method
        method.setAccessible(true);

        // Verify Movement Keys
        assertEquals(Direction.UP, method.invoke(controller, KeyCode.W));
        assertEquals(Direction.LEFT, method.invoke(controller, KeyCode.A));
        assertEquals(Direction.DOWN, method.invoke(controller, KeyCode.S));
        assertEquals(Direction.RIGHT, method.invoke(controller, KeyCode.D));
    }

    // Test Arrow Key Movement
    @Test
    void convertArrowKeysToDirection() throws Exception {

        // Create GameController Instance
        GameController controller = new GameController();

        // Access Private Key Conversion Method
        Method method = GameController.class
                .getDeclaredMethod("convertKeyToDirection", KeyCode.class);

        // Allow Access To Private Method
        method.setAccessible(true);

        // Verify Arrow Keys
        assertEquals(Direction.UP, method.invoke(controller, KeyCode.UP));
        assertEquals(Direction.LEFT, method.invoke(controller, KeyCode.LEFT));
        assertEquals(Direction.DOWN, method.invoke(controller, KeyCode.DOWN));
        assertEquals(Direction.RIGHT, method.invoke(controller, KeyCode.RIGHT));
    }

    // Test Invalid Keyboard Input
    @Test
    void convertInvalidKeyReturnsNull() throws Exception {

        // Create GameController Instance
        GameController controller = new GameController();

        // Access Private Key Conversion Method
        Method method = GameController.class
                .getDeclaredMethod("convertKeyToDirection", KeyCode.class);

        // Allow Access To Private Method
        method.setAccessible(true);

        // Verify Invalid Key Is Ignored
        assertNull(method.invoke(controller, KeyCode.ENTER));
    }
}