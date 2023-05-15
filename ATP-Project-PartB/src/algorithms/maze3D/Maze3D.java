package algorithms.maze3D;
/**
 * Maze3D class
 * A maze presented by a three dimensions array (dimensions given at the constructor)
 * in the array a cell with 0 represent a path and 1 a wall
 * A maze also has starting and ending position - by default they are top left and bottom right -
 * both in the first depth
 */

public class Maze3D {
    final private int[][][] array;
    final private int depth;
    final private int row;
    final private int column;
    final private Position3D start;
    final private Position3D end;
    public Maze3D(int depth, int row, int column){
        this.depth = depth;
        this.row = row;
        this.column = column;
        if(depth <= 0 || row <= 0 || column <= 0){// Cannot create an array with invalid size of one of the dimensions
            this.array = null;
            this.start = null;
            this.end = null;
        }
        else {
            this.array = new int[depth][row][column];
            this.start = new Position3D(0,0, 0);
            this.end = new Position3D(0,row - 1, column - 1);
        }
    }
    /**
     * Checking if indexes represent a valid spot in the maze (indexes in the range)
     * @param depth depth index
     * @param row row index
     * @param column column index
     * @return if the indexes are in range
     */
    private boolean isValidPosition(int depth, int row, int column){
        return !(!(0 <= row && row < this.row) || !( 0 <= column && column < this.column) || !( 0 <= depth && depth < this.depth));
    }
    /**
     * Set in a given indexes a path
     * @param depth depth index
     * @param row row index
     * @param column column index
     */
    protected void setPath(int depth, int row, int column){
        if(isValidPosition(depth, row, column) && array != null)
            array[depth][row][column] = 0;
    }
    /**
     * Set in a given indexes a wall
     * @param depth depth index
     * @param row row index
     * @param column column index
     */
    protected void setWall(int depth, int row, int column){
        if(isValidPosition(depth, row, column) && array != null)
            array[depth][row][column] = 1;
    }
    /**
     * Checking if an indexes represent start/end spot in the maze
     * @param depth depth index
     * @param row row index
     * @param column column index
     * @return if the indexes is start/end state
     */
    protected boolean is_start(int depth, int row, int column){return start != null && depth == 0 && row == 0 && column == 0;}
    protected boolean is_end(int depth, int row, int column){ return end == null || (depth == 0 && row == this.row - 1 && column == this.column - 1);}
    /**
     * Checking if a cell contains a path
     * @param depth depth index
     * @param row row index
     * @param column column index
     * @return if the cell contains a path
     */
    public boolean containsPath(int depth, int row, int column) {
        if(!isValidPosition(depth, row, column) || array == null)
            return false;
        return array[depth][row][column] == 0;
    }
    /**
     * Printing the maze, depth by row by row
     * Setting in the print output S and E and the start and end
     */
    public void print() {
        if(array == null)
            return;
        for(int r = 0; r < row; r++) {
            for (int d = 0; d < depth; d++) {
                for(int c = 0; c < column; c++)
                    if (is_start(d, r, c) || is_end(d, r, c))
                        System.out.print(is_start(d, r, c) ? "S" : "E");
                    else
                        System.out.print(array[d][r][c]);
                System.out.print('\t');
            }
            System.out.println();
        }
        System.out.println();
    }
    /**
     * the following functions are getting attributes because
     * they are privates
     */
    public int getDepth(){return depth;}
    public int getRow(){return row;}
    public int getColumn(){return column;}
    public int[][][] getMap(){return this.array;}
    public Position3D getStartPosition(){return this.start;}
    public Position3D getGoalPosition(){return this.end;}
}