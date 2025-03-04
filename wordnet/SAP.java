import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Shortest ancestral path (SAP).
 * 
 * @author Yaohui Wu
 */
public final class SAP {
    private final Digraph g; // Directed graph.
    // Cache for SAP, vertices -> {0: length, 1: ancestor}.
     private final Map<String, int[]> sapMap;

    /**
     * Constructor takes a digraph (not necessarily a DAG).
     */
    public SAP(Digraph G) {
        validateArgs(G);
        g = new Digraph(G);
        sapMap = new HashMap<>();
    }
 
    /**
     * Length of shortest ancestral path between v and w; -1 if no such path.
     */
    public int length(int v, int w) {
        validateArgs(v, w);
        String key = key(v, w);
        if (sapMap.containsKey(key)) {
            int[] sap = sapMap.get(key);
            int length = sap[0];
            return length;
        }
        int[] sap = findSAP(v, w);
        sapMap.put(key, sap);
        int length = sap[0];
        return length;
    }
 
    /**
     * A common ancestor of v and w that participates in a shortest ancestral
     * path; -1 if no such path.
     */
    public int ancestor(int v, int w) {
        validateArgs(v, w);
        String key = key(v, w);
        if (sapMap.containsKey(key)) {
            int[] sap = sapMap.get(key);
            int ancestor = sap[1];
            return ancestor;
        }
        int[] sap = findSAP(v, w);
        sapMap.put(key, sap);
        int ancestor = sap[1];
        return ancestor;
    }
 
    /**
     * Length of shortest ancestral path between any vertex in v and any
     * vertex in w; -1 if no such path.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateArgs(v, w);
        String key = key(v, w);
        if (sapMap.containsKey(key)) {
            int[] sap = sapMap.get(key);
            int length = sap[0];
            return length;
        }
        int[] sap = findSAP(v, w);
        sapMap.put(key, sap);
        int length = sap[0];
        return length;
    }
 
    /**
     * A common ancestor that participates in shortest ancestral path; -1 if
     * no such path.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateArgs(v, w);
        String key = key(v, w);
        if (sapMap.containsKey(key)) {
            int[] sap = sapMap.get(key);
            int ancestor = sap[1];
            return ancestor;
        }
        int[] sap = findSAP(v, w);
        sapMap.put(key, sap);
        int ancestor = sap[1];
        return ancestor;
    }
 
    /**
     * Do unit testing of this class.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private static void validateArgs(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                String error = "Argument cannot be null";
                throw new IllegalArgumentException(error);
            }
        }
    }

    private void validateVertices(int max, int... vertices) {
        for (int v : vertices) {
            if (v < 0 || v > max) {
                String error = "Vertex must be between 0 and " + max;
                throw new IllegalArgumentException(error);
            }
        }
    }

    private void validateVertices(int max, Iterable<Integer> vertices) {
        for (Integer v : vertices) {
            if (v == null) {
                String error = "Iterable cannot contain null items";
                throw new IllegalArgumentException(error);
            }
            validateVertices(max, v);
        }
    }

    private String key(int v, int w) {
        String key = v + " -> " + w;
        return key;
    }

    private String key(Iterable<Integer> v, Iterable<Integer> w) {
        String key = v.hashCode() + " -> " + w.hashCode();
        return key;
    }

    private int[] findSAP(int v, int w) {
        int max = g.V() - 1;
        validateVertices(max, v, w);
        if (v == w) {
            int[] sap = {0, v};
            return sap;
        }
        List<Integer> vList = new ArrayList<>();
        vList.add(v);
        List<Integer> wList = new ArrayList<>();
        wList.add(w);
        int[] sap = findSAP(vList, wList);
        return sap;
    }

    private int[] findSAP(Iterable<Integer> v, Iterable<Integer> w) {
        int order = g.V(); // Number of vertices in the graph.
        int max = order - 1;
        validateVertices(max, v);
        validateVertices(max, w);
        int[] sap = {-1, -1};
        Map<Integer, Integer> vDist = new HashMap<>(); // Distance from v.
        Map<Integer, Integer> wDist = new HashMap<>(); // Distance from w.
        Deque<Integer> q = new ArrayDeque<>();
        // Initialize distances for v and w.
        for (int x : v) {
            vDist.put(x, 0);
            q.add(x);
        }
        for (int x : w) {
            wDist.put(x, 0);
            q.add(x);
        }
        final int INF = Integer.MAX_VALUE; // Represents infinity.
        int min = INF;
        int ancestor = -1;
        while (!q.isEmpty()) {
            int x = q.remove();
            if (vDist.containsKey(x) && wDist.containsKey(x)) {
                int len = vDist.get(x) + wDist.get(x);
                if (len < min) {
                    min = len;
                    ancestor = x;
                } else if (min != INF && len > min) {
                    break;
                }
            }
            // Explore neighbors.
            for (int y : g.adj(x)) {
                if (vDist.containsKey(x) && !vDist.containsKey(y)) {
                    int dist = vDist.get(x) + 1;
                    vDist.put(y, dist);
                    q.add(y);
                }
                if (wDist.containsKey(x) && !wDist.containsKey(y)) {
                    int dist = wDist.get(x) + 1;
                    wDist.put(y, dist);
                    q.add(y);
                }
            }
        }
        if (min != INF) {
            sap[0] = min;
            sap[1] = ancestor;
        }
        return sap;
    }
}
