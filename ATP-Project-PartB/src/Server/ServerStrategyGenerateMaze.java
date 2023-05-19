package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    private Configurations configurations;
    {
        try {
            configurations = Configurations.getInstance();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void serverStrategy(InputStream in, OutputStream out) throws IOException{
        try {
            ObjectInputStream fromClient = new ObjectInputStream(in);
            ObjectOutputStream toClient = new ObjectOutputStream(out);
            int[] mazeSize = (int[]) fromClient.readObject();
            Maze maze;
            switch (configurations.getGenerateMaze()){
                case "EmptyMazeGenerator" -> maze = new EmptyMazeGenerator().generate(mazeSize[0], mazeSize[1]);
                case "MyMazeGenerator" -> maze = new MyMazeGenerator().generate(mazeSize[0], mazeSize[1]);
                case "SimpleMazeGenerator" -> maze = new SimpleMazeGenerator().generate(mazeSize[0], mazeSize[1]);
                default -> {return;}
            }
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