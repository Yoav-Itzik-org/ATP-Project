package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AmazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        Random rnd = new Random();
        Maze maze = new Maze(rows, columns);
        int column_path = 0;
        for (int row = 0; row < rows; row++) {
            if (rnd.nextBoolean())
                for (int column = 0; column < columns; column++)
                    setWallRow(maze, row, column_path);
            else
                column_path = rnd.nextInt(maze.getColumns());
        }
        setHorizontalPath(maze, maze.getRows() - 1, column_path);
        makeRandomPaths(maze);
        return maze;
    }

    public void setWallRow(Maze maze, int row, int column_path){
        for (int column = 0; column < maze.getColumns(); column++)
            if(column != column_path)
                maze.setWall(row, column);
    }
    public void setHorizontalPath(Maze maze, int row, int start_column){
        for (int column = start_column; column <  maze.getColumns(); column++)
            maze.setPath(row, column);
    }
}
