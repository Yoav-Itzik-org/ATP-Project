package algorithms.search;

import java.util.Collections;
import java.util.PriorityQueue;

public class DepthFirstSearch extends ASearchingAlgorithm{
    public DepthFirstSearch(){
        openList = new PriorityQueue<>(Collections.reverseOrder());
    }
    public String getName(){return "Depth First Search";}
}
