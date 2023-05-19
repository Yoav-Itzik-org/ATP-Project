package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    ArrayList<Maze> solvedMazes;
    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "Solution%d.solution";
    private final Configurations configurations;
    public ServerStrategySolveSearchProblem(){
        solvedMazes = new ArrayList<>();
        try {
            configurations = Configurations.getInstance();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
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
            switch (configurations.getSolutionAlgorithm()) {
                case "BestFirstSearch" -> solution = new BestFirstSearch().solve(new SearchableMaze(maze));
                case "BreadthFirstSearch" -> solution = new BreadthFirstSearch().solve(new SearchableMaze(maze));
                case "DepthFirstSearch" -> solution = new DepthFirstSearch().solve(new SearchableMaze(maze));
                default -> {
                    Position startPosition = maze.getStartPosition();
                    int startRow = startPosition.getRowIndex();
                    int startColumn = startPosition.getColumnIndex();
                    AState startState = new MazeState(startRow, startColumn, 0);
                    solution = new Solution(startState);
                }
            }
            String currentFile = String.format(tempDirectoryPath, solvedMazes.size() - 1);
            ObjectOutputStream insertSolutionStream = new ObjectOutputStream(new FileOutputStream(currentFile));
            insertSolutionStream.writeObject(solution);
            System.out.println("Added new solution");
        }
        else {
            String currentFile = String.format(tempDirectoryPath, solutionIndex);
            ObjectInputStream getSolutionStream = new ObjectInputStream(new FileInputStream(currentFile));
            try {
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
        System.gc();
        for(int fileIndex = 0; fileIndex < solvedMazes.size(); fileIndex++) {
            String currentFile = String.format(tempDirectoryPath, fileIndex);
            File file = new File(currentFile);
            if (!file.delete())
                System.out.println("Failed delete file");
        }
    }
}