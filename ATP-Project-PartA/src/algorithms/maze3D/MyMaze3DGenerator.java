package algorithms.maze3D;
import java.util.Random;

/**
 * MyMaze3DGenerator - generating a maze using Binary tree algorithm - always go in a "beneficial" direction
 */

public class MyMaze3DGenerator extends AMaze3DGenerator{
    @Override
    public Maze3D generate(int depth, int row, int column) {
        Maze3D maze = new Maze3D(depth, row, column);
        for(int d = 0; d < depth; d++)
            for(int r = 0; r < row; r++)
                for(int c = 0; c < column; c++)
                    maze.setWall(d, r, c);
        int d = 0;
        int r = 0;
        int c = 0;
        Random rnd = new Random();
        while (!maze.is_end(d, r, c)) { //generating the maze until we get to the end
            maze.setPath(d, r, c);
            boolean validForward = !(d == depth - 1 || maze.containsPath(d + 1, r, c));
            boolean validBackward = !(d == 0 || maze.containsPath(d - 1, r, c));
            boolean rEnd = r == row - 1;
            boolean cEnd = c == column - 1;
            // Check if reached bottom right - if so go straight to the Goal
            if (rEnd && cEnd) {
                d--;
                continue;
            }
            // Checking if chosen to changed depth and that it can to
            if(rnd.nextBoolean() && (validForward || validBackward)) {
                if(!validBackward || rnd.nextBoolean() && validForward)
                    d++;
                else
                    d--;
                continue;
            }
            if(cEnd || (rnd.nextBoolean() && !rEnd))
                r++;
            else
                c++;
        }
        maze.setPath(d, r, c);
        makeRandomPaths(maze);// we mix the other cells because there is a path to the end
        return maze;
    }
}
