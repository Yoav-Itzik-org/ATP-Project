package algorithms.search;
import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * Execution of BFS algorithm*/
public class BreadthFirstSearch extends ASearchingAlgorithm {
    public BreadthFirstSearch(){
        openList = new PriorityQueue<>(); //for BFS we are using Priority queue with default comparison (by minimum value)
    }
    public String getName(){return "Breadth First Search";}
    /**
     * solving a maze by using a BFS algorithm
     * @param domain the maze we need to solve
     * @return the path of the solution*/
    public Solution solve(ISearchable domain){
        resetVisited();
        if(domain != null && domain.getEndState() != null) { // if the maze is empty (or null) we will return a null solution
            openList.add(domain.getStartState()); // starting to solve the maze from the start state
            ArrayList<AState> visited = new ArrayList<>(); // contains all the visited cells
            while (!openList.isEmpty()) {
                AState current = popOpenList();
                visited.add(current);
                if (current.equals(domain.getEndState())) // if we get to the end we will return the solution from the end
                    return new Solution(current);
                ArrayList<AState> successors = domain.getAllPossibleStates(current);
                for (AState successor : successors)
                    if (!openList.contains(successor) && !visited.contains(successor))
                        openList.add(successor);
            }
        }
        return new Solution(null);
    }
}