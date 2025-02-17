import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int numSegments;
    private List<LineSegment> lines;

    /**
     * Finds all line segments containing 4 or more points.
     */
    public FastCollinearPoints(Point[] points) {
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
        // No line segment if less than 4 points.
        if (length < 4) {
            return;
        }
        for (int i = 0; i < length; i += 1) {
            Point p = points[i]; // Origin point.
            Point[] sortedPoints = points.clone();
            // Sort the points according to the slopes they makes with P.
            Arrays.sort(sortedPoints, p.slopeOrder());
            // Number of points with the same slope including P.
            int count = 1;
            // Index where a new group of collinear points starts.
            int start = 1;
            // Point with the initial slope.
            Point prevPoint = sortedPoints[1];
            /*
             * Iterate through the sorted array to find consecutive collinear
             * points.
             */
            for (int j = 2; j < length; j += 1) {
                Point currPoint = sortedPoints[j];
                Comparator<Point> comparator = p.slopeOrder();
                if (comparator.compare(prevPoint, currPoint) == 0) {
                    count += 1; // Extend the group.
                } else {
                    /*
                     * If any 3 (or more) adjacent points have equal slopes
                     * with respect to P, then they are collinear.
                     */
                    if (count >= 3) {
                        addLine(p, sortedPoints, start, j - 1);
                    }
                    // Reset count for the new slope group.
                    count = 1;
                    // Update the start index for the new group.
                    start = j;
                }
                // Update previous slope for the next iteration.
                prevPoint = currPoint;
            }
            // Handle the last group.
            if (count >= 3) {
                addLine(p, sortedPoints, start, length - 1);
            }    
        }
    }

    /**
     * Adds a line segment from the given collinear points.
     */
    private void addLine(Point p, Point[] sortedPoints, int start, int end) {
        Point minPoint = p;
        Point maxPoint = p;
        // Find the minimum and maximum points in the collinear group.
        for (int i = start; i <= end; i += 1) {
            Point q = sortedPoints[i];
            if (q.compareTo(minPoint) < 0) {
                minPoint = q;
            }
            if (q.compareTo(maxPoint) > 0) {
                maxPoint = q;
            }
        }
        /*
         * Ensure that each segment is added only once (by using the smallest
         * point).
         */
        if (p == minPoint) {
            LineSegment line = new LineSegment(minPoint, maxPoint);
            lines.add(line);
            numSegments += 1;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }
 