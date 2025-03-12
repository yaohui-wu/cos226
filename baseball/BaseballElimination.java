import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
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
    private final Map<Integer, String> indices; // Index to team name.
    private final int[] wins; // Wins for each team.
    private final int[] losses; // Losses for each team.
    private final int[] rem; // Remaining games for each team.
    private final int[][] games; // Remaining games between teams.
    private final boolean elimination[];
    private int flow;
    
    /**
     * Create a baseball division from given filename.
     */
    public BaseballElimination(String filename) {
        In file = new In(filename);
        numTeams = file.readInt();
        teams = new HashMap<>();
        indices = new HashMap<>();
        wins = new int[numTeams];
        losses = new int[numTeams];
        rem = new int[numTeams];
        games = new int[numTeams][numTeams];
        elimination = new boolean[numTeams];
        for (int i = 0; i < numTeams; i++) {
            String team = file.readString();
            teams.put(team, i);
            indices.put(i, team);
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
        validateTeams(team);
        int i = teams.get(team);
        return wins[i];
    }

    /**
     * Number of losses for given team.
     */
    public int losses(String team) {
        validateTeams(team);
        int i = teams.get(team);
        return losses[i];
    }

    /**
     * Number of remaining games for given team.
     */
    public int remaining(String team) {
        validateTeams(team);
        int i = teams.get(team);
        return rem[i];
    }

    /**
     * Number of remaining games between team1 and team2.
     */
    public int against(String team1, String team2) {
        validateTeams(team1, team2);
        int i = teams.get(team1);
        int j = teams.get(team2);
        return games[i][j];
    }

    /**
     * Is given team eliminated?
     */
    public boolean isEliminated(String team) {
        validateTeams(team);
        if (numTeams == 1) {
            return false;
        }
        int x = teams.get(team);
        return trivialElimination(x) || nontrivialElimination(x);
    }

    private boolean trivialElimination(int x) {
        int maxWins = wins[x] + rem[x];
        for (int i = 0; i < numTeams; i++) {
            if (i != x && maxWins < wins[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean nontrivialElimination(int x) {
        // TODO: Implement the nontrivial elimination check.
        FordFulkerson maxFlow = maxFlow(x);
        return flow > maxFlow.value();
    }
    
    private FordFulkerson maxFlow(int x) {
        int flow = 0;
        // Excludes team x.
        int numOtherTeams = numTeams - 1;
        int numGameVertices = nC2(numOtherTeams);
        // Game vertices + team vertices + source + sink.
        int numVertices = numGameVertices + numOtherTeams + 2;
        FlowNetwork flowNetwork = new FlowNetwork(numVertices);
        int s = 0; // Source.
        int t = numVertices - 1; // Sink.
        int v = 1; // Game vertices start from 1.
        for (int i = 0; i < numTeams; i++) {
            if (i != x) {
                for (int j = i + 1; j < numTeams; j++) {
                    if (j != x) {
                        // Connect source to game vertices.
                        FlowEdge gameEdge = new FlowEdge(s, v, games[i][j]);
                        flowNetwork.addEdge(gameEdge);
                        // Connect each game vertex with the two teams.
                        FlowEdge team1Edge = new FlowEdge(v, i + numGameVertices + 1, Double.POSITIVE_INFINITY);
                        flowNetwork.addEdge(team1Edge);
                        FlowEdge team2Edge = new FlowEdge(v, j + numGameVertices + 1, Double.POSITIVE_INFINITY);
                        flowNetwork.addEdge(team2Edge);
                        flow += games[i][j];
                        v++;
                    }
                }
            }
        }
        int maxWins = wins[x] + rem[x];
        for (int i = numGameVertices; i < numVertices; i++) {
            int possibleWins = maxWins - wins[i];
            if (possibleWins < 0) {
                return null;
            }
            FlowEdge sinkEdge = new FlowEdge(i, t, possibleWins);
            flowNetwork.addEdge(sinkEdge);
        }
        FordFulkerson maxFlow = new FordFulkerson(flowNetwork, s, t);
        return maxFlow;
    }

    private int nC2(int n) {
        // n choose 2.
        return n * (n - 1) / 2;
    }

    /**
     * Subset R of teams that eliminates given team; null if not eliminated.
     */
    public Iterable<String> certificateOfElimination(String team) {
        // TODO: Implement the certificate of elimination.
        validateTeams(team);
        int x = teams.get(team);
        List<String> subset = new ArrayList<>();
        int numGameVertices = nC2(numTeams - 1);
        FordFulkerson maxFlow = maxFlow(x);
        for (int i = 0; i < numTeams; i++) {
            if (x != i && wins[x] + rem[x] < wins[i]) {
                subset.add(indices.get(i));
            } else if (maxFlow.inCut(i + numGameVertices + 1)) {
                subset.add(indices.get(i));
            }
        }
        if (subset.isEmpty()) {
            elimination[x] = true;
            return null;
        }
        return subset;
    }

    private void validateTeams(String... givenTeams) {
        for (String team : givenTeams) {
            if (!teams.containsKey(team)) {
                String error = "Invalid team: " + team;
                throw new IllegalArgumentException(error);
            }
        }
    }

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
