package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AmazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        return new Maze(rows, columns);
    }
}
