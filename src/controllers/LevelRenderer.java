package controllers;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import models.entities.Enemy;
import models.entities.Player;
import models.objects.GameObject;
import models.utils.EnemyManager;
import models.utils.GameBoard;

// Level Renderer is Responsible for Rendering the Current Game Level
public class LevelRenderer {

    // Tile Size Used to Determine the Size of Each Game Object
    private static final double TILE_SIZE = 30.0;

    // Game State and JavaFX Game Pane
    private final Pane gamePane;
    private final Player player;
    private final EnemyManager enemyManager;
    private final GameBoard gameBoard;

    // ImageViews for Static Game Objects
    private final ImageView[][] objectViews;

    // ImageView for Player
    private final ImageView playerView;

    // ImageViews for Enemies
    private final Map<Enemy, ImageView> enemyViews;

    // Image Cache Used to Prevent Loading the Same Image Multiple Times
    private final Map<String, Image> imageCache = new HashMap<>();

    // Level Renderer Constructor
    public LevelRenderer(Pane gamePane, Player player, EnemyManager enemyManager, GameBoard gameBoard) {

        this.gamePane = gamePane;
        this.player = player;
        this.enemyManager = enemyManager;
        this.gameBoard = gameBoard;

        // Create Storage for Static Game Object Views
        objectViews = new ImageView[gameBoard.getHeight()][gameBoard.getWidth()];

        // Create Storage for Enemy Views
        enemyViews = new HashMap<>();

        // Create the Player ImageView
        playerView = createImageView(player.getImagePath());
    }

    // Draw the Level for the First Time
    public void drawLevel() {

        // Clear Any Existing Views from the Game Pane
        gamePane.getChildren().clear();

        // Calculate the Offsets Needed to Center the Maze
        double offsetX = calculateOffsetX();
        double offsetY = calculateOffsetY();

        // Create Views for Static Game Objects
        createObjectViews(offsetX, offsetY);

        // Create Views for Enemies
        createEnemyViews(offsetX, offsetY);

        // Set the Initial Player Position
        updatePlayerView(offsetX, offsetY);

        // Add Static Game Object Views to the Game Pane
        addObjectViewsToPane();

        // Add Enemy Views to the Game Pane
        addEnemyViewsToPane();

        // Add the Player View Last so the Player Appears Above Other Objects
        gamePane.getChildren().add(playerView);
    }

    // Update the Existing Level Views After a Game State Change
    public void updateLevel() {

        // Recalculate the Offsets in Case the Game Pane Size Changes
        double offsetX = calculateOffsetX();
        double offsetY = calculateOffsetY();

        // Update Static Game Object Visibility
        updateObjectViews();

        // Update Enemy Positions and Remove Deleted Enemy Views
        updateEnemyViews(offsetX, offsetY);

        // Update Player Position
        updatePlayerView(offsetX, offsetY);
    }

    // Create ImageViews for the Static Game Objects on the Board
    private void createObjectViews(double offsetX, double offsetY) {

        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                // Get the Game Object at the Current Board Position
                GameObject object = gameBoard.getGameObjectAt(row, col);

                // Skip Empty Board Positions
                if (object == null) {
                    continue;
                }

                // Create the ImageView Using the Object's Image
                ImageView imageView = createImageView(object.getImagePath());

                // Position the ImageView on the Game Board
                setImageViewPosition(imageView, row, col, offsetX, offsetY);

                // Store the ImageView for Later Updates
                objectViews[row][col] = imageView;
            }
        }
    }

    // Update the Visibility of Existing Static Game Object Views
    private void updateObjectViews() {

        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                // Get the Existing ImageView
                ImageView imageView = objectViews[row][col];

                // Skip Positions Without an ImageView
                if (imageView == null) {
                    continue;
                }

                // Check Whether a Game Object Still Exists at This Position
                GameObject object = gameBoard.getGameObjectAt(row, col);

                // Hide the ImageView When the Game Object Has Been Removed
                imageView.setVisible(object != null);
            }
        }
    }

    // Create ImageViews for All Enemies
    private void createEnemyViews(double offsetX, double offsetY) {

        for (Enemy enemy : enemyManager.getEnemies()) {

            // Create the Enemy ImageView
            ImageView imageView = createImageView(enemy.getImagePath());

            // Position the Enemy on the Game Board
            setImageViewPosition(imageView, enemy.getRow(), enemy.getCol(), offsetX,
                    offsetY);

            // Store the Enemy View for Later Updates
            enemyViews.put(enemy, imageView);
        }
    }

    // Update Enemy Positions and Remove Views for Deleted Enemies
    private void updateEnemyViews(double offsetX, double offsetY) {

        // Remove ImageViews for Enemies That No Longer Exist
        enemyViews.entrySet().removeIf(entry -> {

            Enemy enemy = entry.getKey();
            ImageView imageView = entry.getValue();

            // Keep the View if the Enemy Still Exists
            if (enemyManager.getEnemies().contains(enemy)) {
                return false;
            }

            // Remove the View from the Game Pane
            gamePane.getChildren().remove(imageView);

            // Remove the Enemy/View Entry from the Map
            return true;
        });

        // Update Existing Enemies and Create Views for New Enemies
        for (Enemy enemy : enemyManager.getEnemies()) {

            // Get the Existing ImageView for the Enemy
            ImageView imageView = enemyViews.get(enemy);

            // Create a View if the Enemy Does Not Have One
            if (imageView == null) {

                imageView = createImageView(enemy.getImagePath());

                enemyViews.put(enemy, imageView);

                // Add the New Enemy View to the Game Pane
                gamePane.getChildren().add(imageView);
            }

            // Update the Enemy's Position
            setImageViewPosition(
                    imageView,
                    enemy.getRow(),
                    enemy.getCol(),
                    offsetX,
                    offsetY);
        }
    }

    // Update the Player's Position
    private void updatePlayerView(double offsetX, double offsetY) {

        // Position the Player Based on the Current Board Coordinates
        setImageViewPosition(
                playerView,
                player.getRow(),
                player.getCol(),
                offsetX,
                offsetY);
    }

    // Add Static Game Object Views to the Game Pane
    private void addObjectViewsToPane() {

        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                // Get the ImageView at the Current Board Position
                ImageView imageView = objectViews[row][col];

                // Add the ImageView if One Exists
                if (imageView != null) {
                    gamePane.getChildren().add(imageView);
                }
            }
        }
    }

    // Add Enemy Views to the Game Pane
    private void addEnemyViewsToPane() {

        for (ImageView imageView : enemyViews.values()) {
            gamePane.getChildren().add(imageView);
        }
    }

    // Create an ImageView Using a Cached Image
    private ImageView createImageView(String imagePath) {

        // Retrieve the Image from the Cache or Load it if Needed
        Image image = imageCache.computeIfAbsent(
                imagePath,
                path -> new Image(path));

        // Create an ImageView Using the Cached Image
        ImageView imageView = new ImageView(image);

        // Set the ImageView Size to Match the Game Tile Size
        imageView.setFitWidth(TILE_SIZE);
        imageView.setFitHeight(TILE_SIZE);

        return imageView;
    }

    // Position an ImageView Using the Game Board Coordinates
    private void setImageViewPosition(ImageView imageView, int row, int col, double offsetX, double offsetY) {

        // Calculate the X and Y Position Based on the Row and Column
        imageView.setX(offsetX + col * TILE_SIZE);
        imageView.setY(offsetY + row * TILE_SIZE);
    }

    // Calculate the Horizontal Offset Needed to Center the Maze
    private double calculateOffsetX() {

        // Calculate the Total Width of the Maze
        double mazeWidth = gameBoard.getWidth() * TILE_SIZE;

        // Center the Maze Horizontally Within the Game Pane
        return (gamePane.getWidth() - mazeWidth) / 2;
    }

    // Calculate the Vertical Offset Needed to Center the Maze
    private double calculateOffsetY() {

        // Calculate the Total Height of the Maze
        double mazeHeight = gameBoard.getHeight() * TILE_SIZE;

        // Center the Maze Vertically Within the Game Pane
        return (gamePane.getHeight() - mazeHeight) / 2;
    }
}