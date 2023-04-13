package algorithms.maze3D;
import algorithms.search.AState;
import algorithms.search.ISearchable;
import java.util.ArrayList;

/**
 * SearchableMaze3D
 * making an adaptation of a maze for solving him by searching a path */

public class SearchableMaze3D implements ISearchable{
    Maze3D maze;
    public SearchableMaze3D(Maze3D maze){
        this.maze = maze;
    }
    public AState getStartState(){
        return new Maze3DState(0, 0, 0, 0);
    }
    public AState getEndState(){
        if(maze.getDepth() <= 0 || maze.getRow() <= 0 || maze.getColumn() <= 0) //if the sizes cannot be handled we will return null
            return null;
        return new Maze3DState(0,maze.getRow() - 1, maze.getColumn() - 1, 0);
    }
    /**
     * returning all the possible neighbors of a specific state
     * @param s specific state of a maze
     * @return all the valid neighbors*/
    public ArrayList<AState> getAllPossibleStates(AState s){
        ArrayList<AState> successors = new ArrayList<>();
        if(s != null) {
            int row = ((Maze3DState) s).getRow();
            int column = ((Maze3DState) s).getColumn();
            int depth = ((Maze3DState) s).getDepth();
            addArray(depth, row + 1, column, successors, s, 10);
            addArray(depth, row - 1, column, successors, s, 10);
            addArray(depth, row, column + 1, successors, s, 10);
            addArray(depth, row, column - 1, successors, s, 10);
            addArray(depth + 1, row, column, successors, s, 10);
            addArray(depth - 1, row, column, successors, s, 10);
            return successors;
        }
        return successors;
    }
    /**
     * adding to a list all the valid neighbor cells that valid to a current state*/
    public void addArray(int depth, int row, int column, ArrayList<AState> successors, AState s, int cost){
        if (maze.containsPath(depth, row, column)){
            AState current = new Maze3DState(depth, row, column, cost);
            current.setCameFrom(s);
            current.addCost(s.getCost());
            successors.add(current);
        }
    }
}
