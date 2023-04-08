package algorithms.mazeGenerators;

public abstract class AmazeGenerator implements IMazeGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long start_time = System.currentTimeMillis();
        generate(rows, columns);
        long end_time = System.currentTimeMillis();
        return end_time - start_time;
    }
}
