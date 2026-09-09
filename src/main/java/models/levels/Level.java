package models.levels;

import java.util.List;

import models.entities.Player;
import models.game.GameBoard;
import models.game.GameBoardConfig;
import models.game.GameObjectType;
import models.objects.Health;
import models.objects.Score;
import models.utils.CollisionManager;
import models.utils.Direction;
import models.utils.EnemyManager;

// Level Owns the Player, Board, Health, and Level Mechanics.
public class Level {

    // Level Components
    protected final Player player;
    protected final GameBoard gameBoard;
    protected final String backgroundPath;
    protected final Score score;
    protected final int maxScore;
    protected final Health health;
    protected final int levelNumber;
    protected final EnemyManager enemyManager;
    protected final CollisionManager collisionManager;

    // Level Builder
    private final LevelBuilder levelBuilder;

    // Level Constructor
    public Level(LevelDefinition definition, Score score) {

        // Store Level Information
        this.levelNumber = definition.levelNumber();
        this.score = score;
        this.backgroundPath = definition.assets().background();
        this.maxScore = definition.maxScore();

        // Create the Game Board
        this.gameBoard = new GameBoard(GameBoardConfig.WIDTH, GameBoardConfig.HEIGHT);

        // Create the Player
        this.player = new Player(
                definition.player().row(),
                definition.player().col(),
                definition.assets().player());

        // Create Health
        this.health = new Health(100);

        // Create Level Managers
        this.enemyManager = new EnemyManager(player, gameBoard, health);

        this.collisionManager = new CollisionManager(gameBoard, score);

        // Create Level Builder
        this.levelBuilder = new LevelBuilder(
                gameBoard,
                enemyManager);

        // Create the Level Objects
        createLevelObjects(
                convertMaze(definition.maze()),
                definition.assets().food(),
                definition.assets().enemy(),
                definition.assets().wall1(),
                definition.assets().wall2(),
                definition.assets().exit());
    }

    // Convert JSON Maze Rows into the char[][] Format Expected by LevelBuilder
    private char[][] convertMaze(List<String> mazeRows) {

        // Create the Character Array
        char[][] maze = new char[mazeRows.size()][];

        // Convert Each Maze Row into a Character Array
        for (int row = 0; row < mazeRows.size(); row++) {
            maze[row] = mazeRows.get(row).toCharArray();
        }

        // Return the Converted Maze
        return maze;
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

    // Check if the Level is Complete
    public boolean isLevelComplete() {

        // Get the Object at the Player's Current Position
        var object = gameBoard.getGameObjectAt(player.getRow(), player.getCol());

        // Return True if the Player is on the Exit
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

    public String getBackgroundPath() {
        return backgroundPath;
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