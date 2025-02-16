import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class BruteCollinearPoints {
    private int numSegments;
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        validateArg(points);
        numSegments = 0;
        segments = new LineSegment[numSegments];
        List<LineSegment> lines = findLines(points);
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
                        Comparator<Point> comparator = p.slopeOrder();
                        if (comparator.compare(q, r) == 0
                            && comparator.compare(r, s) == 0) {
                            LineSegment line = new LineSegment(p, q);
                            lines.add(line);
                            numSegments += 1;
                        }
                    }
                }
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
 }
 