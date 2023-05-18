package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    ArrayList<Maze> solvedMazes;
    public ServerStrategySolveSearchProblem(){solvedMazes = new ArrayList<>();}
    public void serverStrategy(InputStream in, OutputStream out)throws IOException {
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
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
            solution = new BestFirstSearch().solve(new SearchableMaze(maze)); // TODO change the search type
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
}
