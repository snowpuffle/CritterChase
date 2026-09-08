package models.entities;

// Entity Class Represents a Game Entity with Position and Image Representation
public abstract class Entity {

    // Entity Attributes
    protected int row;
    protected int col;
    protected String imagePath;

    // Entity Constructor
    public Entity(int row, int col, String imagePath) {
        this.row = row;
        this.col = col;
        this.imagePath = imagePath;
    }

    // Move the Entity
    public void move(int rowChange, int colChange) {
        this.row += rowChange;
        this.col += colChange;
    }

    // Getters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String getImagePath() {
        return imagePath;
    }

    // Setters
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
}