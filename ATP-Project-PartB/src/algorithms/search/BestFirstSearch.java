package algorithms.search;
import java.util.Comparator;
import java.util.PriorityQueue;
/**
 * Execution of Best First Search algorithm
 * The algorithm using the same algorithm as BFS. The difference is the data structure*/
public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch(){
        Comparator<AState> score = (o1, o2) -> (int)(o2.getCost() - o1.getCost()); // the values in the queue ordered by their cost
        openList = new PriorityQueue<>(score);
    }
    public String getName(){
        return "Best First Search";
    }
}
