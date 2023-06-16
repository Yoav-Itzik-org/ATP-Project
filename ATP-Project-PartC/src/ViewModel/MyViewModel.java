package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;

import algorithms.search.AState;
import algorithms.mazeGenerators.*;
import algorithms.search.*;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

public class MyViewModel extends Observable implements Observer {
    private IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); //Observe the Model for it's changes
    }
    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
    public int[][] getMaze(){
        Maze maze =  model.getMaze();
        int rows = maze.getRows(); int cols = maze.getColumns();
        int[][] mazeArray = new int[rows][cols];
        for(int row = 0; row < rows; ++row)
            for(int column = 0; column < cols; ++column) {
                mazeArray[row][column] = maze.containsPath(row, column) ? 0 : 1;
            }
        return mazeArray;
    }
    public int getPlayerRow(){
        return model.getPlayerRow();
    }
    public int getPlayerCol(){
        return model.getPlayerCol();
    }
    public void generateMaze(int rows, int cols){
        model.generateMaze(rows, cols);
    }
    public ArrayList<int[]> getSolution(){ //TODO
        ArrayList<int[]> solutionPath = new ArrayList<>();
        for(AState step : model.getSolution().getSolutionPath()) {
//            solutionPath.add(new int[]{((MazeState)step).getRow});
        }
        return solutionPath;
    }
}
