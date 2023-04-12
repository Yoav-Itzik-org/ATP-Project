package algorithms.search;
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
}