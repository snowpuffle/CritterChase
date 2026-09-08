package models.levels;

import models.entities.Player;
import models.game.GameBoard;
import models.game.GameObjectType;
import models.objects.Health;
import models.objects.Score;
import models.utils.CollisionManager;
import models.utils.Direction;
import models.utils.EnemyManager;

// Level Owns the Player, Board, Health, and Level Mechanics.
public class Level {

    // Board Dimensions
    protected static final int WIDTH = 15;
    protected static final int HEIGHT = 15;

    // Level Components
    protected final Player player;
    protected final GameBoard gameBoard;
    protected final Score score;
    protected final int maxScore;
    protected final Health health;
    protected final int levelNumber;
    protected final EnemyManager enemyManager;
    protected final CollisionManager collisionManager;

    // Level Renderer
    private final LevelBuilder levelBuilder;

    // Level Constructor
    public Level(LevelDefinition definition, Score score) {
        this.levelNumber = definition.getLevelNumber();
        this.gameBoard = new GameBoard(WIDTH, HEIGHT);
        this.player = new Player(definition.getPlayerRow(), definition.getPlayerCol(), definition.getPlayerImage());
        this.score = score;
        this.maxScore = definition.getMaxScore();
        this.health = new Health(100);
        this.enemyManager = new EnemyManager(player, gameBoard, health);
        this.collisionManager = new CollisionManager(gameBoard, score);
        this.levelBuilder = new LevelBuilder(gameBoard, enemyManager);

        createLevelObjects(
                definition.getMaze(),
                definition.getFoodImage(),
                definition.getEnemyImage(),
                definition.getWallImage1(),
                definition.getWallImage2(),
                definition.getExitImage());
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