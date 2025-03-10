/**
 * Represents a sports division and determines which teams are mathematically
 * eliminated.
 * 
 * @author Yaohui Wu
 */
public class BaseballElimination {
    /**
     * Create a baseball division from given filename.
     */
    public BaseballElimination(String filename) {}

    /**
     * Number of teams.
     */
    public int numberOfTeams() {}

    /**
     * All teams.
     */
    public Iterable<String> teams() {}

    /**
     * Number of wins for given team.
     */
    public int wins(String team) {}

    /**
     * Number of losses for given team.
     */
    public int losses(String name) {}

    /**
     * Number of remaining games for given team.
     */
    public int remaining(String team) {}

    /**
     * Number of remaining games between team1 and team2.
     */
    public int against(String team1, String team2) {}

    /**
     * Is given team eliminated?
     */
    public boolean isEliminated(String team) {}

    /**
     * Subset R of teams that eliminates given team; null if not eliminated.
     */
    public Iterable<String> certificateOfElimination(String team) {}
}
