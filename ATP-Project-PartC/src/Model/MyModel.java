package Model;


import Client.Client;
import Client.IClientStrategy;
import IO.MyCompressorOutputStream;
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
    private Position playerPosition;
    private Solution solution;
    public MyModel() {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }

    @Override
    public void generateMaze(int rows, int cols) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{50, 50};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[2508];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (Exception var1) {
            var1.printStackTrace();
        }
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
        boolean canUp = playerPosition.getRowIndex() - 1 > 0;
        boolean canDown = playerPosition.getRowIndex() + 1 < maze.getRows() - 1;
        boolean canLeft =playerPosition.getColumnIndex() - 1 > 0;
        boolean canRight = playerPosition.getColumnIndex() + 1 < maze.getColumns() - 1;
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
            case UpLeft -> {
                if(canUp && canLeft)
                    movePlayer(playerPosition.getRowIndex() - 1, playerPosition.getColumnIndex() - 1);
            }
            case UpRight -> {
                if (canUp && canRight)
                    movePlayer(playerPosition.getRowIndex() - 1, playerPosition.getColumnIndex() + 1);
            }
            case DownLeft -> {
                if(canDown && canLeft)
                    movePlayer(playerPosition.getRowIndex() + 1, playerPosition.getColumnIndex() - 1);
            }
            case DownRight -> {
                if (canDown && canRight)
                    movePlayer((playerPosition.getRowIndex() + 1), playerPosition.getColumnIndex() + 1);
            }
        }
    }

    private void movePlayer(int row, int col){
        playerPosition = new Position(row, col);
        setChanged();
        notifyObservers("player moved");
    }
    public int getPlayerRow() {return playerPosition.getRowIndex();}
    public int getPlayerCol() {
        return playerPosition.getColumnIndex();
    }
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }
    public void solveMaze() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        Maze maze = mg.generate(50, 50);
                        maze.print();
                        toServer.writeObject(maze);
                        toServer.flush();
                        solution = (Solution)fromServer.readObject();
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
        setChanged();
        notifyObservers("maze solved");
    }
    public Solution getSolution() {
        return solution;
    }
    public void loadMaze(String path){
        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(path));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }
        setChanged();
        notifyObservers("maze loaded");
    }
    public void saveMaze(String path){
        byte[] savedMazeBytes;
        try {
            InputStream in = new MyDecompressorInputStream(new FileInputStream(path));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException var7) {
            var7.printStackTrace();
            savedMazeBytes = null;
        }
        maze = new Maze(savedMazeBytes);
        setChanged();
        notifyObservers("maze saved");
    }
}
