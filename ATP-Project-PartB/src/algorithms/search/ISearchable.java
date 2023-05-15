package algorithms.search;

import java.util.ArrayList;

/**
 * ISearchable interface - to authorize searchable objects to implement certain methods
 * Every Searchable object needs to have a state that considered start, end and states that the current one can pass to
 */
public interface ISearchable {
    AState getStartState();
    AState getEndState();
    ArrayList<AState> getAllPossibleStates(AState s);
}
