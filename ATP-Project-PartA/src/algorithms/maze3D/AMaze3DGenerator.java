package algorithms.maze3D;

import java.util.Random;

public abstract class AMaze3DGenerator implements IMazeGenerator3D {
    @Override
    public long measureAlgorithmTimeMillis(int depth, int row, int column) {
        long start_time = System.currentTimeMillis();
        generate(depth, row, column);
        long end_time = System.currentTimeMillis();
        return end_time - start_time;
    }
    public void makeRandomPaths(Maze3D maze){
        Random rnd = new Random();
        for(int d = 0; d < maze.getDepth(); d++)
            for(int r = 0; r < maze.getRow(); r++)
                for (int c = 0; c < maze.getColumn(); c++)
                    if(rnd.nextBoolean())
                        maze.setPath(d, r, c);
    }
}