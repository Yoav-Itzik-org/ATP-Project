package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class DepthFirstSearch extends ASearchingAlgorithm{
    public DepthFirstSearch(){
        openList = new PriorityQueue<>(Collections.reverseOrder());
    }
    public String getName(){
        return "Depth First Search";
    }
    public Solution solve(ISearchable domain){
        ArrayList<AState> visited = new ArrayList<>();
        openList.add(domain.getStartState());
        while (!openList.isEmpty()){
            AState current = popOpenList();
            visited.add(0, current);
            if(current.equals(domain.getEndState()))
                return new Solution(current);
            ArrayList<AState> successors = domain.getAllSuccessors(current);
            for(AState successor : successors)
                if (!openList.contains(successor) && !(visited.contains(current))){
                    openList.add(successor);
                    break;
                }
        }
        return new Solution(null);
    }
}
