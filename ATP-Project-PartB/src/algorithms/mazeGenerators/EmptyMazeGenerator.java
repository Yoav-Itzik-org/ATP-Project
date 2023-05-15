package algorithms.mazeGenerators;

/**
 * Empty maze generator - create a maze with no walls
 */
public class EmptyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rows, int columns) {
        return new Maze(rows, columns);
    }
}
