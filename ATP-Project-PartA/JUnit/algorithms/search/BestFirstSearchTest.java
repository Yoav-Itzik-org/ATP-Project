package algorithms.search;
import static org.junit.jupiter.api.Assertions.*;
import algorithms.mazeGenerators.Maze;
import org.junit.jupiter.api.*;

class BestFirstSearchTest {
    @Test
    void getName() {
        BestFirstSearch best = new BestFirstSearch();
        assertNotEquals(best.getName(), "BFS");
        assertEquals(best.getName(), "Best First Search");
    }
    @Test
    void evaluatedNodes() {
        BestFirstSearch best = new BestFirstSearch();
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
    }
    @Test
    void popOpenList(){
        BestFirstSearch best = new BestFirstSearch();
        assertNull(best.popOpenList());
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
    }
    @Test
    void solve(){
        BestFirstSearch best = new BestFirstSearch();
        assertEquals(best.solve(null).getSolutionPath().size(), 0);
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
        SearchableMaze searchable = new SearchableMaze(new Maze(0, 0));
        assertEquals(best.solve(searchable).getSolutionPath().size(), 0);
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
        searchable = new SearchableMaze(new Maze(10, 0));
        assertEquals(best.solve(searchable).getSolutionPath().size(), 0);
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
        searchable = new SearchableMaze(new Maze(0, 10));
        assertEquals(best.solve(searchable).getSolutionPath().size(), 0);
        assertEquals(best.getNumberOfNodesEvaluated(), 0);
        searchable = new SearchableMaze(new Maze(8, 10));
        assertNotEquals(best.solve(searchable).getSolutionPath().size(), 0);
        assertNotEquals(best.getNumberOfNodesEvaluated(), 0);
    }
}