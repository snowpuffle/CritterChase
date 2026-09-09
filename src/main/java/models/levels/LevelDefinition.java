package models.levels;

import java.util.List;

import models.utils.PlayerStart;

// LevelDefinition Stores the Data Used to Create a Level
public record LevelDefinition(int levelNumber, int maxScore, PlayerStart player, LevelAssets assets,
        List<String> maze) {

    // Validate the Level Definition When it is Created
    public LevelDefinition {

        // Check that the Maze Exists and Contains at Least One Row
        if (maze == null || maze.isEmpty()) {
            throw new IllegalArgumentException("Maze Cannot Be Empty.");
        }

        // Create an Unmodifiable Copy of the Maze
        // Prevents the Original List from Being Changed After Creation
        maze = List.copyOf(maze);
    }
}