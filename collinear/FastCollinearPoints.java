import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int numSegments;
    private List<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        validateArg(points);
        numSegments = 0;
        segments = findSegments(points);
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

    private List<LineSegment> findSegments(Point[] points) {
        segments = new ArrayList<>();
        int length = points.length;
        Point[] pointsCopy = points.clone();
        for (int i = 0; i < length; i += 1) {
            Point p = points[i];
            Point[] sortedPoints = pointsCopy.clone();
            Arrays.sort(sortedPoints, p.slopeOrder());
            int count = 1;
            int start = 1;
            double prevSlope = p.slopeTo(sortedPoints[1]);
            for (int j = 2; j < length; j += 1) {
                double slope = p.slopeTo(sortedPoints[j]);
                if (Double.compare(slope, prevSlope) == 0) {
                    count += 1;
                } else {
                    if (count >= 3) {
                        addSegment(p, sortedPoints, start, j - 1);
                    }
                    count = 1;
                    start = j;
                }
                prevSlope = slope;
            }
            if (count >= 3) {
                addSegment(p, sortedPoints, start, length - 1);
            }    
        }
        return segments;
    }

    private void addSegment(Point p, Point[] sortedPoints, int start, int end) {
        Point minPoint = p;
        Point maxPoint = p;
        for (int i = start; i <= end; i += 1) {
            Point q = sortedPoints[i];
            if (q.compareTo(minPoint) < 0) {
                minPoint = q;
            }
            if (q.compareTo(maxPoint) > 0) {
                maxPoint = q;
            }
        }
        if (p == minPoint) {
            LineSegment segment = new LineSegment(minPoint, maxPoint);
            segments.add(segment);
            numSegments += 1;
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
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
 