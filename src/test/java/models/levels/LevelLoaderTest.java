package models.levels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

// LevelLoaderTest Tests Level JSON Loading
class LevelLoaderTest {

    // Test Valid Level Loads Successfully
    @Test
    void loadValidLevelReturnsDefinition() {
        LevelDefinition definition = LevelLoader.load(1);
        assertNotNull(definition);
        assertEquals(1, definition.levelNumber());
    }

    // Test Another Valid Level Loads Successfully
    @Test
    void loadAnotherValidLevelReturnsDefinition() {
        LevelDefinition definition = LevelLoader.load(2);
        assertNotNull(definition);
        assertEquals(2, definition.levelNumber());
    }

    // Test Missing Level File
    @Test
    void loadMissingLevelThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> LevelLoader.load(999));
        assertEquals("Level File Not Found: /levels/level_999.json", exception.getMessage());
    }

    // Test Invalid Level Number
    @Test
    void loadInvalidLevelNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> LevelLoader.load(0));
    }
}
