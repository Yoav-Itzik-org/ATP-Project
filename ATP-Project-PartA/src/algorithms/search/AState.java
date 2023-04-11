package algorithms.search;

public abstract class AState implements Comparable<AState> {
    final private int id;
    private double cost;
    private AState cameFrom;
    static int counter = 0;
    public AState (){
        this.cost = 0;
        this.cameFrom = null;
        this.id = counter++;
    }
    @Override
    public int compareTo(AState other) {
        if (other == null)
            return id;
        return -(other.id - id);
    }
    public void setCost(double cost) {this.cost = cost;}
    public void addCost(double cost){this.cost += cost;}
    public double getCost() {return cost;}
    public void setCameFrom(AState came){this.cameFrom = came;}
    public AState getCameFrom(){return cameFrom == null ? null : this.cameFrom;}
}