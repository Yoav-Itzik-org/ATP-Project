package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;
/**
 * the class represent solving maze via server*/
public class ServerStrategySolveSearchProblem implements IServerStrategy{
    ArrayList<Maze> solvedMazes; // contains all the maze that the server generates
    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "Solution%d.solution"; // the file name that contains a maze path solution
    private final Configurations configurations;
    /**
     * constructor - initialize the maze array and get instance of the configuration class*/
    public ServerStrategySolveSearchProblem(){
        solvedMazes = new ArrayList<>();
        try {configurations = Configurations.getInstance();}
        catch (FileNotFoundException e) {throw new RuntimeException(e);}
    }
    /**
     * implements the solving maze and send it through the output stream to the client
     * @param in - getting from the client
     * @param out - sending the solution maze to the out stream
     * @throws IOException Couldn't read/write to the streams
     */
    public void serverStrategy(InputStream in, OutputStream out)throws IOException {
        ObjectInputStream fromClient; ObjectOutputStream toClient; Maze maze; Solution solution;
        try {
            fromClient = new ObjectInputStream(in); // saving the input from the client
            toClient = new ObjectOutputStream(out);
            maze = (Maze) fromClient.readObject(); //saving it as a maze
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            return;
        }
        int solutionIndex = solvedMazes.indexOf(maze); //trying to find the maze in the MazeSolved array
        if(solutionIndex == -1) { //we didn't find
            solvedMazes.add(maze); //adding the maze to the array
            switch (configurations.getSolutionAlgorithm()) { //due to the client preference, we will solve the maze in a different algorithm
                case "BestFirstSearch" -> solution = new BestFirstSearch().solve(new SearchableMaze(maze));
                case "BreadthFirstSearch" -> solution = new BreadthFirstSearch().solve(new SearchableMaze(maze));
                case "DepthFirstSearch" -> solution = new DepthFirstSearch().solve(new SearchableMaze(maze));
                default -> { //if we get wrong algorithm name, we will return {0,0}
                    Position startPosition = maze.getStartPosition();
                    int startRow = startPosition.getRowIndex();
                    int startColumn = startPosition.getColumnIndex();
                    AState startState = new MazeState(startRow, startColumn, 0);
                    solution = new Solution(startState);
                }
            }
            String currentFile = String.format(tempDirectoryPath, solvedMazes.size() - 1);
            ObjectOutputStream insertSolutionStream = new ObjectOutputStream(new FileOutputStream(currentFile)); //saving the solution in a new file
            insertSolutionStream.writeObject(solution);
        }
        else { //we already solved the same maze, so we will return the file
            String currentFile = String.format(tempDirectoryPath, solutionIndex);
            ObjectInputStream getSolutionStream = new ObjectInputStream(new FileInputStream(currentFile)); //getting the correct file
            try {
                solution = (Solution) getSolutionStream.readObject(); //saving the solution with "get" function
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        toClient.writeObject(solution); // and return it to the client through the output stream
        toClient.flush();
        fromClient.close();
    }
    /**
     * the function deletes all the files that created during the project*/
    public void deleteFile(){
        System.gc(); // calling the garbage collector
        for(int fileIndex = 0; fileIndex < solvedMazes.size(); fileIndex++) { //running throw all the files and delete them
            String currentFile = String.format(tempDirectoryPath, fileIndex);
            File file = new File(currentFile);
            file.delete();
        }
    }
}