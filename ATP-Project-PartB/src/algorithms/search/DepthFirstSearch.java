package algorithms.search;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
/**
 * Execution of DFS algorithm*/
public class DepthFirstSearch extends ASearchingAlgorithm{
    public DepthFirstSearch(){
        openList = new PriorityQueue<>(Collections.reverseOrder());
    } //for DFS we are using Priority queue with reverse order comparison, so it act like stack
    public String getName(){return "Depth First Search";}
    public Solution solve(ISearchable domain){
        resetVisited();
        if(domain != null && domain.getEndState() != null) {// if the maze is empty (or null) we will return a null solution
            openList.add(domain.getStartState());// starting to solve the maze from the start state
            ArrayList<AState> visited = new ArrayList<>();// contains all the visited cells
            while (!openList.isEmpty()) {
                AState current = popOpenList();
                visited.add(current);
                if (current.equals(domain.getEndState()))// if we get to the end we will return the solution from the end
                    return new Solution(current);
                ArrayList<AState> successors = domain.getAllPossibleStates(current);
                for (AState successor : successors) {
                    if(visited.contains(successor))
                        continue;
                    openList.remove(successor);// if we reach to a new cell we will put it on top of the queue
                    openList.add(successor);
                }
            }
        }
        return new Solution(null);
    }
}
