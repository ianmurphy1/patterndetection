import edu.princeton.cs.introcs.StdDraw;

import java.util.Arrays;
import java.util.Comparator;


public class Point implements Comparable<Point> {

    // compare points by slope
    public static final Comparator<Point> SLOPE_ORDER = new BySlope();       // YOUR DEFINITION HERE



    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        if (this.y == that.y) return 0d;

        double dx = that.x - this.x, dy = that.y - this.x;

        return dy/dx;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if ((this.y < that.y)) return -1;

        if (this.y == that.y) {
            if (this.x == that.x) return 0;
            if (this.x < that.x) return -1;
        }

        return +1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public static class BySlope implements Comparator<Point> {
        Point p0;

        @Override
        public int compare(Point p1, Point p2) {
            double slope1 = p0.slopeTo(p1), slope2 = p0.slopeTo(p2);

            if (slope1 > slope2) return +1;
            else if (slope1 < slope2) return -1;
            else return 0;
        }
    }

    // unit test
    public static void main(String[] args) {
        Point[] points = new Point[10];

        Arrays.sort(points, 1, points.length - 1, Point.SLOPE_ORDER);

        //Then use this to start sort????
    }

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}