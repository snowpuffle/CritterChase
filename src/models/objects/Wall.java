package models.objects;

import models.utils.GameObjectType;

// Wall Class Represents a Wall Object in the Game
public class Wall extends GameObject {

    // Wall Constructor
    public Wall(int row, int col, String imagePath) {
        super(row, col, imagePath);
    }

    // Check if the Wall is Passable
    public boolean isPassable() {
        return false;
    }

    // Get the Wall's Symbol
    public GameObjectType getType() {
        return GameObjectType.WALL;
    }
}
