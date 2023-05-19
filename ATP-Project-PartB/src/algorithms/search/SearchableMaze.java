package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

/**
 * SearchableMaze - implement ISearchable and used for be able to searching on a maze
 * The class only has one attribute to holds the relevant maze
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;
    public SearchableMaze(Maze maze){this.maze = maze;}
    public AState getStartState(){return new MazeState(0, 0, 0);}
    public AState getEndState(){
        if(maze.getRows() <= 0 || maze.getColumns() <= 0) // if the maze isn't empty
            return null;
        return new MazeState(maze.getRows() - 1, maze.getColumns() - 1, 0);
    }
    /**
     * Getting all neighbor of a state
     * In a 2D maze a state's neighbors considered all the surrounding positions (in straight lines)
     * and all the diagonals that has paths to (the cell has path and there's a way to it including straight cell)
     * ALl the straight neighbors has 10 additional cost and all the diagonal 15
     * @param s a state to search its neighbors
     * @return list of all the neighbors
     */
    public ArrayList<AState> getAllPossibleStates(AState s){
        ArrayList<AState> successors = new ArrayList<>();
        if(s != null) {
            // Get indexes of the current cell
            int row = ((MazeState) s).getRow();
            int column = ((MazeState) s).getColumn();
            // Try to add the straight neighbors
            boolean up = addArray(row - 1, column, successors, s, 10);
            boolean right = addArray(row, column + 1, successors, s, 10);
            boolean down = addArray(row + 1, column, successors, s, 10);
            boolean left = addArray(row, column - 1, successors, s, 10);
            // Try to add the diagonals
            if (up || right)
                addArray(row - 1, column + 1, successors, s, 15);
            if (down || right)
                addArray(row + 1, column + 1, successors, s, 15);
            if (down || left)
                addArray(row + 1, column - 1, successors, s, 15);
            if (up || left)
                addArray(row - 1, column - 1, successors, s, 15);
        }
        return successors;
    }

    /**
     * Add a position to an array of neighbors if it contains valid path
     * @param row row index
     * @param column column index
     * @param successors list of all current found successors
     * @param s current state
     * @param cost the cost that the movement between the cells cost
     * @return if the cell added tot the array
     */
    private boolean addArray(int row, int column, ArrayList<AState> successors, AState s, int cost){
        if (!maze.containsPath(row, column))
            return false;
        AState newState = new MazeState(row, column, cost);
        // Set information about the new state - set "pi" to the current and add the cost of the movement to it
        newState.setCameFrom(s); newState.addCost(s.getCost());
        successors.add(newState);
        return true;
    }
}
