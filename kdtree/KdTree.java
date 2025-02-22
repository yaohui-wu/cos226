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
        if (root == null) {
            root = new Node(p);
            root.rect = new RectHV(0, 0, 1, 1);
            size += 1;
            return;
        }
        root = insert(root, p, root.rect, false);
    }

    private Node insert(Node node, Point2D p, RectHV rect, boolean compareY) {
        if (node == null) {
            node = new Node(p);
            node.rect = rect;
            size += 1;
            return node;
        }
        Point2D rootPoint = node.p;
        if (p.equals(rootPoint)) {
            return node;
        }
        double key = p.x();
        double rootKey = rootPoint.x();
        if (compareY) {
            key = p.y();
            rootKey = rootPoint.y();
        }
        double xmin = node.rect.xmin();
        double ymin = node.rect.ymin();
        double xmax = node.rect.xmax();
        double ymax = node.rect.ymax();
        if (key < rootKey) {
            if (!compareY) {
                xmax = node.p.x();
            } else {
                ymax = node.p.y();
            }
            rect = new RectHV(xmin, ymin, xmax, ymax);
            node.leftBottom = insert(node.leftBottom, p, rect, !compareY);
        } else {
            if (!compareY) {
                xmin = node.p.x();
            } else {
                ymin = node.p.y();
            }
            rect = new RectHV(xmin, ymin, xmax, ymax);
            node.rightTop = insert(node.rightTop, p, rect, !compareY);
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
        Point2D rootPoint = node.p;
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
        draw(root, true);
    }

    private void draw(Node node, boolean splitV) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.p.draw();
        StdDraw.setPenRadius();
        if (splitV) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        draw(node.leftBottom, !splitV);
        draw(node.rightTop, !splitV);
    }

    // all points that are inside the rect (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validateArg(rect);
        List<Point2D> points = new ArrayList<>();
        range(root, rect, points);
        return points;
    }

    private void range(Node node, RectHV rect, List<Point2D> points) {
        if (node == null || !rect.intersects(node.rect)) {
            return;
        }
        if (rect.contains(node.p)) {
            points.add(node.p);
        }
        range(node.leftBottom, rect, points);
        range(node.rightTop, rect, points);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validateArg(p);
        if (isEmpty()) {
            return null;
        }
        Point2D nearest = nearest(p, root.p, root, false);
        return nearest;
    }

    private Point2D nearest(Point2D p, Point2D nearest, Node node, boolean compareY) {
        if (node != null) {
            double minDist = nearest.distanceSquaredTo(p);
            double rectDist = node.rect.distanceSquaredTo(p);
            if (minDist < rectDist) {
                return nearest;
            }
            double dist = node.p.distanceSquaredTo(p);
            if (dist < minDist) {
                nearest = node.p;
            }
            int cmp = compare(p, node.p, compareY);
            if (cmp < 0) {
                nearest = nearest(p, nearest, node.leftBottom, !compareY);
                nearest = nearest(p, nearest, node.rightTop, !compareY);
            } else {
                nearest = nearest(p, nearest, node.rightTop, !compareY);
                nearest = nearest(p, nearest, node.leftBottom, !compareY);
            }
        }
        return nearest;
    }

    private int compare(Point2D p1, Point2D p2, boolean compareY) {
        int cmp = Double.compare(p1.x(), p2.x());
        if (compareY) {
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
        private final Point2D p; // the point
        // the axis-aligned rect corresponding to this node
        private RectHV rect;
        private Node leftBottom; // the left/bottom subtree
        private Node rightTop; // the right/top subtree

        public Node(Point2D point) {
            p = point;
            rect = null;
            leftBottom = null;
            rightTop = null;
        }
    }

    // unit testing of the methods (optional)
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
