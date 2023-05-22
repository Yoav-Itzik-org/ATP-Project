package algorithms.mazeGenerators;

import java.util.Random;

/**
 * MyMazeGenerator - generating a maze using Binary tree algorithm - always go in a "beneficial" direction
 */
public class MyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        // Make the map full of walls
        for (int row = 0; row < rows; row++)
            for(int column = 0; column < columns; column++)
                maze.setWall(row, column);
        // Start going to the target location (end position) and make path in every step
        maze.setPath(0, 0);
        int row = 0;
        int column = 0;
        Random rnd = new Random();
        do{
            // Choose if to go right or down
            if(column == columns - 1 || (rnd.nextBoolean() && row < rows - 1))  // If chosen and possible to go down
                row++;
            else
                column++;
            maze.setPath(row, column); // make path in the process
        }while (!maze.is_end(row, column));
        return maze;
    }
}
