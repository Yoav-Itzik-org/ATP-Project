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
        ArrayList<AState> visited = new ArrayList<>();
        openList.add(domain.getStartState());
        while (!openList.isEmpty()){
            AState current = popOpenList();
            visited.add(current);
            if(current.equals(domain.getEndState()))
                return new Solution(current);
            ArrayList<AState> successors = domain.getAllPossibleStates(current);
            for(AState successor : successors)
                if (!openList.contains(successor) && !(visited.contains(successor)))
                    openList.add(successor);
        }
        return new Solution(null);
    }
}
