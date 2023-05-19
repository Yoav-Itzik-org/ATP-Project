package Server;
import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import java.io.*;
/**
 * the class represent generating maze via server*/
public class ServerStrategyGenerateMaze implements IServerStrategy{
    /**
     * implements the generating maze and send it through the output stream
     * @param in - getting from the client
     * @param out - sending the maze to the out stream
     * @throws IOException*/
    public void serverStrategy(InputStream in, OutputStream out) throws IOException{
        Configurations configurations;
        try {
            configurations = Configurations.getInstance();
        } catch (FileNotFoundException e) {throw new RuntimeException(e);}
        try {
            ObjectInputStream fromClient = new ObjectInputStream(in); // saving the input from the user
            ObjectOutputStream toClient = new ObjectOutputStream(out);
            int[] mazeSize = (int[]) fromClient.readObject(); // contains the maze size with the user ask for
            Maze maze;
            switch (configurations.getGenerateMaze()){ //due to the client preference, we will generate different maze
                // the preference is in "config.properties" file
                default -> maze = new EmptyMazeGenerator().generate(mazeSize[0], mazeSize[1]);
                case "MyMazeGenerator" -> maze = new MyMazeGenerator().generate(mazeSize[0], mazeSize[1]);
                case "SimpleMazeGenerator" -> maze = new SimpleMazeGenerator().generate(mazeSize[0], mazeSize[1]);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteArrayOutputStream); // byteArray will
            // represent the output stream with contains the compressor
            compressor.write(maze.toByteArray()); //saving in maze as byteArray in the compressor
            compressor.flush();
            toClient.writeObject(byteArrayOutputStream.toByteArray()); // using "get" function to send with the output stream the compressor result
            toClient.flush();
            fromClient.close();
            compressor.close();
        }
        catch (ClassNotFoundException e){e.printStackTrace();}
    }
}