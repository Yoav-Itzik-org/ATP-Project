package algorithms.maze3D;
/**
 * 3D Maze Generator interface - authorize that the generators will generate a 3D maze and will be able to measure the time doing so
 */
public interface IMaze3DGenerator {
    /**
     * Generating a 3D maze in a given dimensions
     * @param depth amount of depths
     * @param row amount of row
     * @param column amount of column
     * @return a 3D maze matching the dimensions
     */
    Maze3D generate(int depth, int row, int column);
    /**
     * Measure a time of creation of a 3D maze
     * @param depth amount of depths
     * @param row amount of rows
     * @param column amount of columns
     * @return the time took the maze to be created
     */
    long measureAlgorithmTimeMillis(int depth, int row, int column);
}