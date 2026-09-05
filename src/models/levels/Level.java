package models.levels;

import models.utils.GameBoard;
import models.objects.Health;
import models.objects.Score;
import models.objects.Food;
import models.utils.GameObjectType;
import models.utils.LevelBuilder;
import models.utils.EnemyManager;
import models.utils.Direction;
import models.entities.Player;

// Level Owns the Player, Board, Score, Health, and Level Mechanics.
public abstract class Level {

    // Board Dimensions
    protected static final int WIDTH = 15;
    protected static final int HEIGHT = 15;

    // Level Components
    protected final Player player;
    protected final GameBoard gameBoard;
    protected final Score score;
    protected final Health health;
    protected final int levelNumber;
    protected final EnemyManager enemyManager;

    // Level Renderer
    private final LevelBuilder levelBuilder;

    // Level Constructor
    protected Level(Player player, int levelNumber) {
        this.gameBoard = new GameBoard(WIDTH, HEIGHT);
        this.player = player;
        this.score = new Score();
        this.health = new Health(100);
        this.levelNumber = levelNumber;
        this.enemyManager = new EnemyManager(player, gameBoard, health);
        this.levelBuilder = new LevelBuilder(gameBoard, enemyManager);
    }

    // Create the Level Objects from the Maze
    protected void createLevelObjects(char[][] maze, String foodImage, String enemyImage, String wallImage1,
            String wallImage2, String exitImage) {

        levelBuilder.build(maze, foodImage, enemyImage, wallImage1, wallImage2, exitImage);
    }

    // Move Player
    public boolean movePlayer(Direction direction) {

        // Calculate New Position
        int newRow = player.getRow() + direction.getRowChange();
        int newCol = player.getCol() + direction.getColChange();

        // Check if the New Position is Valid
        if (!gameBoard.isValidPosition(newRow, newCol)) {
            return false;
        }

        // Handle Player Collision with Enemies
        if (enemyManager.handlePlayerCollision(newRow, newCol)) {
            return false;
        }

        // Handle Player Collision with Static Objects
        if (!handleCollision(newRow, newCol)) {
            return false;
        }

        // Move the Player
        player.move(direction.getRowChange(), direction.getColChange());

        return true;
    }

    // Handle Static Object Collision
    private boolean handleCollision(int row, int col) {

        // Get the Game Object at the New Position
        var object = gameBoard.getGameObjectAt(row, col);

        // If There is No Object, the Position is Valid
        if (object == null) {
            return true;
        }

        // Handle Collision Based on Object Type
        switch (object.getType()) {
            case FOOD:
                collectFood((Food) object);
                return true;
            case WALL:
                return false;
            case EXIT:
                return true;
            default:
                return true;
        }
    }

    // Collect Food
    private void collectFood(Food food) {
        score.addPoints(food.getPoints());
        gameBoard.removeGameObjectAt(food.getRow(), food.getCol());
    }

    // Move Enemies
    public void moveEnemies() {
        enemyManager.moveEnemies();
    }

    // Check Level Complete
    public boolean isLevelComplete() {

        // Check if the Player is on the Exit Object
        var object = gameBoard.getGameObjectAt(player.getRow(), player.getCol());

        // Return True if the Player is on the Exit Object, Otherwise Return False
        return object != null && object.getType() == GameObjectType.EXIT;
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