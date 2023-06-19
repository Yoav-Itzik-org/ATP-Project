package Model;


import Client.Client;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.ServerStrategySolveSearchProblem;
import Server.ServerStrategyGenerateMaze;
import Server.Server;
import algorithms.search.Solution;
import algorithms.mazeGenerators.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final Logger LOG = LogManager.getLogger();
    public MyModel() {
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        LOG.info("Starting server at port = 5400"); LOG.info("Starting server at port = 5401");
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }

    @Override
    public boolean containsPath(int row, int col) {
        return maze.containsPath(row, col);
    }

    @Override
    public void generateMaze(int rows, int cols) {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, (inFromServer, outToServer) -> {
                try {
                    LOG.info("Client accepted to Generate Maze ");
                    ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                    toServer.flush();
                    int[] mazeDimensions = new int[]{rows, cols};
                    toServer.writeObject(mazeDimensions);
                    toServer.flush();
                    byte[] compressedMaze = (byte[])fromServer.readObject();
                    InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                    byte[] decompressedMaze = new byte[rows * cols + 8];
                    is.read(decompressedMaze);
                    maze = new Maze(decompressedMaze);
                } catch (Exception var10) {
                    LOG.error("Couldn't generate the maze");
                    var10.printStackTrace();
                }
            });
            client.communicateWithServer();
        } catch (Exception var1) {
            LOG.error("Couldn't connect to the client");
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
        LOG.info("Moving player's location to" + direction.toString());
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

    public void movePlayer(int row, int col){
        playerPosition = new Position(row, col);
        setChanged();
        notifyObservers("player moved");
        isSolved();
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
                    LOG.info("Client accepted to Solve Maze ");
                    ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                    ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                    toServer.flush();
                    toServer.writeObject(maze);
                    toServer.flush();
                    solution = (Solution)fromServer.readObject();
                } catch (Exception var10) {
                    LOG.error("Couldn't solve the maze");
                    var10.printStackTrace();
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            LOG.error("Couldn't connect to the client");
            var1.printStackTrace();
        }
        setChanged();
        notifyObservers("maze solved");
    }
    public Solution getSolution() {
        return solution;
    }
    public void saveMaze(File mazeFile){
        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFile));
            out.write(maze.toByteArray());
            out.flush();
            out.close();
            LOG.info(String.format("Saving maze as %s", mazeFile.getName()));
        } catch (IOException var8) {
            LOG.error("Can't save the maze");
            var8.printStackTrace();
        }
        setChanged();
        notifyObservers("maze saved");
    }
    public void loadMaze(File mazeFile){
        byte[] savedMazeBytes;
        try {
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFile));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
            maze = new Maze(savedMazeBytes);
            LOG.info(String.format("Loading %s's maze", mazeFile.getName()));
        } catch (IOException var7) {
            LOG.error("Can't load the maze file");
            var7.printStackTrace();
            savedMazeBytes = null;
        }
        maze = new Maze(savedMazeBytes);
        setChanged();
        notifyObservers("maze generated");
    }
    public void setMaze(Maze maze){
        this.maze = maze;
        this.solution = null;
        playerPosition = new Position(0, 0);
        setChanged();
        notifyObservers("maze generated");
    }
    public void isSolved(){
        int row = getPlayerRow();
        int col = getPlayerCol();
        if (row == getMaze().getRows() -1 && col == getMaze().getColumns() - 1) {
            setChanged();
            notifyObservers("maze solved by user");
        }
    }
}
