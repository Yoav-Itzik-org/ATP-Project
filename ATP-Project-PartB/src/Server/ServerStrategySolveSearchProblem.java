package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    public void serverStrategy(InputStream in, OutputStream out)throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(in);
            ObjectOutputStream toClient = new ObjectOutputStream(out);
            Maze maze = (Maze) fromClient.readObject();
            Solution solution = new BestFirstSearch().solve(new SearchableMaze(maze)); // TODO change the search type
            //TODO - Save solution
            toClient.writeObject(solution);
            toClient.flush();
            fromClient.close();
            toClient.close();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
