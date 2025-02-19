import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * A mutable data type that uses a 2d-tree to represent a set of points in the
 * unit square.
 * 
 * @author Yaohui Wu
 */
public class KdTree {
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validateArg(p);
        Node node = new Node(p);
        insert(root, node, false);
    }

    private void insert(Node root, Node node, boolean compareY) {
        if (root == null) {
            root = node;
        }
        double rootKey = root.p.x();
        double nodeKey = node.p.x();
        if (compareY) {
            rootKey = root.p.y();
            nodeKey = node.p.y();
        }
        if (nodeKey < rootKey) {
            root = root.lb;
        } else {
            root = root.rt;
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validateArg(p);
    }

    // draw all points to standard draw
    public void draw() {}

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validateArg(rect);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validateArg(p);
    }

    private static void validateArg(Object obj) {
        if (obj == null) {
            String error = "Argument cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    private static class Node {
        private Point2D p; // the point
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        private Node lb; // the left/bottom subtree
        private Node rt; // the right/top subtree

        public Node(Point2D point) {
            p = point;
            rect = null;
            lb = null;
            rt = null;
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {}
}
