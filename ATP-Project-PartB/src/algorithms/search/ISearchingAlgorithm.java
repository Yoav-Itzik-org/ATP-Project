package algorithms.search;

/**
 * interface for searching algorithms.
 * Every searching algorithm should have a methods to get its, to get a solution that it found during it's search,
 * and number of nodes evaluated during the search.
 */
public interface ISearchingAlgorithm {
    String getName();
    /**
     * Get a searchable object and try to solve it (get a path from the start to the finish according to it)
     * @param domain a searchable object to run the search om
     * @return a solution to the searchable object - path from the start to the finish (or empty path if there isn't)
     */
    Solution solve(ISearchable domain);
    int getNumberOfNodesEvaluated();
}
