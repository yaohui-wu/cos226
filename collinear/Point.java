/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        int dx = that.x - x; // Change in x.
        int dy = that.y - y; // Change in y.
        if (dx == 0 && dy == 0) {
            // Same point.
            return Double.NEGATIVE_INFINITY;
        } else if (dx == 0) {
            // Vertical line.
            return Double.POSITIVE_INFINITY;
        } else if (dy == 0) {
            // Horizontal line.
            return +0.0;
        }
        double slope = (double) dy / dx;
        return slope;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (y == that.y) {
            return Integer.compare(x - that.x, 0);
        }
        return Integer.compare(y - that.y, 0);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (p1, p2) -> {
            // Slope comparison using only integer arithmetic.
            int x1 = p1.x;
            int y1 = p1.y;
            int x2 = p2.x;
            int y2 = p2.y;
            if (x1 == x && y1 == y) {
                // Two points are the same as this point.
                if (x2 == x && y2 == y) {
                    return 0;
                }
                // Only the first point is the same as this point.
                return -1;
            }
            if (x2 == x && y2 == y) {
                // Only the second point is the same as this point.
                return 1;
            }
            // Vertical line cases
            boolean p1Vertical = (x1 == x);
            boolean p2Vertical = (x2 == x);
            if (p1Vertical && p2Vertical) {
                return 0;
            }
            if (p1Vertical) {
                return 1;
            }
            if (p2Vertical) {
                return -1;
            }

            // Horizontal line cases
            boolean p1Horizontal = (y1 == y);
            boolean p2Horizontal = (y2 == y);
            if (p1Horizontal && p2Horizontal) {
                return 0;
            }
            int denominator1 = x1 - x;
            int denominator2 = x2 - x;
            int cross1 = (y1 - y) * denominator2;
            int cross2 = (y2 - y) * denominator1;
            if (p1Horizontal) {
                return Integer.compare(0, cross2);
            }
            if (p2Horizontal) {
                return Integer.compare(cross1, 0);
            }
            /*
             * Compare slopes using cross product to avoid floating point
             * arithmetic.
             */
            if (denominator1 * denominator2 < 0) {
                // Flip the inequality when the denominator signs differ.
                return Integer.compare(cross2, cross1);
            }
            return Integer.compare(cross1, cross2);
        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}
