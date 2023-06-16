package Model;

import java.util.Observer;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public interface IModel {
    void generateMaze(int rows, int cols);
    Maze getMaze();
    void updatePlayerLocation(Direction direction);
    int getPlayerRow();
    int getPlayerCol();
    void assignObserver(Observer o);
    void solveMaze();
    Solution getSolution();
}
