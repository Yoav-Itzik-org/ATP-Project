package Model;

import algorithms.search.AState;
import algorithms.search.Solution;
import algorithms.mazeGenerators.*;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {
    private Maze maze;
    private int playerRow;
    private int playerCol;
    private Solution solution;
    private MyMazeGenerator generator;
    public MyModel() {
        generator = new MyMazeGenerator();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        maze = generator.generate(rows, cols);
        setChanged();
        notifyObservers("maze generated");
        movePlayer(0, 0);
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void updatePlayerLocation(Direction direction) {
        switch (direction) {
            case Up -> {
                if ((playerRow > 0) && (playerRow - 1 > 0))
                    movePlayer(playerRow - 1, playerCol);
            }
            case Down -> {
                if ((playerRow < maze.getRows() - 1) && (playerRow + 1 < maze.getRows() - 1))
                    movePlayer(playerRow + 1, playerCol);
            }
            case Left -> {
                if ((playerCol > 0) && (playerCol - 1 > 0))
                    movePlayer(playerRow, playerCol - 1);
            }
            case Right -> {
                if ((playerCol < maze.getColumns() - 1) && (playerCol + 1 < maze.getColumns() - 1))
                    movePlayer(playerRow, playerCol + 1);
            }
        }
    }

    private void movePlayer(int row, int col){
        this.playerRow = row;
        this.playerCol = col;
        setChanged();
        notifyObservers("player moved");
    }
    public int getPlayerRow() {
        return playerRow;
    }
    public int getPlayerCol() {
        return playerCol;
    }
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }
    public void solveMaze() {
        solution = new Solution(new AState() {
            @Override
            public AState getCameFrom() {
                return super.getCameFrom();
            }
        });
        setChanged();
        notifyObservers("maze solved");
    }
    public Solution getSolution() {
        return solution;
    }
}
