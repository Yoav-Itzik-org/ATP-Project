package test;
import algorithms.mazeGenerators.*;

public class RunMazeGenerator {
    public static void main(String[] args) {
        testMazeGenerator(new EmptyMazeGenerator());
        testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }
    private static void testMazeGenerator(IMazeGenerator mazeGenerator){
        System.out.printf("Maze generation time(ms): %s%n", mazeGenerator.measureAlgorithmTimeMillis(100, 90));
        Maze maze = mazeGenerator.generate(90,100);
        maze.print();
        Position startPosition = maze.getStartPosition();
        System.out.printf("Start Position: %s%n", startPosition);
        System.out.printf("Goal Position: %s%n", maze.getGoalPosition());
    }
}
