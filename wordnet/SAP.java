import java.util.ArrayDeque;
import java.util.Deque;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Shortest ancestral path (SAP).
 */
public final class SAP {
    private final Digraph g;

    /**
     * Constructor takes a digraph (not necessarily a DAG).
     */
    public SAP(Digraph G) {
        validateArgs(G);
        g = new Digraph(G);
    }
 
    /**
     * Length of shortest ancestral path between v and w; -1 if no such path.
     */
    public int length(int v, int w) {
        validateArgs(v, w);
        validateVertices(v, w);
        Cache cache = new Cache();
        cache.findSAP(v, w);
        int length = cache.length;
        return length;
    }
 
    /**
     * A common ancestor of v and w that participates in a shortest ancestral
     * path; -1 if no such path.
     */
    public int ancestor(int v, int w) {
        validateArgs(v, w);
        validateVertices(v, w);
        Cache cache = new Cache();
        cache.findSAP(v, w);
        int ancestor = cache.ancestor;
        return ancestor;
    }
 
    /**
     * Length of shortest ancestral path between any vertex in v and any
     * vertex in w; -1 if no such path.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateArgs(v, w);
        validateIter(v);
        validateIter(w);
        Cache cache = new Cache();
        for (int i : v) {
            for (int j : w) {
                cache.findSAP(i, j);
            }
        }
        int length = cache.length;
        return length;
    }
 
    /**
     * A common ancestor that participates in shortest ancestral path; -1 if
     * no such path.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateArgs(v, w);
        validateIter(v);
        validateIter(w);
        Cache cache = new Cache();
        for (int i : v) {
            for (int j : w) {
                cache.findSAP(i, j);
            }
        }
        int ancestor = cache.ancestor;
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

    private void validateVertices(int... vertices) {
        int maxV = g.V() - 1;
        for (int v : vertices) {
            if (v < 0 || v > maxV) {
                String error = "Vertex must be between 0 and " + maxV;
                throw new IllegalArgumentException(error);
            }
        }
    }

    private void validateIter(Iterable<Integer> iter) {
        for (Integer i : iter) {
            if (i == null) {
                String error = "Iterable cannot contain null items";
                throw new IllegalArgumentException(error);
            }
        }
    }

    private class Cache {
        private int length;
        private int ancestor;

        public Cache() {
            length = -1;
            ancestor = -1;
        }

        public void findSAP(int v, int w) {
            if (v == w) {
                length = 0;
                ancestor = v;
                return;
            }
            int order = g.V();
            final int INF = Integer.MAX_VALUE;
            int[] vDist = new int[order];
            int[] wDist = new int[order];
            for (int i = 0; i < order; i += 1) {
                vDist[i] = INF;
                wDist[i] = INF;
            }
            Deque<Integer> q = new ArrayDeque<>();
            vDist[v] = 0;
            q.add(v);
            while (!q.isEmpty()) {
                int x = q.remove();
                for (int y : g.adj(x)) {
                    if (vDist[y] == INF) {
                        vDist[y] = vDist[x] + 1;
                        q.add(y);
                    }
                }
            }
            int min = INF;
            wDist[w] = 0;
            q.add(w);
            while (!q.isEmpty()) {
                int x = q.remove();
                if (vDist[x] != INF) {
                    int len = vDist[x] + wDist[x];
                    if (len < min) {
                        min = len;
                        ancestor = x;
                    }
                }
                for (int y : g.adj(x)) {
                    if (wDist[y] == INF) {
                        wDist[y] = wDist[x] + 1;
                        q.add(y);
                    }
                }
            }
            if (min != INF) {
                length = min;
            }
        }
    }
}
