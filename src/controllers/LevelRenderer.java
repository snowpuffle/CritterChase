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
    private final Player player;
    private final EnemyManager enemyManager;
    private final GameBoard gameBoard;

    // Tile Size
    private final double tileSize = 30;

    // Cache Images so They Are Only Loaded Once
    private final Map<String, Image> imageCache = new HashMap<>();

    // LevelRenderer Constructor
    public LevelRenderer(
            Pane gamePane,
            Player player,
            EnemyManager enemyManager,
            GameBoard gameBoard) {

        this.gamePane = gamePane;
        this.player = player;
        this.enemyManager = enemyManager;
        this.gameBoard = gameBoard;

        // Load Images Once
        cacheImage(player.getImagePath());

        for (Enemy enemy : enemyManager.getEnemies()) {
            cacheImage(enemy.getImagePath());
        }

        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {

                GameObject object = gameBoard.getGameObjectAt(row, col);

                if (object != null) {
                    cacheImage(object.getImagePath());
                }
            }
        }
    }

    // Draw the Level on the Game Pane
    public void drawLevel() {

        // Clear the Game Pane
        gamePane.getChildren().clear();

        // Calculate the Maze Dimensions
        double mazeWidth = gameBoard.getWidth() * tileSize;
        double mazeHeight = gameBoard.getHeight() * tileSize;

        // Calculate the Offsets to Center the Maze
        double offsetX = (gamePane.getWidth() - mazeWidth) / 2;
        double offsetY = (gamePane.getHeight() - mazeHeight) / 2;

        // Loop Through Each Row and Column
        for (int row = 0; row < gameBoard.getHeight(); row++) {
            for (int col = 0; col < gameBoard.getWidth(); col++) {
                drawTile(row, col, offsetX, offsetY);
            }
        }
    }

    // Draw a Single Tile
    private void drawTile(int row, int col, double offsetX, double offsetY) {

        // Draw Player
        if (player.getRow() == row && player.getCol() == col) {
            addImage(player.getImagePath(), row, col, offsetX, offsetY);
            return;
        }

        // Draw Enemy
        Enemy enemy = enemyManager.getEnemyAt(row, col);

        if (enemy != null) {
            addImage(enemy.getImagePath(), row, col, offsetX, offsetY);
            return;
        }

        // Draw Game Object
        GameObject object = gameBoard.getGameObjectAt(row, col);

        if (object != null) {
            addImage(object.getImagePath(), row, col, offsetX, offsetY);
        }
    }

    // Cache an Image
    private void cacheImage(String imagePath) {

        if (!imageCache.containsKey(imagePath)) {
            imageCache.put(imagePath, new Image(imagePath));
        }
    }

    // Add an Image to the Game Pane
    private void addImage(
            String imagePath,
            int row,
            int col,
            double offsetX,
            double offsetY) {

        // Get the Cached Image
        Image image = imageCache.get(imagePath);

        // Create ImageView Using the Cached Image
        ImageView imageView = new ImageView(image);

        // Set Image Size
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);

        // Set Image Position
        imageView.setX(offsetX + col * tileSize);
        imageView.setY(offsetY + row * tileSize);

        // Add ImageView to Game Pane
        gamePane.getChildren().add(imageView);
    }
}