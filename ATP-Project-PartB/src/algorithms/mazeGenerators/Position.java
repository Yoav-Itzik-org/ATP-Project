package algorithms.mazeGenerators;

/**
 * Position class
 * A position presented by coordinates - row and column indexes
 */
public class Position {
    final private int row;
    final private int column;
    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }
    public int getRowIndex() {
        return row;
    }
    public int getColumnIndex() {
        return column;
    }
    public String toString(){
        return String.format("{%d,%d}", row, column);
    }

    /**
     * Overriding equals - To position considered equal if their coordinates are the same
     * @param other other position to compare to the current one
     * @return if the two are equal
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Position))
            return false;
        return other == this || (row == ((Position) other).getRowIndex() && column == ((Position) other).getColumnIndex());
    }
}
