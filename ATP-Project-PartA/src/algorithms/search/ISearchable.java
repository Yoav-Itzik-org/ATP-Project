package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {
    AState getStartState();
    AState getEndState();
    ArrayList<AState> getAllSuccessors(AState s);
}
