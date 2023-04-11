package algorithms.search;

import java.util.PriorityQueue;

public class BreadthFirstSearch extends ASearchingAlgorithm {
    public BreadthFirstSearch(){
        openList = new PriorityQueue<>();
    }
    public String getName(){return "Breadth First Search";}
}