package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    public void serverStrategy(InputStream in, OutputStream out) throws IOException{
        try {
            ObjectInputStream fromClient = new ObjectInputStream(in);
            ObjectOutputStream toClient = new ObjectOutputStream(out);
            toClient.flush();
            int[] mazeSize = (int[]) fromClient.readObject();
            Maze maze = new MyMazeGenerator().generate(mazeSize[0], mazeSize[1]); //TODO change maze type
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(toClient);
            compressor.write(maze.toByteArray());
            compressor.flush();
            fromClient.close();
            toClient.close();
            compressor.close();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}