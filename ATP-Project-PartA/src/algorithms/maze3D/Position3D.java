package algorithms.maze3D;

/**
 * Position3D class
 * A position3D presented by coordinates - depth, row and column indexes
 */
public class Position3D {
    final private int depth;
    final private int row;
    final private int column;
    public Position3D(int depth, int row, int column){
        this.depth = depth;
        this.row = row;
        this.column = column;
    }
    public int getDepthIndex(){return this.depth;}
    public int getRowIndex(){return this.row;}
    public int getColumnIndex(){return this.column;}
    public String toString(){
        return String.format("{%d,%d,%d}",depth, row, column);
    }
    /**
     * Overriding equals - To position considered equal if their coordinates are the same
     * @param other other 3D position to compare to the current one
     * @return if the two are equal
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Position3D))
            return false;
        return other == this || (row == ((Position3D) other).getRowIndex() && column == ((Position3D) other).getColumnIndex()
                && depth == ((Position3D) other).getDepthIndex());
    }
}
