package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AmazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        for (int row = 0; row < rows; row++)
            for(int column = 0; column < columns; column++)
                maze.setWall(row, column);
        maze.setPath(0, 0);
        int row = 0;
        int column = 0;
        Random rnd = new Random();
        do{
            if(column == columns - 1 || (rnd.nextBoolean() && row < rows - 1)) // Go South
                row++;
            else
                column++;
            maze.setPath(row, column);
        }while (!maze.is_end(row, column));
        makeRandomPaths(maze);
        return maze;
    }
}
