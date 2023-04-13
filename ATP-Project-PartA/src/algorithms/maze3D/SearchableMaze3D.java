package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

/**
 * SearchableMaze3D
 * implement ISearchable and used for be able to searching on a 3Dmaze
 * The class only has one attribute to holds the relevant maze
 */

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
     * Getting all neighbor of a current state
     * In a 3D maze a state's neighbors considered all the surrounding positions (in straight lines)
     * ALl the neighbors has 10 cost
     * @param s a state to search its neighbors
     * @return list of all the neighbors
     */
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
     * Add a position to an array of neighbors if it contains valid path
     * @param depth depth index
     * @param row row index
     * @param column column index
     * @param successors list of all current found successors
     * @param s current state
     * @param cost the cost that the movement between the cells cost
     */
    private void addArray(int depth, int row, int column, ArrayList<AState> successors, AState s, int cost){
        if (maze.containsPath(depth, row, column)){
            AState current = new Maze3DState(depth, row, column, cost);
            current.setCameFrom(s);
            current.addCost(s.getCost());
            successors.add(current);
        }
    }
}
