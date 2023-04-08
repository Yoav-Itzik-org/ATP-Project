package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int column;
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
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Position))
            return false;
        return other == this || (row == ((Position) other).getRowIndex() && column == ((Position) other).getColumnIndex());
    }
}
