package Model;


import Client.Client;
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
            Client client = new Client(InetAddress.getLocalHost(), 5400, (inFromServer, outToServer) -> {
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
        int row = playerPosition.getRowIndex();
        int col = playerPosition.getColumnIndex();
        boolean canUp = maze.containsPath(row - 1, col);
        boolean canDown = maze.containsPath(row + 1, col);
        boolean canLeft = maze.containsPath(row, col - 1);
        boolean canRight = maze.containsPath(row, col + 1);
        boolean canUpLeft = maze.containsPath(row - 1, col - 1);
        boolean canUpRight = maze.containsPath(row - 1, col + 1);
        boolean canDownLeft = maze.containsPath(row + 1, col - 1);
        boolean canDownRight = maze.containsPath(row + 1, col + 1);
        switch (direction) {
            case UP -> {
                if (canUp)
                    movePlayer(row - 1, col);
            }
            case DOWN -> {
                if (canDown)
                    movePlayer(row + 1, col);
            }
            case LEFT -> {
                if (canLeft)
                    movePlayer(row, col - 1);
            }
            case RIGHT -> {
                if (canRight)
                    movePlayer(row, col + 1);
            }
            case UP_LEFT -> {
                if(canUpLeft)
                    movePlayer(row - 1, col - 1);
            }
            case UP_RIGHT -> {
                if (canUpRight)
                    movePlayer(row - 1, col + 1);
            }
            case DOWN_LEFT -> {
                if(canDownLeft)
                    movePlayer(row + 1, col - 1);
            }
            case DOWN_RIGHT -> {
                if (canDownRight)
                    movePlayer( row + 1, col + 1);
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
            Client client = new Client(InetAddress.getLocalHost(), 5401, (inFromServer, outToServer) -> {
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
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
        setChanged();
        notifyObservers("maze solved");
    }
    public Solution getSolution() {
        solveMaze();
        return solution;
    }
    public void loadMaze(File mazeFile){
        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFile));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException var8) {
            var8.printStackTrace();
        }
        setChanged();
        notifyObservers("maze loaded");
    }
    public void saveMaze(File mazeFile){
        byte[] savedMazeBytes;
        try {
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFile));
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
