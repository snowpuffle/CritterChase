package models.levels;

import models.entities.Player;
import models.game.GameBoard;
import models.game.GameBoardConfig;
import models.game.GameObjectType;
import models.objects.GameObject;
import models.objects.Score;
import models.objects.Weapon;
import models.utils.CollisionManager;
import models.utils.CombatManager;
import models.utils.Direction;
import models.utils.EnemyManager;

// Level Class Represents Individual Playable Game Level
public class Level {

    private final Player player;
    private final GameBoard gameBoard;
    private final String backgroundPath;
    private final Score score;
    private final int maxScore;
    private final int levelNumber;
    private final EnemyManager enemyManager;
    private final CollisionManager collisionManager;
    private final CombatManager combatManager;
    private final LevelBuilder levelBuilder;

    // Level Constructor
    public Level(LevelDefinition definition, Score score) {
        this.levelNumber = definition.levelNumber();
        this.maxScore = definition.maxScore();
        this.backgroundPath = definition.assets().background();
        this.score = score;
        this.gameBoard = new GameBoard(GameBoardConfig.WIDTH, GameBoardConfig.HEIGHT);
        this.player = new Player(definition.player().row(), definition.player().col(), definition.assets().player());
        this.enemyManager = new EnemyManager(player, gameBoard);
        this.collisionManager = new CollisionManager(gameBoard, score);
        this.combatManager = new CombatManager(player, enemyManager);
        this.levelBuilder = new LevelBuilder(gameBoard, enemyManager);

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
            maze[row] = definition.maze().get(row).toCharArray();
        }

        // Get Level Assets
        String foodImage = definition.assets().food();
        String enemyImage = definition.assets().enemy();
        String wallImage1 = definition.assets().wall1();
        String wallImage2 = definition.assets().wall2();
        String exitImage = definition.assets().exit();
        String weaponImage = definition.assets().weapon();

        // Build Game Objects Using LevelBuilder
        levelBuilder.build(maze, foodImage, enemyImage, wallImage1, wallImage2, exitImage, weaponImage);
    }

    // Process One Player Turn
    public boolean takeTurn(Direction direction) {

        // Reject Invalid Direction
        if (direction == null) {
            return false;
        }

        // Calculate Target Position
        int targetRow = player.getRow() + direction.getRowChange();
        int targetCol = player.getCol() + direction.getColChange();

        // Reject Positions Outside GameBoard
        if (!gameBoard.isValidPosition(targetRow, targetCol)) {
            return false;
        }

        // Attempt to Attack an Enemy
        if (combatManager.attack(targetRow, targetCol)) {
            moveEnemies();
            return true;
        }

        // Attempt to Move Player
        if (movePlayer(targetRow, targetCol)) {
            moveEnemies();
            return true;
        }

        // Player Could Not Attack or Move
        return false;
    }

    // Move Player to Target Position
    private boolean movePlayer(int row, int col) {

        // Check Enemy Collision Before Player Movement
        if (enemyManager.handlePlayerCollision(row, col)) {
            return false;
        }

        // Check GameObject Collision Before Player Movement
        if (!collisionManager.canPlayerMoveTo(row, col)) {
            return false;
        }

        // Move Player to New Position
        player.setPosition(row, col);

        // Collect Weapon at Player Position
        collectWeapon(row, col);

        return true;
    }

    // Move All Enemies
    private void moveEnemies() {

        // Process Enemy Movement Through EnemyManager
        enemyManager.moveEnemies();
    }

    // Collect Weapon at Player Position
    private void collectWeapon(int row, int col) {

        // Get GameObject at Player Position
        GameObject object = gameBoard.getGameObjectAt(row, col);

        // Check if GameObject is a Weapon
        if (object == null || object.getType() != GameObjectType.WEAPON) {
            return;
        }

        // Convert GameObject to Weapon
        Weapon weapon = (Weapon) object;

        // Equip Weapon to Player
        player.equipWeapon(weapon);

        // Remove Weapon from GameBoard
        gameBoard.removeGameObjectAt(row, col);
    }

    // Check if Player Reached Level Exit
    public boolean isLevelComplete() {

        // Get GameObject at Player Position
        GameObject object = gameBoard.getGameObjectAt(player.getRow(), player.getCol());

        // Check Exit Object
        return object != null && object.getType() == GameObjectType.EXIT;
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

    // Get Level Number
    public int getLevelNumber() {
        return levelNumber;
    }

    // Get EnemyManager
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
}