package Model;


import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.ServerStrategySolveSearchProblem;
import Server.ServerStrategyGenerateMaze;
import Server.Server;
import algorithms.search.Solution;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {
    public static Server mazeGeneratingServer;
    public static Server solveSearchProblemServer;
    private Maze maze;
    private int playerRow;
    private int playerCol;
    private Solution solution;
    private MyMazeGenerator generator;
    public MyModel() {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        maze = generator.generate(rows, cols);
        setChanged();
        notifyObservers("maze generated");
        movePlayer(0, 0);
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void updatePlayerLocation(Direction direction) {
        switch (direction) {
            case Up -> {
                if (canUp)
                    movePlayer(playerPosition.getRowIndex() - 1, playerPosition.getColumnIndex());
            }
            case Down -> {
                if (canDown)
                    movePlayer(playerPosition.getRowIndex() + 1, playerPosition.getColumnIndex());
            }
            case Left -> {
                if (canLeft)
                    movePlayer(playerPosition.getRowIndex(), playerPosition.getColumnIndex() - 1);
            }
            case Right -> {
                if (canRight)
                    movePlayer(playerPosition.getRowIndex(), playerPosition.getColumnIndex() + 1);
            }
        }
    }

    private void movePlayer(int row, int col){
        this.playerRow = row;
        this.playerCol = col;
        setChanged();
        notifyObservers("player moved");
    }
    public int getPlayerRow() {
        return playerRow;
    }
    public int getPlayerCol() {
        return playerCol;
    }
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }
    public void solveMaze() {
        solution = new Solution(new AState() {
            @Override
            public AState getCameFrom() {
                return super.getCameFrom();
            }
        });
        setChanged();
        notifyObservers("maze solved");
    }
    public Solution getSolution() {
        return solution;
    }
}
