package algorithms.maze3D;

import java.util.Random;
/**
 * 3D Maze Generator abstract class
 */
public abstract class AMaze3DGenerator implements IMaze3DGenerator {

    /**
     * measuring the time that takes to generate a 3D maze
     * by given sizes
     * @param depth size of depth
     * @param row size of row
     * @param column size of column
     * @return the measured time
     * */
    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long start_time = System.currentTimeMillis();
        generate(depth, row, column);
        long end_time = System.currentTimeMillis();
        return end_time - start_time;
    }
    /**
     * add some random paths in a given 3D maze
     * @param maze a 3D maze to add walls to
     */
    protected void makeRandomPaths(Maze3D maze){
        Random rnd = new Random();
        for(int d = 0; d < maze.getDepth(); d++)
            for(int r = 0; r < maze.getRow(); r++)
                for (int c = 0; c < maze.getColumn(); c++)
                    if(rnd.nextBoolean())
                        maze.setPath(d, r, c);
    }
}