package models.objects;

import models.utils.GameObjectType;

public class Food extends GameObject {

    // Food Attributes
    private final int points;

    // Food Constructor
    public Food(int row, int col, String imagePath) {
        super(row, col, imagePath);
        this.points = 10;
    }

    // Get the Food's Point Value
    public int getPoints() {
        return points;
    }

    // Get the Food's Symbol
    public GameObjectType getType() {
        return GameObjectType.FOOD;
    }
}
