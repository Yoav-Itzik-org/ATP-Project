package algorithms.search;
import algorithms.mazeGenerators.Maze;
import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    Maze maze;
    public SearchableMaze(Maze maze){
        this.maze = maze;
    }
    public AState getStartState(){
        return new MazeState(0, 0, 0);
    }
    public AState getEndState(){
//        TO DO
        return new MazeState(maze.getRows() - 1, maze.getColumns() - 1, 0);
    }
    public ArrayList<AState> getAllSuccessors(AState s){
        ArrayList<AState> successors = new ArrayList<>();
        if(s == null)
            return successors;
        int row = ((MazeState) s).getRow();
        int column = ((MazeState) s).getColumn();
        boolean up = addArray(row - 1, column , successors, s, 10);
        boolean right = addArray(row, column + 1 , successors, s,  10);
        boolean down = addArray(row + 1, column , successors, s, 10);
        boolean left = addArray(row , column - 1 , successors, s,  10);
        if(up || right)
            addArray(row - 1, column + 1, successors, s,  15);
        if(down || right)
            addArray(row + 1, column + 1, successors, s,15);
        if(down || left)
            addArray(row + 1, column - 1, successors, s,15);
        if(up || left)
            addArray(row - 1, column - 1, successors, s,15);
        return successors;
//        boolean up = maze.containsPath(row - 1, column);
//        boolean down = maze.containsPath(row + 1, column);
//        boolean right = maze.containsPath(row, column + 1);
//        boolean left = maze.containsPath(row, column - 1);
//        if((up || right) && maze.containsPath(row - 1, column + 1))
//            successors.add(new MazeState(row - 1, column + 1, 15));
//        if((down || right) && maze.containsPath(row + 1, column + 1))
//            successors.add(new MazeState(row + 1, column + 1, 15));
//        if((down || left) && maze.containsPath(row + 1, column - 1))
//            successors.add(new MazeState(row + 1, column - 1, 15));
//        if((up || left) && maze.containsPath(row - 1, column - 1))
//            successors.add(new MazeState(row - 1, column - 1, 15));
//        if(up)
//            successors.add(new MazeState(row - 1, column, 10));
//        if(right)
//            successors.add(new MazeState(row, column + 1, 10));
//        if(down)
//            successors.add(new MazeState(row + 1, column, 10));
//        if(left)
//            successors.add(new MazeState(row, column - 1, 10));
//        return successors;
    }

    public boolean addArray(int row, int column, ArrayList<AState> successors, AState s, int cost){
        if (!maze.containsPath(row, column))
            return false;
        AState current = new MazeState(row, column, cost);
        current.setCameFrom(s); current.addCost(s.getCost());
        successors.add(current);
        return true;
    }
}
