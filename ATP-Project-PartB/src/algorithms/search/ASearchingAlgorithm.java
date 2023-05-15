package algorithms.search;
import java.util.PriorityQueue;

/**
 * Abstract class for searching algorithm. every searching algorithm has a priority queue of all the "gray" nodes in it
 * and amount of nodes visited during the search
 */
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
    protected AState popOpenList() {
        if(openList.isEmpty())
            return null;
        visitedNodes++; // because a node was extracted it can be added to the counter of the visited ones
        return openList.poll();
    }

    /**
     * A searching algorithm needs a method to reset the number of evaluated nodes before every search
     */
    protected void resetVisited() {visitedNodes = 0;}
}