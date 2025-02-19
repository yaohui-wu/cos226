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
        root = insert(root, p, false);
    }

    private Node insert(Node node, Point2D point, boolean compareY) {
        if (node == null) {
            size += 1;
            return new Node(point);
        }
        Point2D rootPoint = node.point;
        if (point.equals(rootPoint)) {
            return node;
        }
        double key = point.x();
        double rootKey = rootPoint.x();
        if (compareY) {
            key = point.y();
            rootKey = rootPoint.y();
        }
        if (key < rootKey) {
            node.leftBottom = insert(node.leftBottom, point, !compareY);
        } else {
            node.rightTop = insert(node.rightTop, point, !compareY);
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validateArg(p);
        return search(root, p, false);
    }

    private boolean search(Node node, Point2D point, boolean compareY) {
        if (node == null) {
            return false;
        }
        Point2D rootPoint = node.point;
        if (point.equals(rootPoint)) {
            return true;
        }
        double key = point.x();
        double rootKey = rootPoint.x();
        if (compareY) {
            key = point.y();
            rootKey = rootPoint.y();
        }
        int comparison = Double.compare(key, rootKey);
        if (comparison < 0) {
            return search(node.leftBottom, point, !compareY);
        } else {
            return search(node.rightTop, point, !compareY);
        }
    }

    // draw all points to standard draw
    public void draw() {
        // TODO: implement this method.
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validateArg(rect);
        // TODO: implement this method.
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validateArg(p);
        // TODO: implement this method.
        return null;
    }

    private static void validateArg(Object obj) {
        if (obj == null) {
            String error = "Argument cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    private static class Node {
        private Point2D point; // the point
        private Node leftBottom; // the left/bottom subtree
        private Node rightTop; // the right/top subtree
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;

        public Node(Point2D p) {
            point = p;
            leftBottom = null;
            rightTop = null;
            rect = null;
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {}
}
