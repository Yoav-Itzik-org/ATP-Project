package algorithms.mazeGenerators;

/**
 * Maze Generator interface - authorize that the generators will generate a maze and will be able to measure the time doing so
 */
public interface IMazeGenerator {
    /**
     * Generating a maze in a given dimensions
     * @param rows amount of rows
     * @param columns amount of columns
     * @return a maze matching the dimensions
     */
    Maze generate(int rows, int columns);

    /**
     * Measure a time of creation of a maze
     * @param rows amount of rows
     * @param columns amount of columns
     * @return the time took the maze to be created
     */
    long measureAlgorithmTimeMillis(int rows, int columns);
}