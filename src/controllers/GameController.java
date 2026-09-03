package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import models.Maze;

public class GameController {

    @FXML
    private Pane gamePane;

    private final Maze maze = new Maze();

    private final Image woodWallImage = new Image("file:lib/assets/walls/wood_wall.png");

    private final Image clayWallImage = new Image("file:lib/assets/walls/clay_wall.png");

    private final Image exitImage = new Image("file:lib/assets/others/exit.png");

    private final Image mouseImage = new Image("file:lib/assets/animals/mouse.png");

    private final Image catImage = new Image("file:lib/assets/animals/cat.png");

    private final Image cheeseImage = new Image("file:lib/assets/food/cheese.png");

    @FXML
    public void initialize() {
        Platform.runLater(this::drawLevel);
    }

    private void drawLevel() {

        gamePane.getChildren().clear();

        char[][] layout = maze.getLevel1();

        double tileSize = 35;

        double mazeWidth = layout[0].length * tileSize;
        double mazeHeight = layout.length * tileSize;

        double offsetX = (gamePane.getWidth() - mazeWidth) / 2;
        double offsetY = (gamePane.getHeight() - mazeHeight) / 2;

        for (int row = 0; row < layout.length; row++) {

            for (int col = 0; col < layout[row].length; col++) {

                char tile = layout[row][col];

                double x = offsetX + col * tileSize;
                double y = offsetY + row * tileSize;

                switch (tile) {

                    case '#':
                        addImage(woodWallImage, x, y, tileSize);
                        break;

                    case '%':
                        addImage(clayWallImage, x, y, tileSize);
                        break;

                    case 'F':
                        addImage(cheeseImage, x, y, tileSize);
                        break;

                    case 'P':
                        addImage(mouseImage, x, y, tileSize);
                        break;

                    case 'E':
                        addImage(catImage, x, y, tileSize);
                        break;

                    case 'X':
                        addImage(exitImage, x, y, tileSize);
                        break;

                    case ' ':
                        // Empty space
                        break;
                }
            }
        }
    }

    private void addImage(
            Image image,
            double x,
            double y,
            double size) {

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(size);
        imageView.setFitHeight(size);

        imageView.setX(x);
        imageView.setY(y);

        gamePane.getChildren().add(imageView);
    }

}