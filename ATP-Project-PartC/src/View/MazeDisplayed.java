package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayed extends Canvas {
    private int[][] maze;
    private ArrayList<int[]> solution;
    // player position:
    private int playerRow = 0;
    private int playerCol = 0;
    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameFinal = new SimpleStringProperty();


    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public void setSolution(ArrayList<int[]> solution) {
        this.solution = solution;
        draw();
    }
    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }
    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }
    public String imageFileNameFinalProperty() {
        return imageFileNameFinal.get();
    }

    public void setImageFileNameFinal(String imageFileNameFinal) {
        this.imageFileNameFinal.set(imageFileNameFinal);
    }

    // WALL
    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    // PLAYER
    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    // FINAL
    public String getImageFileNameFinal() {
        return imageFileNameFinal.get();
    }

    public void drawMaze(int[][] maze) {
        this.solution = null;
        this.maze = maze;
        draw();
    }

    private void draw() {
        double canvasHeight = getHeight() - 2;
        double canvasWidth = getWidth() - 2;
        GraphicsContext graphicsContext = this.getGraphicsContext2D();
        graphicsContext.clearRect(1, 1, canvasWidth, canvasHeight);
        if(maze != null){
            int rows = maze.length;
            int cols = maze[0].length;
            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;
            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if(solution != null)
                drawSolutionPath(graphicsContext, cellHeight, cellWidth);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }
    public void drawSolutionPath(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        graphicsContext.setFill(Color.GREEN);
        for(int[] coordinates : solution){
            double y = coordinates[0] * cellWidth;
            double x = coordinates[1] * cellHeight;
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        }
    }
    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(javafx.scene.paint.Color.RED);

        javafx.scene.image.Image wallImage = null; javafx.scene.image.Image finalImage = null;
        try{
            wallImage = new javafx.scene.image.Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }
        try{
            finalImage = new javafx.scene.image.Image(new FileInputStream(getImageFileNameFinal()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no final image file");
        }
        if (maze == null)
            return;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                if(maze[i][j] == 1) {
                    if (wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
                    if(i == rows - 1 && j == cols - 1 && finalImage != null)
                        graphicsContext.drawImage(finalImage, x, y, cellWidth, cellHeight);
            }
        }
    }
    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth + 1;
        double y = getPlayerRow() * cellHeight + 1;
        graphicsContext.setFill(Color.GREEN);

        javafx.scene.image.Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }
}
