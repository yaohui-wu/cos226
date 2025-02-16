import java.util.ArrayList;
import java.util.List;

public class FastCollinearPoints {
    private int numSegments;
    private LineSegments[] segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {}

    // the number of line segments
    public int numberOfSegments() {}

    // the line segments
    public LineSegment[] segments() {}

        private void validateArg(Point[] points) {
        boolean valid = true;
        if (points == null) {
            valid = false;
        }
        List<Point> uniquePoints = new ArrayList<>();
        for (Point point : points) {
            if (point == null || uniquePoints.contains(point)) {
                valid = false;
            } else {
                uniquePoints.add(point);
            }
        }
        if (!valid) {
            String error = "Invalid argument";
            throw new IllegalArgumentException(error);
        }
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
 