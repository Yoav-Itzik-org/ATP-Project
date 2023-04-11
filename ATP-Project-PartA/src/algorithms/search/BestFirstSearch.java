package algorithms.search;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends ASearchingAlgorithm {
    public BestFirstSearch(){
        Comparator<AState> score = (o1, o2) -> (int)(o2.getCost() - o1.getCost());
        openList = new PriorityQueue<>(score);
    }
    public String getName(){
        return "Best First Search";
    }
}
