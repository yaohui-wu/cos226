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
        root = insert(root, p, root.rect, true);
    }

    private Node insert(Node node, Point2D point, RectHV rectangle, boolean compareX) {
        if (node == null) {
            Node newNode = new Node(point);
            newNode.rect = rectangle;
            size += 1;
            return newNode;
        }
        Point2D rootPoint = node.point;
        if (point.equals(rootPoint)) {
            return node;
        }
        double key = point.x();
        double rootKey = rootPoint.x();
        if (!compareX) {
            key = point.y();
            rootKey = rootPoint.y();
        }
        double xmin = rectangle.xmin();
        double ymin = rectangle.ymin();
        double xmax = rectangle.xmax();
        double ymax = rectangle.ymax();
        if (key < rootKey) {
            if (compareX) {
                xmax = node.point.x();
            } else {
                ymax = node.point.y();
            }
            rectangle = new RectHV(xmin, ymin, xmax, ymax);
            node.leftBottom = insert(node.leftBottom, point, rectangle, !compareX);
        } else {
            if (compareX) {
                xmin = node.point.x();
            } else {
                ymin = node.point.y();
            }
            rectangle = new RectHV(xmin, ymin, xmax, ymax);
            node.rightTop = insert(node.rightTop, point, rectangle, !compareX);
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
        draw(root, true);
    }

    private void draw(Node node, boolean splitV) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        StdDraw.setPenRadius();
        if (splitV) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        draw(node.leftBottom, !splitV);
        draw(node.rightTop, !splitV);
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
        private final Point2D point; // the point
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        private Node leftBottom; // the left/bottom subtree
        private Node rightTop; // the right/top subtree

        public Node(Point2D p) {
            point = p;
            leftBottom = null;
            rightTop = null;
            rect = null;
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
        System.out.println(kdTree.contains(new Point2D(0.9, 1.0)));
        kdTree.draw();
        RectHV rect = new RectHV(0.1, 0.2, 0.5, 0.6);
        for (Point2D point : kdTree.range(rect)) {
            System.out.println(point);
        }
        System.out.println(kdTree.nearest(new Point2D(0.1, 0.2)));
    }
}
