package algorithms.search;
import java.util.ArrayList;

public class Solution {
    ArrayList<AState> nodes;

    public Solution(AState state){
        nodes = new ArrayList<>();
        while (state != null) {
            nodes.add(0, state);
            state = state.getCameFrom();
        }
    }

    public ArrayList<AState> getSolutionPath(){
        return nodes;
    }
}
