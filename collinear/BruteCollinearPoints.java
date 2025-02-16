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

    // the number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // the line segments
    public LineSegment[] segments() {}
 }
 