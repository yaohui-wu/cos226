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
     * Length of shortest ancestral path between V and W; -1 if no such path.
     */
    public int length(int v, int w) {
        validateArgs(v, w);
        validateVertices(v, w);
        return -1;
    }
 
    /**
     * A common ancestor of V and W that participates in a shortest ancestral
     * path; -1 if no such path.
     */
    public int ancestor(int v, int w) {
        validateArgs(v, w);
        validateVertices(v, w);
        return -1;
    }
 
    /**
     * Length of shortest ancestral path between any vertex in V and any
     * vertex in W; -1 if no such path.
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateArgs(v, w);
        validateIter(v);
        validateIter(w);
        return -1;
    }
 
    /**
     * A common ancestor that participates in shortest ancestral path; -1 if
     * no such path.
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateArgs(v, w);
        validateIter(v);
        validateIter(w);
        return -1;
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
}
