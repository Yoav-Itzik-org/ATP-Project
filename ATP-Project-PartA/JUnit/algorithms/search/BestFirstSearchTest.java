package algorithms.search;
import static org.junit.jupiter.api.Assertions.*;
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
    void solve(){
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
}