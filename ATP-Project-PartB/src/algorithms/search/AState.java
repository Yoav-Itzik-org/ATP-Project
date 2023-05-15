package algorithms.search;

/**
 * AState Class - abstract class to represent an object.
 * A state has an id (for when he was initialized compared to older objects), cost to get to it,
 * A "pi" attribute that represent the ancestor of the state.
 * The class also has static counter to generate the id (so it will be unique)
 */
public abstract class AState implements Comparable<AState> {
    final private int id;
    private double cost;
    private AState cameFrom;
    private static int counter = 0;
    public AState (){
        this.cost = 0;
        this.cameFrom = null;
        this.id = counter++;
    }

    /**
     * Overriding compare to of the AState
     * The state which is the "older" (initialized first) considered the "bigger"
     * @param other the object to be compared.
     * @return if this older that other
     */
    @Override
    public int compareTo(AState other) {
        if (other == null)
            return id;
        return -(other.id - id);
    }
    public void setCost(double cost) {this.cost = cost;}

    /**
     * Add a cost to the current cost (instead of replacing it)
     * @param cost the cost to add
     */
    public void addCost(double cost){setCost(this.cost + cost);}
    public double getCost() {return cost;}
    public void setCameFrom(AState came){this.cameFrom = came;}
    public AState getCameFrom(){return cameFrom == null ? null : this.cameFrom;}
}