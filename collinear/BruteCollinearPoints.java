import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int numSegments;
    private List<LineSegment> lines;

    /**
     * Finds all line segments containing 4 points.
     */
    public BruteCollinearPoints(Point[] points) {
        validateArg(points);
        numSegments = 0;
        lines = new ArrayList<>();
        findLines(points);
    }

    private void validateArg(Point[] points) {
        if (points == null) {
            error();
        }
        List<Point> uniquePoints = new ArrayList<>();
        for (Point point : points) {
            if (point == null || uniquePoints.contains(point)) {
                error();
            }
            uniquePoints.add(point);
        }
    }

    private void error() {
        String error = "Invalid argument";
        throw new IllegalArgumentException(error);
    }

    private void findLines(Point[] points) {
        int length = points.length;
        for (int i = 0; i < length - 3; i += 1) {
            for (int j = i + 1; j < length - 2; j += 1) {
                for (int k = j + 1; k < length - 1; k += 1) {
                    Point p1 = points[i];
                    Point p2 = points[j];
                    Point p3 = points[k];
                    // Check if 3 points are collinear.
                    if (isCollinear(p1, p2, p3)) {
                        /*
                         * Check the fourth point to form a line segment if
                         * they are collinear.
                         */
                        for (int n = k + 1; n < length; n += 1) {
                            Point p4 = points[n];
                            addLine(p1, p2, p3, p4);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if three points are collinear.
     */
    private boolean isCollinear(Point p1, Point p2, Point p3) {
        Comparator<Point> comparator = p1.slopeOrder();
        return comparator.compare(p2, p3) == 0;
    }

    /**
     * Adds a line segment if 4 points are collinear and the maximum and
     * minimum points are distinct.
     */
    private void addLine(Point p1, Point p2, Point p3, Point p4) {
        if (isCollinear(p1, p2, p4)) {
            Point[] collinearPoints = {p1, p2, p3, p4};
            Arrays.sort(collinearPoints);
            Point minPoint = collinearPoints[0];
            Point maxPoint = collinearPoints[collinearPoints.length - 1];
            if (minPoint.compareTo(maxPoint) != 0) {
                LineSegment line = new LineSegment(minPoint, maxPoint);
                lines.add(line);
                numSegments += 1;
            }
        }
    }

    /**
     * Returns the number of line segments.
     */
    public int numberOfSegments() {
        return numSegments;
    }

    /**
     * Returns the line segments.
     */
    public LineSegment[] segments() {
        return lines.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }
 