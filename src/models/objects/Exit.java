package models.objects;

import models.utils.GameObjectType;

// Exit Class Represents an Exit Object in the Game
public class Exit extends GameObject {

    // Exit Constructor
    public Exit(int row, int col, String imagePath) {
        super(row, col, imagePath);
    }

    // Check if the Exit is Passable
    public boolean isPassable() {
        return true;
    }

    // Get the Exit's Symbol
    public GameObjectType getType() {
        return GameObjectType.EXIT;
    }
}