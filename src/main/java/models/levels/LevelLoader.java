package models.levels;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

// LevelLoader Loads Level Definitions from JSON Files
public class LevelLoader {

    // Jackson JSON Object Mapper
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    // Load Level Definition from JSON File
    public static LevelDefinition load(int levelNumber) {

        // Create Level Resource Path
        String resourcePath = "/levels/level_" + levelNumber + ".json";

        // Open Level JSON Resource
        try (InputStream inputStream = LevelLoader.class.getResourceAsStream(resourcePath)) {

            // Reject Missing Level Resource
            if (inputStream == null) {
                throw new IllegalArgumentException(
                        "Level File Not Found: " + resourcePath);
            }

            // Convert JSON into LevelDefinition
            LevelDefinition definition = OBJECT_MAPPER.readValue(
                    inputStream,
                    LevelDefinition.class);

            // Validate Loaded Level
            LevelValidator.validate(definition);

            // Validate Requested Level Number
            if (definition.levelNumber() != levelNumber) {
                throw new IllegalArgumentException(
                        "Level Number Does Not Match Requested Level: "
                                + levelNumber);
            }

            // Return Validated Level Definition
            return definition;

        } catch (IOException exception) {

            // Convert JSON Loading Error
            throw new IllegalArgumentException(
                    "Unable to Load Level: " + levelNumber,
                    exception);
        }
    }
}
