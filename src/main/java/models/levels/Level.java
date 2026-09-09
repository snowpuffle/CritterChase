package models.levels;

import models.entities.Player;
import models.game.GameBoard;
import models.game.GameBoardConfig;
import models.game.GameObjectType;
import models.objects.GameObject;
import models.objects.Health;
import models.objects.Score;
import models.utils.CollisionManager;
import models.utils.Direction;
import models.utils.EnemyManager;

// Level Class Represents Individual Playable Game Level
public class Level {

    private final Player player;
    private final GameBoard gameBoard;
    private final String backgroundPath;
    private final Score score;
    private final int maxScore;
    private final Health health;
    private final int levelNumber;
    private final EnemyManager enemyManager;
    private final CollisionManager collisionManager;
    private final LevelBuilder levelBuilder;

    // Level Constructor
    public Level(
            LevelDefinition definition,
            Score score) {

        // Store Level Information
        this.levelNumber = definition.levelNumber();
        this.maxScore = definition.maxScore();
        this.backgroundPath = definition.assets().background();

        // Store Level Score Reference
        this.score = score;

        // Create GameBoard Using Level Dimensions
        this.gameBoard = new GameBoard(
                GameBoardConfig.WIDTH,
                GameBoardConfig.HEIGHT);

        // Create Player Using Level Player Definition
        this.player = new Player(
                definition.player().row(),
                definition.player().col(),
                definition.assets().player());

        // Create Player Health
        this.health = new Health(100);

        // Create EnemyManager Using Player, GameBoard, and Health
        this.enemyManager = new EnemyManager(
                player,
                gameBoard,
                health);

        // Create CollisionManager
        this.collisionManager = new CollisionManager(
                gameBoard,
                score);

        // Create LevelBuilder
        this.levelBuilder = new LevelBuilder(
                gameBoard,
                enemyManager);

        // Build Level Objects
        buildLevel(definition);
    }

    // Build Level Objects from Level Definition
    private void buildLevel(LevelDefinition definition) {

        // Get Maze Rows
        int height = definition.maze().size();

        // Get Maze Columns
        int width = definition.maze().get(0).length();

        // Create Character Array for Maze
        char[][] maze = new char[height][width];

        // Convert Maze Strings into Character Array
        for (int row = 0; row < height; row++) {

            // Get Current Maze Row
            String mazeRow = definition.maze().get(row);

            // Convert Current Row into Character Array
            maze[row] = mazeRow.toCharArray();
        }

        // Get Level Assets
        String foodImage = definition.assets().food();
        String enemyImage = definition.assets().enemy();
        String wallImage1 = definition.assets().wall1();
        String wallImage2 = definition.assets().wall2();
        String exitImage = definition.assets().exit();

        // Build Game Objects Using LevelBuilder
        levelBuilder.build(
                maze,
                foodImage,
                enemyImage,
                wallImage1,
                wallImage2,
                exitImage);
    }

    // Process One Player Turn
    public boolean takeTurn(Direction direction) {

        // Reject Null Direction Values
        if (direction == null) {
            return false;
        }

        // Attempt Player Movement
        boolean moved = movePlayer(direction);

        // Stop Turn Processing When Player Cannot Move
        if (!moved) {
            return false;
        }

        // Move Enemies After Successful Player Movement
        moveEnemies();

        // Return Successful Turn Result
        return true;
    }

    // Move Player in Requested Direction
    private boolean movePlayer(Direction direction) {

        // Calculate New Player Row
        int newRow = player.getRow()
                + direction.getRowChange();

        // Calculate New Player Column
        int newCol = player.getCol()
                + direction.getColChange();

        // Reject Positions Outside GameBoard
        if (!gameBoard.isValidPosition(newRow, newCol)) {
            return false;
        }

        // Check Enemy Collision Before Player Movement
        if (enemyManager.handlePlayerCollision(newRow, newCol)) {
            return false;
        }

        // Check GameObject Collision Before Player Movement
        if (!collisionManager.canPlayerMoveTo(newRow, newCol)) {
            return false;
        }

        // Move Player to New Position
        player.setPosition(newRow, newCol);

        // Return Successful Movement Result
        return true;
    }

    // Move All Enemies
    private void moveEnemies() {

        // Process Enemy Movement Through EnemyManager
        enemyManager.moveEnemies();
    }

    // Check if Player Reached Level Exit
    public boolean isLevelComplete() {

        // Get GameObject at Player Position
        GameObject object = gameBoard.getGameObjectAt(
                player.getRow(),
                player.getCol());

        // Check Exit Object
        return object != null
                && object.getType() == GameObjectType.EXIT;
    }

    // Get Player
    public Player getPlayer() {
        return player;
    }

    // Get GameBoard
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    // Get Background Image Path
    public String getBackgroundPath() {
        return backgroundPath;
    }

    // Get Level Score
    public Score getScore() {
        return score;
    }

    // Get Maximum Level Score
    public int getMaxScore() {
        return maxScore;
    }

    // Get Player Health
    public Health getHealth() {
        return health;
    }

    // Get Level Number
    public int getLevelNumber() {
        return levelNumber;
    }

    // Get EnemyManager
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
}