package models.levels;

import models.entities.Player;
import models.game.GameBoard;
import models.game.GameObjectType;
import models.objects.Food;
import models.objects.GameObject;
import models.objects.Health;
import models.objects.Score;
import models.utils.CollisionManager;
import models.utils.Direction;
import models.utils.EnemyManager;

// Level Owns the Player, Board, Health, and Level Mechanics.
public abstract class Level {

    // Board Dimensions
    protected static final int WIDTH = 15;
    protected static final int HEIGHT = 15;

    // Level Components
    protected final Player player;
    protected final GameBoard gameBoard;
    protected final Score score;
    private int maxScore;
    protected final Health health;
    protected final int levelNumber;
    protected final EnemyManager enemyManager;
    protected final CollisionManager collisionManager;

    // Level Renderer
    private final LevelBuilder levelBuilder;

    // Level Constructor
    protected Level(Player player, int levelNumber, Score score) {
        this.gameBoard = new GameBoard(WIDTH, HEIGHT);
        this.player = player;
        this.score = score;
        this.health = new Health(100);
        this.levelNumber = levelNumber;
        this.enemyManager = new EnemyManager(player, gameBoard, health);
        this.collisionManager = new CollisionManager(gameBoard, score);
        this.levelBuilder = new LevelBuilder(gameBoard, enemyManager);
    }

    // Create the Level Objects from the Maze
    protected void createLevelObjects(char[][] maze, String foodImage, String enemyImage, String wallImage1,
            String wallImage2, String exitImage) {

        levelBuilder.build(maze, foodImage, enemyImage, wallImage1, wallImage2, exitImage);
    }

    // Process One Complete Player Turn
    public boolean takeTurn(Direction direction) {

        // Move the Player
        boolean moved = movePlayer(direction);

        // Stop if the Player Could Not Move
        if (!moved) {
            return false;
        }

        // Move Enemies After the Player Moves
        moveEnemies();

        // Return True if the Turn Was Processed
        return true;
    }

    // Move Player
    public boolean movePlayer(Direction direction) {

        // Calculate New Player Position
        int newRow = player.getRow() + direction.getRowChange();
        int newCol = player.getCol() + direction.getColChange();

        // Check if the New Position is Valid
        if (!gameBoard.isValidPosition(newRow, newCol)) {
            return false;
        }

        // Check if the Player Collides with an Enemy
        if (enemyManager.handlePlayerCollision(newRow, newCol)) {
            return false;
        }

        // Check if the Player Can Move to the New Position
        if (!collisionManager.canPlayerMoveTo(newRow, newCol)) {
            return false;
        }

        // Move the Player to the New Position
        player.move(direction.getRowChange(), direction.getColChange());

        return true;
    }

    // Move Enemies
    public void moveEnemies() {
        enemyManager.moveEnemies();
    }

    // Check Level Complete
    public boolean isLevelComplete() {

        // Check if the Player is on the Exit Object
        var object = gameBoard.getGameObjectAt(player.getRow(), player.getCol());

        return object != null && object.getType() == GameObjectType.EXIT;
    }

    // Calculate Max Score for the Level
    public void calculateAndStoreMaxScore() {

        // Reset the Maximum Score Before Calculating
        maxScore = 0;

        // Loop Through Each Row of the Game Board
        for (int row = 0; row < gameBoard.getHeight(); row++) {

            // Loop Through Each Column of the Game Board
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                // Get the Game Object at the Current Position
                GameObject object = gameBoard.getGameObjectAt(row, col);

                // Add the Food's Points to the Maximum Score
                if (object instanceof Food) {
                    maxScore += ((Food) object).getPoints();
                }
            }
        }
    }

    // Getters
    public Player getPlayer() {
        return player;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Score getScore() {
        return score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public Health getHealth() {
        return health;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
}