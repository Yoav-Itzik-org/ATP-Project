package algorithms.search;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    public BreadthFirstSearch(){
        openList = new PriorityQueue<>();
    }
    public String getName(){
        // TO VERIFY
        return "Breadth First Search";
    }
    public Solution solve(ISearchable domain){
        openList.add(domain.getStartState());
        ArrayList<AState> visited = new ArrayList<>();
        while (!openList.isEmpty()){
            AState current = popOpenList();
            visited.add(current);
            if(current.equals(domain.getEndState())){
                return new Solution(current);
            }
            ArrayList<AState> successors = domain.getAllSuccessors(current);
            for(AState successor : successors)
                if(!openList.contains(successor) && !visited.contains(successor)) {
                    successor.addCost(current.getCost());
                    successor.setCameFrom(current);
                    openList.add(successor);
                }
            System.out.printf("Current: %s, Queue: %s\n", current, openList);
        }
        return new Solution(null);
    }
}
