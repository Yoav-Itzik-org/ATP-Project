package test;

import algorithms.maze3D.IMaze3DGenerator;
import algorithms.maze3D.Maze3D;
import algorithms.maze3D.MyMaze3DGenerator;
import algorithms.maze3D.Position3D;

public class RunMaze3DGenerator {
    public static void main(String[] args) {
        testMaze3DGenerator(new MyMaze3DGenerator());
    }
    private static void testMaze3DGenerator(IMaze3DGenerator mazeGenerator) {
//        prints the time it takes the algorithm to run
        System.out.printf("Maze generation time(ms): %s%n", mazeGenerator.measureAlgorithmTimeMillis(500/*depth*/, 500/*rows*/,500/*columns*/));
//         generate another maze
        Maze3D maze = mazeGenerator.generate(500/*depth*/, 500/*rows*/, 500/*columns*/);
//         get the maze entrance
        Position3D startPosition = maze.getStartPosition();
//         print the start position
        System.out.printf("Start Position: %s%n", startPosition); // format "{depth,row,column}"
//         prints the maze exit position
        System.out.printf("Goal Position: %s%n", maze.getGoalPosition());
    }
}
