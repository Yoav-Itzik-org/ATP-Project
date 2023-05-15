package algorithms.maze3D;
import algorithms.search.AState;
/**
 * Maze3DState
 * the class represent a position in a specific indexes of the 3D maze */
public class Maze3DState extends AState {
    final private Position3D position;
    public Maze3DState(int depth, int row, int column, double cost){
        position = new Position3D(depth, row, column);
        setCost(cost);
    }
    /**
     * Overriding equals - To Maze3DState considered equal if their coordinates are the same
     * @param other other Maze3DState to compare to the current one
     * @return if the two are equal
     */
    @Override
    public boolean equals(Object other){
        if(!(other instanceof Maze3DState))
            return false;
        return other == this || (((Maze3DState)other).getRow() == getRow() && ((Maze3DState)other).getColumn() == getColumn()
                && ((Maze3DState)other).getDepth() == getDepth());
    }
    protected int getDepth() {return position.getDepthIndex();}
    protected int getRow() {return position.getRowIndex();}
    protected int getColumn() {return position.getColumnIndex();}
    public String toString(){
        return position.toString();
    }
}
