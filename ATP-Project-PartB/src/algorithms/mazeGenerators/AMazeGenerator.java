package algorithms.mazeGenerators;
import java.util.Random;

/**
 * Maze Generator abstract class
 */
public abstract class AMazeGenerator implements IMazeGenerator {
    /**
     * measuring the time that takes to generate a maze
     * by given sizes
     * @param rows size of row
     * @param columns size of column
     * @return the measured time
     * */
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long start_time = System.currentTimeMillis();
        generate(rows, columns);
        long end_time = System.currentTimeMillis();
        return end_time - start_time;
    }

    /**
     * add some random paths in a given maze
     * @param maze a maze to add walls to
     */
    protected void makeRandomPaths(Maze maze){
        Random rnd = new Random();
        for(int row = 0; row < maze.getRows(); row++)
            for(int column = 0; column < maze.getColumns(); column++)
                if(rnd.nextBoolean()) // add a random path
                    maze.setPath(row, column);
    }
}
