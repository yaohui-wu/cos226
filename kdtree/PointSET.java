import java.util.ArrayList;
import java.util.List;

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
    private final SET<Point2D> pointSet;

    /**
     * Constructs an empty set of points
     */
    public PointSET() {
        pointSet = new SET<>();
    }

    /**
     * Returns true if the set is empty.
     */
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    /**
     * Returns the number of points in the set.
     */
    public int size() {
        return pointSet.size();
    }

    /**
     * Adds the point to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        validateArg(p);
        if (!pointSet.contains(p)) {
            pointSet.add(p);
        }
    }

    /**
     * Returns true if the set contains the point P.
     */
    public boolean contains(Point2D p) {
        validateArg(p);
        return pointSet.contains(p);
    }

    /**
     * Draws all points to standard draw.
     */
    public void draw() {
        for (Point2D point : pointSet) {
            point.draw();
        }
    }

    /**
     * Returns an iterable of all points that are inside the rectangle (or on
     * the boundary).
     */
    public Iterable<Point2D> range(RectHV rect) {
        validateArg(rect);
        List<Point2D> points = new ArrayList<>();
        for (Point2D point : pointSet) {
            if (rect.contains(point)) {
                points.add(point);
            }
        }
        return points;
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
        Point2D nearest = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (Point2D point : pointSet) {
            double dist = p.distanceSquaredTo(point);
            if (dist < minDist) {
                minDist = dist;
                nearest = point;
            }
        }
        return nearest;
    }

    private static void validateArg(Object obj) {
        if (obj == null) {
            String error = "Argument cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Unit testing of the methods.
     */
    public static void main(String[] args) {
        PointSET pointSet = new PointSET();
        Point2D p1 = new Point2D(0.7, 0.2);
        Point2D p2 = new Point2D(0.5, 0.4);
        Point2D p3 = new Point2D(0.2, 0.3);
        Point2D p4 = new Point2D(0.4, 0.7);
        Point2D p5 = new Point2D(0.9, 0.6);
        pointSet.insert(p1);
        pointSet.insert(p2);
        pointSet.insert(p3);
        pointSet.insert(p4);
        pointSet.insert(p5);
        System.out.println(pointSet.size());
        System.out.println(pointSet.contains(p1));
        System.out.println(pointSet.contains(p2));
        System.out.println(pointSet.contains(p3));
        System.out.println(pointSet.contains(p4));
        System.out.println(pointSet.contains(p5));
        System.out.println(pointSet.contains(new Point2D(0.1, 0.2)));
        pointSet.draw();
        RectHV rect = new RectHV(0.1, 0.1, 0.5, 0.5);
        for (Point2D point : pointSet.range(rect)) {
            System.out.println(point);
        }
        System.out.println(pointSet.nearest(new Point2D(0.1, 0.2)));
    }
}
