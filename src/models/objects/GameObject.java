package models.objects;

import models.utils.GameObjectType;

public abstract class GameObject {

    // Game Object Attributes
    protected int row;
    protected int col;
    protected String imagePath;

    // Game Object Constructor
    public GameObject(int row, int col, String imagePath) {
        this.row = row;
        this.col = col;
        this.imagePath = imagePath;
    }

    // Get the Game Object Type
    public abstract GameObjectType getType();

    // Getters
    public String getImagePath() {
        return imagePath;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
