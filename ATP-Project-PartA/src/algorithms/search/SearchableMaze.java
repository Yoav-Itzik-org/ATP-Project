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
    public ArrayList<AState> getAllSuccesors(AState s){
//        TO DO
        return new ArrayList<>();
    }
}
