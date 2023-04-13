package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class DepthFirstSearch extends ASearchingAlgorithm{
    public DepthFirstSearch(){
        openList = new PriorityQueue<>(Collections.reverseOrder());
    }
    public String getName(){return "Depth First Search";}
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
                for (AState successor : successors) {
                    if(visited.contains(successor))
                        continue;
                    openList.remove(successor);
                    openList.add(successor);
                }
            }
        }
        return new Solution(null);
    }
}
