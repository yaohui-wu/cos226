import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A mutable data type that uses a 2d-tree to represent a set of points in the
 * unit square.
 * 
 * @author Yaohui Wu
 */
public class KdTree {
    private Node root;
    private int size;

    /**
     * Constructs an empty set of points
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * Returns true if the set is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of points in the set.
     */
    public int size() {
        return size;
    }

    /**
     * Adds the point to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        validateArg(p);
        if (root == null) {
            root = new Node(p);
            root.rect = new RectHV(0, 0, 1, 1); // Unit square.
            size += 1;
            return;
        }
        root = insert(p, root, root.rect, true);
    }

    private Node insert(Point2D p, Node node, RectHV rect, boolean compareX) {
        if (node == null) {
            node = new Node(p);
            node.rect = rect;
            size += 1;
            return node;
        }
        Point2D point = node.p;
        if (p.equals(point)) {
            // The point is already in the set.
            return node;
        }
        rect = node.rect;
        double xmin = rect.xmin();
        double ymin = rect.ymin();
        double xmax = rect.xmax();
        double ymax = rect.ymax();
        int cmp = compare(p, point, compareX);
        if (cmp < 0) {
            // Left children has smaller x-coordinate or y-coordinate.
            if (compareX) {
                xmax = point.x();
            } else {
                ymax = point.y();
            }
            if (node.left == null) {
                rect = new RectHV(xmin, ymin, xmax, ymax);
            }
            node.left = insert(p, node.left, rect, !compareX);
        } else {
            // Right children has larger x-coordinate or y-coordinate.
            if (compareX) {
                xmin = point.x();
            } else {
                ymin = point.y();
            }
            if (node.right == null) {
                rect = new RectHV(xmin, ymin, xmax, ymax);
            }
            node.right = insert(p, node.right, rect, !compareX);
        }
        return node;
    }

    /**
     * Returns true if the set contains the point P.
     */
    public boolean contains(Point2D p) {
        validateArg(p);
        return search(p, root, true);
    }

    private boolean search(Point2D p, Node node, boolean compareX) {
        if (node == null) {
            return false;
        }
        Point2D point = node.p;
        if (p.equals(point)) {
            return true;
        }
        int cmp = compare(p, point, compareX);
        if (cmp < 0) {
            return search(p, node.left, !compareX);
        } else {
            return search(p, node.right, !compareX);
        }
    }

    /**
     * Draws all points to standard draw.
     */
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean vert) {
        if (node == null) {
            return;
        }
        Point2D p = node.p;
        // Draw the point in black.
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        p.draw();
        RectHV rect = node.rect;
        StdDraw.setPenRadius();
        if (vert) {
            // Draw the vertical split line in red.
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(p.x(), rect.ymin(), p.x(), rect.ymax());
        } else {
            // Draw the horizontal split line in blue.
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(rect.xmin(), p.y(), rect.xmax(), p.y());
        }
        draw(node.left, !vert);
        draw(node.right, !vert);
    }

    /**
     * Returns an iterable of all points that are inside the rectangle (or on
     * the boundary).
     */
    public Iterable<Point2D> range(RectHV rect) {
        validateArg(rect);
        List<Point2D> points = new ArrayList<>();
        range(rect, root, points, true);
        return points;
    }

    private void range(RectHV rect, Node node, List<Point2D> points, boolean vert) {
        if (node == null) {
            return;
        }
        Point2D p = node.p;
        if (rect.contains(p)) {
            points.add(p);
        }
        /*
         * Check only whether the query rectangle intersects the splitting
         * line segment.
         */
        double x = p.x();
        double y = p.y();
        int cmpLeft;
        int cmpRight;
        if (vert) {
            cmpLeft = Double.compare(x, rect.xmin());
            cmpRight = Double.compare(x, rect.xmax());
        } else {
            cmpLeft = Double.compare(y, rect.ymin());
            cmpRight = Double.compare(y, rect.ymax());
        }
        if (cmpLeft > 0 && cmpRight > 0) {
            range(rect, node.left, points, !vert);
        } else if (cmpLeft < 0 && cmpRight < 0) {
            range(rect, node.right, points, !vert);
        } else {
            range(rect, node.left, points, !vert);
            range(rect, node.right, points, !vert);
        }
    }

    /**
     * Returns the nearest neighbor in the set to point P, returns null if the
     * set is empty.
     */
    public Point2D nearest(Point2D p) {
        validateArg(p);
        if (isEmpty()) {
            return null;
        }
        Point2D nearest = nearest(p, root.p, root, true);
        return nearest;
    }

    private Point2D nearest(
        Point2D p,
        Point2D nearest,
        Node node,
        boolean compareX
    ) {
        /*
         * Search a node only if it might contain a point that is closer than
         * the best one found so far.
         */
        if (node == null) {
            return nearest;
        }
        /*
         * Compare the squares of two Euclidean distances to avoid the
         * expensive operation of taking square roots.
         */
        double minDist = nearest.distanceSquaredTo(p);
        double rectDist = node.rect.distanceSquaredTo(p);
        if (minDist < rectDist) {
            return nearest;
        }
        Point2D point = node.p;
        double dist = point.distanceSquaredTo(p);
        if (dist < minDist) {
            nearest = point;
        }
        /*
         * Always choose the subtree that is on the same side of the
         * splitting line as the query point as the first subtree to explore.
         */
        int cmp = compare(p, point, compareX);
        if (cmp < 0) {
            // Left first.
            nearest = nearest(p, nearest, node.left, !compareX);
            nearest = nearest(p, nearest, node.right, !compareX);
        } else {
            // Right first.
            nearest = nearest(p, nearest, node.right, !compareX);
            nearest = nearest(p, nearest, node.left, !compareX);
        }
        return nearest;
    }

    private int compare(Point2D p1, Point2D p2, boolean compareX) {
        int cmp;
        if (compareX) {
            cmp = Double.compare(p1.x(), p2.x());
        } else {
            cmp = Double.compare(p1.y(), p2.y());
        }
        return cmp;
    }

    private static void validateArg(Object obj) {
        if (obj == null) {
            String error = "Argument cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    private static class Node {
        private final Point2D p; // The point.
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        // The left subtree.
        private Node left;
        // The right subtree.
        private Node right;

        public Node(Point2D point) {
            p = point;
            rect = null;
            left = null;
            right = null;
        }
    }

    /**
     * Unit testing of the methods.
     */
    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);
        kdTree.insert(p1);
        kdTree.insert(p2);
        kdTree.insert(p3);
        kdTree.insert(p4);
        kdTree.insert(p5);
        System.out.println(kdTree.size());
        System.out.println(kdTree.contains(p1));
        System.out.println(kdTree.contains(p2));
        System.out.println(kdTree.contains(p3));
        System.out.println(kdTree.contains(p4));
        System.out.println(kdTree.contains(p5));
        System.out.println(kdTree.contains(new Point2D(0.1, 0.2)));
        kdTree.draw();
        RectHV rect = new RectHV(0.1, 0.1, 0.5, 0.5);
        for (Point2D point : kdTree.range(rect)) {
            System.out.println(point);
        }
        System.out.println(kdTree.nearest(new Point2D(0.1, 0.2)));
    }
}
