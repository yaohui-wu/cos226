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
    // SAP: vertices -> {0: length, 1: ancestor}.
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
        validateVertices(v, w);
        String key = key(v, w);
        if (sapMap.containsKey(key)) {
            int[] sap = sapMap.get(key);
            return sap[0];
        }
        int[] sap = findSAP(v, w);
        sapMap.put(key, sap);
        return sap[0];
    }
 
    /**
     * A common ancestor of v and w that participates in a shortest ancestral
     * path; -1 if no such path.
     */
    public int ancestor(int v, int w) {
        validateArgs(v, w);
        validateVertices(v, w);
        String key = key(v, w);
        if (sapMap.containsKey(key)) {
            int[] sap = sapMap.get(key);
            return sap[1];
        }
        int[] sap = findSAP(v, w);
        sapMap.put(key, sap);
        return sap[1];
    }
 
    /**
     * Length of shortest ancestral path between any vertex in v and any
     * vertex in w; -1 if no such path.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateArgs(v, w);
        validateVertices(v);
        validateVertices(w);
        String key = key(v, w);
        if (sapMap.containsKey(key)) {
            int[] sap = sapMap.get(key);
            return sap[0];
        }
        int[] sap = findSAP(v, w);
        sapMap.put(key, sap);
        return sap[0];
    }
 
    /**
     * A common ancestor that participates in shortest ancestral path; -1 if
     * no such path.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateArgs(v, w);
        validateVertices(v);
        validateVertices(w);
        String key = key(v, w);
        if (sapMap.containsKey(key)) {
            int[] sap = sapMap.get(key);
            return sap[1];
        }
        int[] sap = findSAP(v, w);
        sapMap.put(key, sap);
        return sap[1];
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

    private void validateVertices(int... vertices) {
        int max = g.V() - 1;
        for (int v : vertices) {
            if (v < 0 || v > max) {
                String error = "Vertex must be between 0 and " + max;
                throw new IllegalArgumentException(error);
            }
        }
    }

    private void validateVertices(Iterable<Integer> vertices) {
        for (Integer v : vertices) {
            if (v == null) {
                String error = "Iterable cannot contain null items";
                throw new IllegalArgumentException(error);
            }
            validateVertices(v);
        }
    }

    private String key(int v, int w) {
        String key = v + " " + w;
        return key;
    }

    private String key(Iterable<Integer> v, Iterable<Integer> w) {
        String key = v + " " + w;
        return key;
    }

    private int[] findSAP(int v, int w) {
        if (v == w) {
            return new int[] {0, v};
        }
        List<Integer> vList = new ArrayList<>();
        vList.add(v);
        List<Integer> wList = new ArrayList<>();
        wList.add(w);
        return findSAP(vList, wList);
    }

    private int[] findSAP(Iterable<Integer> v, Iterable<Integer> w) {
        // Distance from v to each vertex, vertex -> distance.
        Map<Integer, Integer> vDists = new HashMap<>();
        // Distance from w to each vertex, vertex -> distance.
        Map<Integer, Integer> wDists = new HashMap<>();
        Deque<Integer> vQ = new ArrayDeque<>(); // Queue for BFS from v.
        for (int x : v) {
            vDists.put(x, 0);
            vQ.add(x);
        }
        Deque<Integer> wQ = new ArrayDeque<>(); // Queue for BFS from w.
        for (int x : w) {
            wDists.put(x, 0);
            wQ.add(x);
        }
        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        /*
         * Run breadth-first searches (BFS) from v and w in lockstep.
         * Terminate the BFS from v (or w) as soon as the distance exceeds the
         * length of the best ancestral path.
         */
        while (!vQ.isEmpty() || !wQ.isEmpty()) {
            if (!vQ.isEmpty()) {
                int x = vQ.remove();
                int vDist = vDists.get(x);
                if (wDists.containsKey(x)) {
                    int length = vDist + wDists.get(x);
                    if (length < min) {
                        min = length;
                        ancestor = x;
                    }
                }
                if (vDist < min) {
                    for (int y : g.adj(x)) {
                        if (!vDists.containsKey(y)) {
                            int dist = vDist + 1;
                            vDists.put(y, dist);
                            vQ.add(y);
                        }
                    }
                }
            }
            if (!wQ.isEmpty()) {
                int x = wQ.remove();
                int wDist = wDists.get(x);
                if (vDists.containsKey(x)) {
                    int length = vDists.get(x) + wDist;
                    if (length < min) {
                        min = length;
                        ancestor = x;
                    }
                }
                if (wDist < min) {
                    for (int y : g.adj(x)) {
                        if (!wDists.containsKey(y)) {
                            int dist = wDists.get(x) + 1;
                            wDists.put(y, dist);
                            wQ.add(y);
                        }
                    }
                }
            }
        }
        if (min != Integer.MAX_VALUE) {
            return new int[] {min, ancestor};
        }
        // No common ancestor.
        return new int[] {-1, -1};
    }
}
