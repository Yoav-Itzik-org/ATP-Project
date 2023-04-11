package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState{
    final private Position position;
    public MazeState(int row, int column, double cost){
        position = new Position(row, column);
        setCost(cost);
    }
    public int getRow() {return position.getRowIndex();}
    public int getColumn() {return position.getColumnIndex();}
    @Override
    public boolean equals(Object other){
        if(!(other instanceof MazeState))
            return false;
        return other == this || (((MazeState)other).getRow() == getRow() && ((MazeState)other).getColumn() == getColumn());
    }
    public String toString(){
        return position.toString();
    }
}
