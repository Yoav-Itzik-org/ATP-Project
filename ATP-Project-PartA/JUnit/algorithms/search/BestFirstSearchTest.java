package algorithms.search;
import static org.junit.jupiter.api.Assertions.*;

import algorithms.maze3D.*;
import algorithms.mazeGenerators.Maze;
import org.junit.jupiter.api.*;

/**
 * testing cases with Best First Search algorithm
 */
class BestFirstSearchTest {
    /**
     * checking if getName function return the correct name
     */
    @Test
    void getName() {
        BestFirstSearch best = new BestFirstSearch();
        assertNotEquals(best.getName(), "BFS");
        assertEquals(best.getName(), "Best First Search");
    }
    /**
     * checking if the total nodes of creating a new Best First Search is 0
     */
    @Test
    void evaluatedNodes() {
        BestFirstSearch best = new BestFirstSearch();
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
    }
    /**
     * checking if the total evaluated nodes after popping a cell by creating a new Best First Search is 0
     */
    @Test
    void popOpenList(){
        BestFirstSearch best = new BestFirstSearch();
        assertNull(best.popOpenList());
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
    }
    /**
     * checking cases of solving a maze with Best First Search algorithm
     */
    @Test
    void solve2D() {
        BestFirstSearch best = new BestFirstSearch();
        assertEquals(best.solve(null).getSolutionPath().size(), 0);// checking if there is no path of solution by given a null maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        SearchableMaze searchable = new SearchableMaze(new Maze(0, 0));
        assertEquals(best.solve(searchable).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable = new SearchableMaze(new Maze(10, 0));
        assertEquals(best.solve(searchable).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable = new SearchableMaze(new Maze(0, 10));
        assertEquals(best.solve(searchable).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable = new SearchableMaze(new Maze(8, 10));
        assertNotEquals(best.solve(searchable).getSolutionPath().size(), 0);// checking if there is a path of solution by given a valid maze
        assertNotEquals(best.getNumberOfNodesEvaluated(), 0);
    }
        // Checking runs on maze3D
        @Test
        void solve3D() {
        BestFirstSearch best = new BestFirstSearch();
        SearchableMaze3D searchable3 = new SearchableMaze3D(new Maze3D(0, 0, 0));
        assertEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable3 = new SearchableMaze3D(new Maze3D(10, 0, 0));
        assertEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable3 = new SearchableMaze3D(new Maze3D(0, 10, 0));
        assertEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable3 = new SearchableMaze3D(new Maze3D(0,0 , 10));
        assertEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable3 = new SearchableMaze3D(new Maze3D(10, 10, 0));
        assertEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable3 = new SearchableMaze3D(new Maze3D(0, 10, 10));
        assertEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable3 = new SearchableMaze3D(new Maze3D(10,0 , 10));
        assertEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is no path of solution by given a not valid size of maze
        assertEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable3 = new SearchableMaze3D(new Maze3D(3, 3, 3));
        assertNotEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is a path of solution by given a valid maze
        assertNotEquals(best.getNumberOfNodesEvaluated(), 0);

        searchable3 = new SearchableMaze3D(new Maze3D(1, 1, 1));
        assertNotEquals(best.solve(searchable3).getSolutionPath().size(), 0);// checking if there is a path of solution by given a valid maze
        assertNotEquals(best.getNumberOfNodesEvaluated(), 0);
    }
}