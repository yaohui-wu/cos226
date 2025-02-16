import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private int numSegments;
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validateArg(points);
    }

    private void validateArg(Point[] points) {
        if (points == null) {
            error();
        }
        List<Point> uniquePoints = new ArrayList<>();
        for (Point point : points) {
            if (point == null || uniquePoints.contains(point)) {
                error();
            } else {
                uniquePoints.add(point);
            }
        }
    }

    private void error() {
        String error = "Invalid argument";
        throw new IllegalArgumentException(error);
    }

    private void findLines(Point[] points) {
        int length = points.length;
        for (int i = 0; i < length; i += 1) {
            for (int j = 1; j < length; j += 1) {
                for (int k = 2; k < length; k += 1) {
                    for (int l = 3; l < length; l += 1) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[l];
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
 }
 