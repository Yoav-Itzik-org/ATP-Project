package algorithms.mazeGenerators;


import java.io.Serializable;

/**
 * Maze class
 * A maze presented by a two dimensions array (dimensions given at the constructor)
 * in the array a cell with 0 represent a path and 1 a wall
 * A maze also has starting and ending position - by default they are top left and bottom right
 */
public class Maze implements Serializable {
    private int[][] array;
    private int rows;
    private int columns;
    private Position start;
    private Position end;
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
    @Override
    public boolean equals(Object other){
        if(other == null || other.getClass() != this.getClass())
            return false;
        Maze otherMaze = (Maze) other;
        if(rows != otherMaze.getRows() || columns != otherMaze.getColumns())
            return false;
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++)
                if(containsPath(row, column) != otherMaze.containsPath(row, column))
                    return false;
        return true;
    }
    //Part B additions
    public Maze(byte[] bytes) {
        if (bytes == null) {
            this.array = null;
            this.rows = -1;
            this.columns = -1;
            this.start = null;
            this.end = null;
        }
        else {
            rows = byteDimensionToInteger(bytes, 0);
            columns = byteDimensionToInteger(bytes, 4);
            array = new int[rows][columns];
            int byteArrayIndex = 8;
            for (int row = 0; row < rows; row++)
                for (int coulmn = 0; coulmn < columns; coulmn++)
                    array[row][coulmn] = bytes[byteArrayIndex++];
            this.start = new Position(0, 0);
            this.end = new Position(rows - 1, columns - 1);
        }
    }
    public int byteDimensionToInteger(byte[] bytes, int startIndex){
        byte[] binaryArray = new byte[32];
        for(int byteIndex = 0; byteIndex < 4; byteIndex++)
            fillBinaryArray(binaryArray, byteIndex * 8, bytes[startIndex + byteIndex]);
        int result = 0;
        for(int bitIndex = 0; bitIndex < 32; bitIndex++)
            result += binaryArray[bitIndex] * Math.pow(2, bitIndex);
        return result;
    }
    public void fillBinaryArray(byte[] binaryArray, int byteOffset, int num){
        num = num & 0xff;
        for(int bitOffest = 0; bitOffest < 8; bitOffest++) {
            binaryArray[byteOffset + bitOffest] = (byte) (num % 2);
            num /= 2;
        }
    }
    public byte[] toByteArray(){
        if(array == null)
            return null;
        final int mazeSettingCount = 8;
        byte[] byteArray = new byte[columns * rows + mazeSettingCount];
        byte[][] dimensionsArray = new byte[][]{separateIntegerToBytes(rows), separateIntegerToBytes(columns)};
        for(int dimensionIndex = 0; dimensionIndex < 2; dimensionIndex++)
            System.arraycopy(dimensionsArray[dimensionIndex], 0, byteArray, dimensionIndex * 4, 4);
        int byteArrayIndex = 8;
        for(int row = 0; row < rows; row++)
            for(int column = 0; column < columns; column++)
                byteArray[byteArrayIndex++] = (byte) array[row][column];
        return byteArray;
    }
    public byte[] separateIntegerToBytes(int num){
        byte[] byteArray = new byte[4];
        byte[] binaryNum = decimalToBinary(num);
        for(int offsetCount = 0; offsetCount < 4; offsetCount++)
            byteArray[offsetCount] = binaryToByte(binaryNum, offsetCount * 8);
        return byteArray;
    }
    public byte[] decimalToBinary(int num){
        byte[] binaryArray = new byte[32];
        for (int i = 0; i < 32 ; i++){
            binaryArray[i] = (byte) (num % 2);
            num /= 2;
            if(num == 0)
                break;
        }
        return binaryArray;
    }
    public byte binaryToByte(byte[] bitArray, int offsetIndex){
        byte result = 0;
        for(int bitIndex = 0; bitIndex < 8; bitIndex++)
            result += bitArray[offsetIndex + bitIndex] * Math.pow(2, bitIndex);
        return result;
    }
}