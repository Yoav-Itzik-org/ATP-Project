package algorithms.search;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {
    public BestFirstSearch(){
        Comparator<AState> score = (o1, o2) -> (int)(o1.getCost() - o2.getCost());
        openList = new PriorityQueue<>(score);
    }
    public String getName(){
        return "Best First Search";
    }
}
