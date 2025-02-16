import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int numSegments;
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validateArg(points);
        numSegments = 0;
        List<LineSegment> lines = findLines(points);
        segments = new LineSegment[numSegments];
        for (int i = 0; i < numSegments; i += 1) {
            segments[i] = lines.get(i);
        }
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

    private List<LineSegment> findLines(Point[] points) {
        List<LineSegment> lines = new ArrayList<>();
        int length = points.length;
        for (int i = 0; i < length; i += 1) {
            for (int j = 1; j < length; j += 1) {
                for (int k = 2; k < length; k += 1) {
                    for (int l = 3; l < length; l += 1) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                        LineSegment line = getLine(p, q, r, s);
                        if (line != null) {
                            lines.add(line);
                            numSegments += 1;
                        }
                    }
                }
            }
        }
        return lines;
    }

    private boolean isCollinear(Point p1, Point p2, Point p3) {
        Comparator<Point> comparator = p1.slopeOrder();
        return comparator.compare(p2, p3) == 0;
    }

    private LineSegment getLine(Point p, Point q, Point r, Point s) {
        if (isCollinear(p, q, r) && isCollinear(q, r, s)) {
            Point[] collinearPoints = {p, q, r, s};
            Arrays.sort(collinearPoints);
            Point min = collinearPoints[0];
            Point max = collinearPoints[collinearPoints.length - 1];
            if (min.compareTo(max) != 0) {
                LineSegment line = new LineSegment(min, max);
                return line;
            }
        }
        return null;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
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
 