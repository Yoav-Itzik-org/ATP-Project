package algorithms.mazeGenerators;
import java.util.*;

public class Maze {
    private int[][] array;
    private int rows;
    private int columns;
    private Position start;
    private Position end;
    public Maze(int rows, int columns){
        this.array = new int[rows][columns];
        this.rows = rows;
        this.columns = columns;
        this.start = new Position(0, 0);
        this.end = new Position(0, 0);
    }
    public void setPath(int row, int column){array[row][column] = 0;}
    public void setWall(int row, int column){array[row][column] = 1;}
    public Position getStartPosition(){return start;}
    public Position getGoalPosition(){return end;}
    public boolean is_start(int row, int column){return row == 0 && column == 0;}
    public boolean is_end(int row, int column){return row == rows - 1 && column == columns - 1;}
    public void print() {
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