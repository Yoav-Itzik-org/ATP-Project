package algorithms.mazeGenerators;
import java.util.Random;

/**
 * SimpleMazeGenerator - Creating a maze with random path
 */
public class SimpleMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns);
        Random rnd = new Random();
        int column_path = 0; // Make the exception path zero first to ensure that the start contains a path
        for (int row = 0; row < rows; row++) {
            if (rnd.nextBoolean()) // Choose the make wall row
                 setWallRow(maze, row, column_path);
            else
                column_path = ((columns != 0 ) ? rnd.nextInt(maze.getColumns()) : 0); // Change the column index path
        }
        setHorizontalPath(maze, maze.getRows() - 1, column_path); // Add path from the column index path to the end position (at the last row)
        makeRandomPaths(maze);
        return maze;
    }

    /**
     * Set walls in a given row
     * @param maze maze to add the walls to
     * @param row the row to add the rows to
     * @param column_path an exception cell which a wall will not be added (to make a path to the end)
     */
    private void setWallRow(Maze maze, int row, int column_path){
        for (int column = 0; column < maze.getColumns(); column++)
            if(column != column_path)
                maze.setWall(row, column);
    }

    /**
     * Add pathes in a maze from at a given row and a given column (to the end column of the row)
     * @param maze a maze to add the paths to
     * @param row a row index that the paths will be added to
     * @param start_column a column index that the row will start added paths from (to the end of the row)
     */
    private void setHorizontalPath(Maze maze, int row, int start_column){
        for (int column = start_column; column <  maze.getColumns(); column++)
            maze.setPath(row, column);
    }
}
