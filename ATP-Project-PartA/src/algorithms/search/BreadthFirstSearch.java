package algorithms.search;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    public BreadthFirstSearch(){
        openList = new PriorityQueue<>();
    }
    public String getName(){return "Breadth First Search";}
    public Solution solve(ISearchable domain){
        resetVisited();
        if(domain != null && domain.getEndState() != null) {
            openList.add(domain.getStartState());
            ArrayList<AState> visited = new ArrayList<>();
            while (!openList.isEmpty()) {
                AState current = popOpenList();
                visited.add(current);
                if (current.equals(domain.getEndState()))
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