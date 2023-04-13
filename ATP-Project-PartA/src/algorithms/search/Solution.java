package algorithms.search;
import java.util.ArrayList;

/**
 * Solution object
 * a solution hold a path of states which are connected with cells of paths
 */
public class Solution {
    private final ArrayList<AState> nodes;

    /**
     * Get a state and start getting its ancestors to create a full path to it from the start
     * @param state the last state in the path
     */
    public Solution(AState state){
        nodes = new ArrayList<>();
        while (state != null) {
            nodes.add(0, state); // Add the state to the top of the list, so it will be order eventually from start to finish
            state = state.getCameFrom(); // Get the ancestor
        }
    }
    public ArrayList<AState> getSolutionPath(){
        return nodes;
    }
}
