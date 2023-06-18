package Model;

import java.io.File;
import java.util.Observer;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface IModel {
    void loadMaze(File mazeFile);
    void saveMaze(File mazeFile);
    void generateMaze(int rows, int cols);
    Maze getMaze();
    void updatePlayerLocation(Direction direction);
    int getPlayerRow();
    int getPlayerCol();
    void assignObserver(Observer o);
    void solveMaze();
    Solution getSolution();
    void setMaze(Maze maze);
    void isSolved();
    boolean containsPath(int row, int col);
    void movePlayer(int row, int col);
}
