import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int numSegments;
    private LineSegment[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
            Point p = points[i];
            Point[] sortedPoints = new Point[length - 1];
            int index = 0;
            for (int j = 0; j < length; j += 1) {
                if (i != j) {
                    sortedPoints[index] = points[j];
                    index += 1;
                }
            }
            Arrays.sort(sortedPoints, p.slopeOrder());
            int count = 1;
            Point first = sortedPoints[0];
            double prevSlope = p.slopeTo(first);
            int sortedLength = sortedPoints.length;
            for (int k = 1; k < sortedLength; k += 1) {
                double slope = p.slopeTo(sortedPoints[k]);
                if (slope == prevSlope) {
                    count += 1;
                } else {
                    if (count >= 3) {
                        Point q = sortedPoints[k - 1];
                        LineSegment line = new LineSegment(p, q);
                        lines.add(line);
                        numSegments += 1;
                    }
                    count = 1;
                    first = sortedPoints[k];
                }
                prevSlope = slope;
            }
            if (count >= 3) {
                Point q = sortedPoints[sortedLength - 1];
                LineSegment line = new LineSegment(p, q);
                lines.add(line);
                numSegments += 1;
            }
        }
        return lines;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
 }
 