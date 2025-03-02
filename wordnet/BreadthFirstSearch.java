import java.util.ArrayDeque;
import java.util.Deque;

import edu.princeton.cs.algs4.Digraph;

/**
 * Breadth-first search.
 * 
 * @author Yaohui Wu
 */
public class BreadthFirstSearch {
    private static final int INF = Integer.MAX_VALUE; // Represents infinity.
    // visited[v] = true if there is a path from s to v.
    private boolean[] visited;
    // edgeTo[v] = last edge on the shortest path from s to v.
    private int[] edgeTo;
    // distTo[v] = number of edges in the shortest path from s to v.
    private int[] distTo;

    public BreadthFirstSearch(Digraph g, int s) {
        int order = g.V(); // Number of vertices in the graph.
        visited = new boolean[order];
        edgeTo = new int[order];
        distTo = new int[order];
        for (int i = 0; i < order; i += 1) {
            distTo[i] = INF;
        }
        bfs(g, s);
    }

    private void bfs(Digraph g, int s) {
        Deque<Integer> q = new ArrayDeque<>();
        visited[s] = true;
        distTo[s] = 0;
        q.add(s);
        while (!q.isEmpty()) {
            int v = q.remove();
            for (int w : g.adj(v)) {
                if (!visited[w]) {
                    visited[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.add(w);
                }
            }
        }
    }
}
