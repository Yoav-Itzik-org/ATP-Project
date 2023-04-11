package algorithms.mazeGenerators;

public class Maze {
    final private int[][] array;
    final private int rows;
    final private int columns;
    final private Position start;
    final private Position end;
    public Maze(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        if(rows <= 0 || columns <= 0){
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
    public boolean isValidPosition(int row, int column){
        return !(!(0 <= row && row < rows) || !( 0 <= column && column < columns));
    }
    public void setPath(int row, int column){
        if(isValidPosition(row, column) && array != null)
            array[row][column] = 0;
    }
    public void setWall(int row, int column){
        if(isValidPosition(row, column) && array != null)
            array[row][column] = 1;
    }
    public Position getStartPosition(){return start;}
    public Position getGoalPosition(){return end;}
    public int getRows(){return rows;}
    public int getColumns(){return columns;}
    public boolean is_start(int row, int column){return start != null && row == 0 && column == 0;}
    public boolean is_end(int row, int column){ return end == null || (row == rows - 1 && column == columns - 1);}
    public boolean containsPath(int row, int column) {
        if(!isValidPosition(row, column) || array == null)
            return false;
        return array[row][column] == 0;
    }
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