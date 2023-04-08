package algorithms.search;

public abstract class AState {
    private String state;
    private double cost;
    private AState cameFrom;
    public AState (String state, double cost, AState cameFrom){
        this.state = state;
        this.cost = cost;
        this.cameFrom = cameFrom;
    }
    @Override
    public boolean equals(Object other){
        if(!(other instanceof AState))
            return false;
        return other == this || state.equals(((AState) other).state);
    }
}