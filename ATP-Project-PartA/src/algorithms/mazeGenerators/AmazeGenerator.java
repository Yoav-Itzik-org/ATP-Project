package algorithms.mazeGenerators;

import java.util.Random;

public abstract class AmazeGenerator implements IMazeGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long start_time = System.currentTimeMillis();
        generate(rows, columns);
        long end_time = System.currentTimeMillis();
        return end_time - start_time;
    }
    public void makeRandomPaths(Maze maze){
        Random rnd = new Random();
        for(int row = 0; row < maze.getRows(); row++)
            for(int column = 0; column < maze.getColumns(); column++)
                if(rnd.nextBoolean())
                    maze.setPath(row, column);
    }
}
