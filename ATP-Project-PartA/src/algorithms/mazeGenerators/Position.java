package algorithms.mazeGenerators;

public class Position {
    int row;
    int column;
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
        return other == this || (row == ((Position)other).row && column == ((Position)other).column);
    }
}
