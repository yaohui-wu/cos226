import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Represents a sports division and determines which teams are mathematically
 * eliminated.
 * 
 * @author Yaohui Wu
 */
public final class BaseballElimination {
    private final int numTeams; // Number of teams.
    private final Map<String, Integer> teams; // Team name to index.
    private final int[] wins; // Wins for each team.
    private final int[] losses; // Losses for each team.
    private final int[] rem; // Remaining games for each team.
    private final int[][] games; // Remaining games between teams.
    
    /**
     * Create a baseball division from given filename.
     */
    public BaseballElimination(String filename) {
        In file = new In(filename);
        numTeams = file.readInt();
        teams = new HashMap<>();
        wins = new int[numTeams];
        losses = new int[numTeams];
        rem = new int[numTeams];
        games = new int[numTeams][numTeams];
        for (int i = 0; i < numTeams; i++) {
            String team = file.readString();
            teams.put(team, i);
            wins[i] = file.readInt();
            losses[i] = file.readInt();
            rem[i] = file.readInt();
            for (int j = 0; j < numTeams; j++) {
                games[i][j] = file.readInt();
            }
        }
    }

    /**
     * Number of teams.
     */
    public int numberOfTeams() {
        return numTeams;
    }

    /**
     * All teams.
     */
    public Iterable<String> teams() {
        return teams.keySet();
    }

    /**
     * Number of wins for given team.
     */
    public int wins(String team) {
        int i = teams.get(team);
        return wins[i];
    }

    /**
     * Number of losses for given team.
     */
    public int losses(String team) {
        int i = teams.get(team);
        return losses[i];
    }

    /**
     * Number of remaining games for given team.
     */
    public int remaining(String team) {
        int i = teams.get(team);
        return rem[i];
    }

    /**
     * Number of remaining games between team1 and team2.
     */
    public int against(String team1, String team2) {
        int i = teams.get(team1);
        int j = teams.get(team2);
        return games[i][j];
    }

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
