package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AmazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        Random rand = new Random();
        Maze maze = new Maze(rows, columns);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (!((i == 0 && j == 0) || (i == rows - 1 && j == columns - 1)))
                    maze.array[i][j] = rand.nextInt(2);
        return maze;
    }
}
