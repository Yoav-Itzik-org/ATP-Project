package Server;

import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    ArrayList<Maze> solvedMazes;
    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "Solutions.txt";
    private Configurations configurations;
    {
        try {
            configurations = Configurations.getInstance();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public ServerStrategySolveSearchProblem(){solvedMazes = new ArrayList<>();}
    public void serverStrategy(InputStream in, OutputStream out)throws IOException {

        ObjectInputStream fromClient; ObjectOutputStream toClient; Maze maze; Solution solution;
        try {
            fromClient = new ObjectInputStream(in);
            toClient = new ObjectOutputStream(out);
            maze = (Maze) fromClient.readObject();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
            return;
        }
        int solutionIndex = solvedMazes.indexOf(maze);
        if(solutionIndex == -1) {
            solvedMazes.add(maze);
            switch (configurations.getSolutionAlgorithm()){
                case "BestFirstSearch" -> solution = new BestFirstSearch().solve(new SearchableMaze(maze));
                case "BreadthFirstSearch" -> solution = new BreadthFirstSearch().solve(new SearchableMaze(maze));
                case "DepthFirstSearch" -> solution = new DepthFirstSearch().solve(new SearchableMaze(maze));
                default -> solution = null;
            }
            ObjectOutputStream insertSolutionStream = new ObjectOutputStream(new FileOutputStream(tempDirectoryPath));
            insertSolutionStream.writeObject(solution);
            System.out.println("Added new solution");
        }
        else {
            ObjectInputStream getSolutionStream = new ObjectInputStream(new FileInputStream(tempDirectoryPath));
            try {
                for (int solutionCount = 0; solutionCount < solutionIndex - 1; solutionCount++)
                    getSolutionStream.readObject();
                solution = (Solution) getSolutionStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
            System.out.println("Previously solved solution");
        }
        toClient.writeObject(solution);
        toClient.flush();
        fromClient.close();
    }
    public void deleteFile(){
        File file = new File(tempDirectoryPath);
        file.delete();
    }
}