package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    public SearchableMaze(Maze maze){
//        TO DO
    }
    public AState getStartState(){
//        TO DO
        return new MazeState();
    }
    public AState getEndState(){
//        TO DO
        return new MazeState();
    }
    public ArrayList<AState> getAllSuccesors(AState s){
//        TO DO
        return new ArrayList<>();
    }
}
