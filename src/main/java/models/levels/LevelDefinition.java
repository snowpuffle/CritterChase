package models.levels;

public class LevelDefinition {

    private final int levelNumber;
    private final int playerRow;
    private final int playerCol;

    private final String playerImage;
    private final String foodImage;
    private final String enemyImage;
    private final String wallImage1;
    private final String wallImage2;
    private final String exitImage;

    private final char[][] maze;

    // Level Definition Contructor
    public LevelDefinition(int levelNumber, int playerRow, int playerCol, String playerImage, String foodImage,
            String enemyImage, String wallImage1, String wallImage2, String exitImage, char[][] maze) {

        this.levelNumber = levelNumber;
        this.playerRow = playerRow;
        this.playerCol = playerCol;
        this.playerImage = playerImage;
        this.foodImage = foodImage;
        this.enemyImage = enemyImage;
        this.wallImage1 = wallImage1;
        this.wallImage2 = wallImage2;
        this.exitImage = exitImage;
        this.maze = maze;
    }

    // Getters
    public int getLevelNumber() {
        return levelNumber;
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public String getPlayerImage() {
        return playerImage;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public String getEnemyImage() {
        return enemyImage;
    }

    public String getWallImage1() {
        return wallImage1;
    }

    public String getWallImage2() {
        return wallImage2;
    }

    public String getExitImage() {
        return exitImage;
    }

    public char[][] getMaze() {
        return maze;
    }
}