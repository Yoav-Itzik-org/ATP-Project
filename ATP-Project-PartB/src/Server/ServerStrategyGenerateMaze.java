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

            int[] mazeSize = (int[]) fromClient.readObject();
            Maze maze = new MyMazeGenerator().generate(mazeSize[0], mazeSize[1]); //TODO change maze type
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteArrayOutputStream);
            compressor.write(maze.toByteArray());
            compressor.flush();
            toClient.writeObject(byteArrayOutputStream.toByteArray());
            toClient.flush();
            fromClient.close();
            compressor.close();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}