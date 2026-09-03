package controllers;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import models.entities.Enemy;
import models.entities.Player;
import models.objects.GameObject;
import models.utils.GameBoard;
import models.utils.EnemyManager;

// LevelRenderer Class Responsible for Rendering the Game Level
public class LevelRenderer {

    // Game Pane to Render the Level
    private final Pane gamePane;

    // Game Objects Needed for Rendering
    private final Player player;
    private final EnemyManager enemyManager;
    private final GameBoard gameBoard;

    // ImageViews for Static Game Objects
    private final ImageView[][] objectViews;

    // ImageView for Player
    private final ImageView playerView;

    // ImageViews for Enemies
    private final Map<Enemy, ImageView> enemyViews;

    // Tile Size
    private static final double TILE_SIZE = 30;

    // Cache Images so They Are Only Loaded Once
    private final Map<String, Image> imageCache = new HashMap<>();

    // LevelRenderer Constructor
    public LevelRenderer(Pane gamePane, Player player, EnemyManager enemyManager, GameBoard gameBoard) {

        this.gamePane = gamePane;
        this.player = player;
        this.enemyManager = enemyManager;
        this.gameBoard = gameBoard;

        // Create ImageView Storage for Static Objects
        this.objectViews = new ImageView[gameBoard.getHeight()][gameBoard.getWidth()];

        // Create ImageView Storage for Enemies
        this.enemyViews = new HashMap<>();

        // Cache All Images Used by the Level
        cacheImages();

        // Create ImageView for Player
        this.playerView = createImageView(player.getImagePath());
    }

    // Draw the Level for the First Time
    public void drawLevel() {

        // Clear the Game Pane
        gamePane.getChildren().clear();

        // Calculate the Maze Offset
        double offsetX = calculateOffsetX();
        double offsetY = calculateOffsetY();

        // Create Static Game Object Views
        createObjectViews(offsetX, offsetY);

        // Create Enemy Views
        createEnemyViews(offsetX, offsetY);

        // Add Player View
        updatePlayerView(offsetX, offsetY);

        // Add Static Objects to the Pane
        addObjectViewsToPane();

        // Add Enemy Views to the Pane
        addEnemyViewsToPane();

        // Add Player View to the Pane
        gamePane.getChildren().add(playerView);
    }

    // Update the Existing Level After a Game State Change
    public void updateLevel() {

        // Calculate the Current Maze Offset
        double offsetX = calculateOffsetX();
        double offsetY = calculateOffsetY();

        // Update Static Game Objects
        updateObjectViews();

        // Update Enemy Positions
        updateEnemyViews(offsetX, offsetY);

        // Update Player Position
        updatePlayerView(offsetX, offsetY);
    }

    // Create ImageViews for Static Game Objects
    private void createObjectViews(double offsetX, double offsetY) {

        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                GameObject object = gameBoard.getGameObjectAt(row, col);

                if (object == null) {
                    continue;
                }

                ImageView imageView = createImageView(object.getImagePath());

                setImageViewPosition(
                        imageView,
                        row,
                        col,
                        offsetX,
                        offsetY);

                objectViews[row][col] = imageView;
            }
        }
    }

    // Update Static Game Object Views
    private void updateObjectViews() {

        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                ImageView imageView = objectViews[row][col];
                GameObject object = gameBoard.getGameObjectAt(row, col);

                // No Existing View
                if (imageView == null) {
                    continue;
                }

                // Hide the View if the Object Has Been Removed
                imageView.setVisible(object != null);
            }
        }
    }

    // Create ImageViews for All Enemies
    private void createEnemyViews(double offsetX, double offsetY) {

        for (Enemy enemy : enemyManager.getEnemies()) {

            ImageView imageView = createImageView(enemy.getImagePath());

            setImageViewPosition(
                    imageView,
                    enemy.getRow(),
                    enemy.getCol(),
                    offsetX,
                    offsetY);

            enemyViews.put(enemy, imageView);
        }
    }

    // Update All Enemy Positions
    private void updateEnemyViews(double offsetX, double offsetY) {

        for (Enemy enemy : enemyManager.getEnemies()) {

            ImageView imageView = enemyViews.get(enemy);

            if (imageView == null) {
                continue;
            }

            setImageViewPosition(
                    imageView,
                    enemy.getRow(),
                    enemy.getCol(),
                    offsetX,
                    offsetY);
        }
    }

    // Update Player Position
    private void updatePlayerView(double offsetX, double offsetY) {

        setImageViewPosition(
                playerView,
                player.getRow(),
                player.getCol(),
                offsetX,
                offsetY);
    }

    // Add Static Object Views to the Game Pane
    private void addObjectViewsToPane() {

        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                ImageView imageView = objectViews[row][col];

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

        ImageView imageView = new ImageView(imageCache.get(imagePath));

        imageView.setFitWidth(TILE_SIZE);
        imageView.setFitHeight(TILE_SIZE);

        return imageView;
    }

    // Set the Position of an ImageView
    private void setImageViewPosition(ImageView imageView, int row, int col, double offsetX, double offsetY) {

        imageView.setX(offsetX + col * TILE_SIZE);
        imageView.setY(offsetY + row * TILE_SIZE);
    }

    // Calculate the Horizontal Offset Needed to Center the Maze
    private double calculateOffsetX() {

        double mazeWidth = gameBoard.getWidth() * TILE_SIZE;

        return (gamePane.getWidth() - mazeWidth) / 2;
    }

    // Calculate the Vertical Offset Needed to Center the Maze
    private double calculateOffsetY() {

        double mazeHeight = gameBoard.getHeight() * TILE_SIZE;

        return (gamePane.getHeight() - mazeHeight) / 2;
    }

    // Cache All Images Used by the Level
    private void cacheImages() {

        // Cache Player Image
        cacheImage(player.getImagePath());

        // Cache Enemy Images
        for (Enemy enemy : enemyManager.getEnemies()) {
            cacheImage(enemy.getImagePath());
        }

        // Cache Game Object Images
        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                GameObject object = gameBoard.getGameObjectAt(row, col);

                if (object != null) {
                    cacheImage(object.getImagePath());
                }
            }
        }
    }

    // Cache a Single Image
    private void cacheImage(String imagePath) {

        if (!imageCache.containsKey(imagePath)) {
            imageCache.put(imagePath, new Image(imagePath));
        }
    }
}
