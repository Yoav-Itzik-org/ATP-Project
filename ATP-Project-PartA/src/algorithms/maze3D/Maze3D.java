package algorithms.maze3D;

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
        if(depth <= 0 || row <= 0 || column <= 0){
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
    public int[][][] getMap(){return this.array;}
    public Position3D getStartPosition(){return this.start;}
    public Position3D getGoalPosition(){return this.end;}
    public boolean isValidPosition(int depth, int row, int column){
        return !(!(0 <= row && row < this.row) || !( 0 <= column && column < this.column) || !( 0 <= depth && depth < this.depth));
    }
    public void setPath(int depth, int row, int column){
        if(isValidPosition(depth, row, column) && array != null)
            array[depth][row][column] = 0;
    }
    public void setWall(int depth, int row, int column){
        if(isValidPosition(depth, row, column) && array != null)
            array[depth][row][column] = 1;
    }
    public boolean is_start(int depth, int row, int column){return start != null && depth == 0 && row == 0 && column == 0;}
    public boolean is_end(int depth, int row, int column){ return end == null || (depth == 0 && row == this.row - 1 && column == this.column - 1);}
    public boolean containsPath(int depth, int row, int column) {
        if(!isValidPosition(depth, row, column) || array == null)
            return false;
        return array[depth][row][column] == 0;
    }
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
    public int getDepth(){return depth;}
    public int getRow(){return row;}
    public int getColumn(){return column;}
}