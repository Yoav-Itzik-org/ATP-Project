package algorithms.mazeGenerators;

/**
 * Maze class
 * A maze presented by a two dimensions array (dimensions given at the constructor)
 * in the array a cell with 0 represent a path and 1 a wall
 * A maze also has starting and ending position - by default they are top left and bottom right
 */
public class Maze {
    final private int[][] array;
    final private int rows;
    final private int columns;
    final private Position start;
    final private Position end;
    public Maze(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        if(rows <= 0 || columns <= 0){ // Cannot create an array with invalid size of one of the dimensions
            this.array = null;
            this.start = null;
            this.end = null;
        }
        else {
            this.array = new int[rows][columns];
            this.start = new Position(0, 0);
            this.end = new Position(rows - 1, columns - 1);
        }
    }

    /**
     * Checking if indexes represent a valid spot in the maze (indexes in the range)
     * @param row row index
     * @param column column index
     * @return if the indexes are in range
     */
    private boolean isValidPosition(int row, int column){
        return !(!(0 <= row && row < rows) || !( 0 <= column && column < columns));
    }

    /**
     * Set in a given indexes a path
     * @param row row index
     * @param column column index
     */
    protected void setPath(int row, int column){
        if(isValidPosition(row, column) && array != null)   // if the spot is valid and the maze is has been initialized
            array[row][column] = 0;
    }
    /**
     * Set in a given indexes a wall
     * @param row row index
     * @param column column index
     */
    protected void setWall(int row, int column){
        if(isValidPosition(row, column) && array != null)   // if the spot is valid and the maze is has been initialized
            array[row][column] = 1;
    }
    public Position getStartPosition(){return start;}
    public Position getGoalPosition(){return end;}
    public int getRows(){return rows;}
    public int getColumns(){return columns;}

    // Checking if an indexes represent an important spot in the maze
    protected boolean is_start(int row, int column){return start != null && row == 0 && column == 0;} // If the maze is empty every position isn't considered start
    protected boolean is_end(int row, int column){ return end == null || (row == rows - 1 && column == columns - 1);} // If the maze is empty every position considered end

    /**
     * Checking if a cell contains a path
     * @param row row index
     * @param column column index
     * @return if the cell contains a path
     */
    public boolean containsPath(int row, int column) {
        if(!isValidPosition(row, column) || array == null)   // if the spot is valid and the maze is has been initialized
            return false;
        // Checking the cell's value
        return array[row][column] == 0;
    }

    /**
     * Printing the maze, row by row
     * Setting in the print output S and E and the start and end
     */
    public void print() {
        if(array == null)
            return;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (is_start(row, column) || is_end(row, column))
                    System.out.print(is_start(row, column) ? "S" : "E");
                else
                    System.out.print(array[row][column]);
            }
            System.out.println();
        }
    }
}