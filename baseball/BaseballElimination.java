import edu.princeton.cs.algs4.StdOut;

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

    /**
     * Reads in a sports division from an input file and prints whether each
     * team is mathematically eliminated and a certificate of elimination for
     * each team that is eliminated.
     */
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
