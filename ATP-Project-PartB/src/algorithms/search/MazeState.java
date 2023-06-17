package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

/**
 * MazeState class. The maze state class extends the abstract AState class to represent a state while searching a maze.
 * The maze state adds a position attribute to represent the state's location in the maze
 */
public class MazeState extends AState implements Serializable {
    private Position position;
    public MazeState(int row, int column, double cost){
        position = new Position(row, column);
        setCost(cost);
    }
    public int getRow() {return position.getRowIndex();}
    public int getColumn() {return position.getColumnIndex();}

    /**
     * Overriding equals. two MazeStates considered equal if their position's coordinates are equal
     * @param other an MazeState to compare to
     * @return if the two MazeStates are equal
     */
    @Override
    public boolean equals(Object other){
        return other instanceof MazeState && (other == this || position.equals(((MazeState) other).position));
    }
    public String toString(){return position.toString();}
}
