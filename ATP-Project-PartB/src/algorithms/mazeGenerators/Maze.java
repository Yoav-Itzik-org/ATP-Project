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

    /**
     * Override equals
     * to mazes are equal if they have the same dimensions and same values in them and same start and goal positions
     * @param other other maze
     * @return true if equal, false if not
     */
    @Override
    public boolean equals(Object other){
        if(other == null || other.getClass() != this.getClass())
            return false;
        Maze otherMaze = (Maze) other;
        if(rows != otherMaze.getRows() || columns != otherMaze.getColumns())
            return false;
        if(!start.equals(otherMaze.getStartPosition()) || !end.equals(otherMaze.getGoalPosition()))
            return false;
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++)
                if(containsPath(row, column) != otherMaze.containsPath(row, column))
                    return false;
        return true;
    }

    //Part B additions

    /**
     * Constructor from byte array
     * @param bytes the array to construct to
     */
    public Maze(byte[] bytes) {
        if (bytes == null) { // nothing the build from
            this.array = null;
            this.rows = -1;
            this.columns = -1;
            this.start = null;
            this.end = null;
        }
        else {
            // get dimensions
            rows = byteDimensionToInteger(bytes, 0);
            columns = byteDimensionToInteger(bytes, 4);
            // start build the maze
            array = new int[rows][columns];
            int byteArrayIndex = 8; // start of maze cells (first indexes are for dimensions
            for (int row = 0; row < rows; row++)
                for (int column = 0; column < columns; column++)
                    array[row][column] = bytes[byteArrayIndex++];
            this.start = new Position(0, 0);
            this.end = new Position(rows - 1, columns - 1);
        }
    }

    /**
     * Get dimension value from bytes array (4 bytes only)
     * @param bytes the byte array
     * @param startIndex the start of the byte array
     * @return the integer value correspond to the bytes array
     */
    public int byteDimensionToInteger(byte[] bytes, int startIndex){
        byte[] binaryArray = new byte[32];
        // Calculate all the bits
        for(int byteIndex = 0; byteIndex < 4; byteIndex++)
            fillBinaryArray(binaryArray, byteIndex * 8, bytes[startIndex + byteIndex]);
        // Calculate the decimal value
        int result = 0;
        for(int bitIndex = 0; bitIndex < 32; bitIndex++)
            result += binaryArray[bitIndex] * Math.pow(2, bitIndex);
        return result;
    }

    /**
     * Fill a binary array with bits turned on
     * @param binaryArray to fill to
     * @param byteOffset where to start fill
     * @param num the byte which needs to be inserted as bits
     */
    public void fillBinaryArray(byte[] binaryArray, int byteOffset, int num){
        num = num & 0xff; // Unsigned value
        // Calculate binary value and insert to the relevant cells
        for(int bitOffest = 0; bitOffest < 8; bitOffest++) {
            binaryArray[byteOffset + bitOffest] = (byte) (num % 2);
            num /= 2;
        }
    }

    /**
     * Convert maze to byte array
     * [4 bytes to row dimension, 4 bytes to column dimension, bytes cells]
     * @return the relevant byte array
     */
    public byte[] toByteArray(){
        if(array == null) // No maze to calculate
            return null;
        final int mazeSettingCount = 8; // where the cells start
        byte[] byteArray = new byte[columns * rows + mazeSettingCount]; // amount of cells in the conversion
        // Get dimensions as seperated bytes and fill them to first 8 cells
        byte[][] dimensionsArray = new byte[][]{separateIntegerToBytes(rows), separateIntegerToBytes(columns)};
        for(int dimensionIndex = 0; dimensionIndex < 2; dimensionIndex++)
            System.arraycopy(dimensionsArray[dimensionIndex], 0, byteArray, dimensionIndex * 4, 4);
        // Start fill the maze cells
        int byteArrayIndex = mazeSettingCount;
        for(int row = 0; row < rows; row++)
            for(int column = 0; column < columns; column++)
                byteArray[byteArrayIndex++] = (byte) array[row][column];
        return byteArray;
    }

    /**
     * Separate a number to 4 bytes array
     * @param num integer needs to be seperated
     * @return a byte array of the bytes of the number (seperated)
     */
    public byte[] separateIntegerToBytes(int num){
        byte[] byteArray = new byte[4];
        byte[] binaryNum = decimalToBinary(num); // Get the binary value of the bytes
        // fill the array with the correspond decimal value of each 8 bits
        for(int offsetCount = 0; offsetCount < 4; offsetCount++)
            byteArray[offsetCount] = binaryToByte(binaryNum, offsetCount * 8);
        return byteArray;
    }

    /**
     * Gets binary array from number
     * @param num a given number needs to be converted
     * @return a byte of the bits of the number represent the binary value
     */
    public byte[] decimalToBinary(int num){
        byte[] binaryArray = new byte[32];
        // Get bits values
        for (int i = 0; i < 32 ; i++){
            binaryArray[i] = (byte) (num % 2);
            num /= 2;
            if(num == 0)
                break;
        }
        return binaryArray;
    }

    /**
     * Gets byte decimal value of bit array
     * @param bitArray the binary representation
     * @param offsetIndex where the binary number start
     * @return the decimal value
     */
    public byte binaryToByte(byte[] bitArray, int offsetIndex){
        byte result = 0;
        for(int bitIndex = 0; bitIndex < 8; bitIndex++)
            result += bitArray[offsetIndex + bitIndex] * Math.pow(2, bitIndex);
        return result;
    }
}