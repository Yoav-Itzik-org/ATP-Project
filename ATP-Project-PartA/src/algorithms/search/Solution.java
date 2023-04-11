package algorithms.search;

import algorithms.mazeGenerators.Position;

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
//    public void append(AState state){
//        if (nodes.size() != 0)
//            nodes.get(0).setCameFrom(state);
//        nodes.add(0, state);
//    }
    public ArrayList<AState> getSolutionPath(){
        return nodes;
    }
}
