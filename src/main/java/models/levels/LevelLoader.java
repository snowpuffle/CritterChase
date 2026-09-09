package models.levels;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

// LevelLoader Loads and Validates Level Definitions from JSON Resources
public final class LevelLoader {

    // Create the ObjectMapper Used to Convert JSON into Java Objects
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Private Constructor Prevents the Class from Being Instantiated
    private LevelLoader() {
        // Utility class
    }

    // Load a Level Definition from the Corresponding JSON Resource
    public static LevelDefinition load(int levelNumber) {

        // Build the Resource Path for the Requested Level
        String path = "/levels/level_" + levelNumber + ".json";

        // Open the Level JSON Resource
        InputStream input = LevelLoader.class.getResourceAsStream(path);

        // Check if the Level Resource Exists
        if (input == null) {
            throw new IllegalArgumentException("Level Resource Not Found: " + path);
        }

        // Automatically Close the Input Stream After Loading
        try (input) {

            // Convert the JSON Resource into a Level Definition
            LevelDefinition definition = MAPPER.readValue(input, LevelDefinition.class);

            // Validate the Loaded Level Definition
            validate(definition);

            // Return the Validated Level Definition
            return definition;

        } catch (IOException e) {

            // Convert the File Reading Error into a Runtime Exception
            throw new IllegalStateException("Failed to Load Level " + levelNumber, e);
        }
    }

    // Validate the Data Loaded from the Level JSON File
    private static void validate(LevelDefinition definition) {

        // Check that the Level Number is Valid
        if (definition.levelNumber() <= 0) {
            throw new IllegalArgumentException("Level Number Must be > 0.");
        }

        // Check that the Maximum Score is Not Negative
        if (definition.maxScore() < 0) {
            throw new IllegalArgumentException("Max Score Cannot be Negative.");
        }

        // Check that the Player Starting Position Exists
        if (definition.player() == null) {
            throw new IllegalArgumentException("Player Position is Missing.");
        }

        // Check that the Level Assets Exist
        if (definition.assets() == null) {
            throw new IllegalArgumentException("Level Assets are Missing.");
        }

        // Check that the Maze Contains Exactly 15 Rows
        if (definition.maze().size() != 15) {
            throw new IllegalArgumentException(
                    "Level " + definition.levelNumber() + " Must Have Exactly 15 Rows.");
        }

        // Check that the Player Starting Position is Inside the Maze
        if (definition.player().row() < 0
                || definition.player().row() >= 15
                || definition.player().col() < 0
                || definition.player().col() >= 15) {

            throw new IllegalArgumentException("Player Starting Position is Outside the Maze.");
        }

        // Keep Track of the Number of Exit Positions
        int exitCount = 0;

        // Validate Each Row of the Maze
        for (int row = 0; row < definition.maze().size(); row++) {

            // Get the Current Maze Row
            String mazeRow = definition.maze().get(row);

            // Check that the Current Row Contains Exactly 15 Characters
            if (mazeRow.length() != 15) {
                throw new IllegalArgumentException(
                        "Level " + definition.levelNumber() + " Row " + row + " Must Contain Exactly 15 Characters.");
            }

            // Validate Each Character in the Current Row
            for (int col = 0; col < mazeRow.length(); col++) {

                // Get the Current Maze Character
                char cell = mazeRow.charAt(col);

                // Count the Exit Positions
                if (cell == 'X') {
                    exitCount++;
                }

                // Check that the Maze Character is a Valid Game Object
                if ("#%FEXP ".indexOf(cell) == -1) {
                    throw new IllegalArgumentException(
                            "Invalid Maze Character '" + cell + "' at Row " + row + ", Column " + col + " in Level "
                                    + definition.levelNumber());
                }
            }
        }

        // Check that the Maze Contains Exactly One Exit
        if (exitCount != 1) {
            throw new IllegalArgumentException(
                    "Level " + definition.levelNumber() + " Must Contain Exactly One Exit.");
        }
    }
}