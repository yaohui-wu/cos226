import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

/**
 * A mutable data type that represents a set of points in the unit square,
 * implemented using a red-black binary search tree (BST).
 * 
 * @author Yaohui Wu
 */
public class PointSET {
    private SET<Point2D> pointSet;

    // construct an empty set of points
    public PointSET() {
        pointSet = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validateArg(p);
        if (!pointSet.contains(p)) {
            pointSet.insert(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validateArg(p);
        return pointSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : pointSet) {
            point.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validateArg(rect);
        SET<Point2D> points = new SET<>();
        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                points.insert(point);
            }
        }
        return points;
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

    // unit testing of the methods (optional)
    public static void main(String[] args) {}
}
