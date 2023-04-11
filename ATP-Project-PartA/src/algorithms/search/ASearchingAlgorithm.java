package algorithms.search;
import java.util.ArrayList;
import java.util.PriorityQueue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{
    protected PriorityQueue<AState> openList;
    private  int visitedNodes;
    public ASearchingAlgorithm(){
        visitedNodes = 0;
    }
    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }
    public AState popOpenList() {
        if(openList.isEmpty())
            return null;
        visitedNodes++;
        return openList.poll();
    }
    public Solution solve(ISearchable domain){
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