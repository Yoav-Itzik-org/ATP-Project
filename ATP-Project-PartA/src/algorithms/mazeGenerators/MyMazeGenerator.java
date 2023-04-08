package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AmazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        for (int row = 0; row < rows; row++)
            Arrays.fill(maze.array[row], 1);
        maze.array[0][0] = 0;
        int row = 0;
        int column = 0;
        Random rnd = new Random();
        do{
            if(column == columns - 1 || (rnd.nextBoolean() && row < rows - 1)) // Go South
                row++;
            else
                column++;
            maze.array[row][column] = 0;
        }while (!maze.is_end(row, column));
        return maze;
    }
}
