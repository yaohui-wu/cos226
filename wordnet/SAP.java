import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     private final Map<Integer, int[]> sapMap;

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
        int key = key(v, w);
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
        int key = key(v, w);
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
        int key = key(v, w);
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
        int key = key(v, w);
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

    private int key(int v, int w) {
        Set<Integer> set = new HashSet<>();
        set.add(v);
        set.add(w);
        return set.hashCode();
    }

    private int key(Iterable<Integer> v, Iterable<Integer> w) {
        Set<Integer> set = new HashSet<>();
        for (int x : v) {
            set.add(x);
        }
        for (int x : w) {
            set.add(x);
        }
        return set.hashCode();
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
        Map<Integer, Integer> vDist = new HashMap<>(); // Distance from v.
        Map<Integer, Integer> wDist = new HashMap<>(); // Distance from w.
        Deque<Integer> q = new ArrayDeque<>();
        // Initialize distances for v and w.
        for (int x : v) {
            vDist.put(x, 0);
            q.add(x);
        }
        for (int x : w) {
            if (vDist.containsKey(x)) {
                // There is a common vertex.
                return new int[] {0, x};
            }
            wDist.put(x, 0);
            q.add(x);
        }
        final int INF = Integer.MAX_VALUE; // Represents infinity.
        int min = INF;
        int ancestor = -1;
        /*
         * Run breadth-first searches (BFS) from v and w alternating back and
         * forth between exploring vertices in each of the two searches.
         */
        while (!q.isEmpty()) {
            int x = q.remove();
            if (vDist.containsKey(x) && wDist.containsKey(x)) {
                int length = vDist.get(x) + wDist.get(x);
                if (length < min) {
                    min = length;
                    ancestor = x;
                }
            }
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
            return new int[] {min, ancestor};
        }
        // No common ancestor.
        return new int[] {-1, -1};
    }
}
