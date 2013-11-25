package hashmapimplementation;

import Point.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Collections;


public class LineDetector {

    private Map<String, SortedSet<Point>> lines;
    private int count;

    public LineDetector() {
        lines = new HashMap<String, SortedSet<Point>>();
        count = 0;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        LineDetector det = new LineDetector();
        det.run("input10000.txt");
	}

    private void run(String s) {
        Stopwatch stopwatch = new Stopwatch();
        Point[] points = getPoints(s);

        for (int i = 0; i < points.length; i++) {
            createLines(points[i], Arrays.copyOfRange(points, i + 1, points.length));
        }
        drawLines();
        StdOut.println("Number of lines: " + lines.size());
        StdOut.println("Done in: " + stopwatch.elapsedTime() + "s");
        StdOut.println("Op Count: " + count);
    }

    private void drawLines() {

        for (Map.Entry<String, SortedSet<Point>> line: lines.entrySet()) {
            line.getValue().first().drawTo(line.getValue().last());
            double rad = StdDraw.getPenRadius();
            StdDraw.setPenRadius(rad * 3);
            StdOut.print(line.getValue().size() + ": ");
            for (Point p: line.getValue()) {
                StdOut.print(p);
                if (!p.equals(Collections.max(line.getValue()))) StdOut.print(" -> ");
                else StdOut.print("\n");
                p.draw();
            }
            StdDraw.setPenRadius(rad);
        }
    }

    private void createLines(Point p, Point[] points) {
            Arrays.sort(points, p.SLOPE_ORDER);
            double slope1, slope2;
            for (int i = 0; i < points.length - 2; i++) {
            SortedSet<Point> line = new TreeSet<Point>();
            line.add(p);
            line.add(points[i]);
            slope1 = p.slopeTo(points[i]);
            for (int j = i + 1; j < points.length; j++) {
                slope2 = p.slopeTo(points[j]);
                count++;
                if (Double.compare(slope1, slope2) == 0) {
                    line.add(points[j]);
                    String key = equationOfLine(slope1, points[i]) ;
                    //System.out.println("Key is: " + key);
                    if (line.size() > 3) {
                        if (!lines.containsKey(key) ) lines.put(key, line);
                        else if (lines.containsKey(key) && lines.get(key).size() < line.size()) lines.put(key, line);
                    }
                    if (j == points.length - 1) i = points.length;
                } else {
                    i = j - 1;
                    break;
                }
            }
        }
    }

    private Point[] getPoints(String file) {

        In in = new In("inputs/" + file);
        if (!in.exists()) System.exit(1);
        int n = in.readInt();
        Point[] points = new Point[n];
        double xMin, xMax, yMin, yMax;
        xMax = yMax = Double.MIN_VALUE;
        xMin = yMin = Double.MAX_VALUE;
        int i = 0;
        while (i < n) {
            int x = in.readInt(), y = in.readInt();
            if (x < xMin) xMin = x;
            if (x > xMax) xMax = x;
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
            points[i] = new Point(x, y);
            points[i].draw();
            i++;
        }
        StdOut.println("Number of points: " + n);
        StdOut.println("Max X: " + xMax);
        StdOut.println("Max Y: " + yMax);
        StdOut.println("Min X: " + xMin);
        StdOut.println("Min Y: " + yMin);
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);
        return points;
    }

    private String equationOfLine(double slope, Point p) {
        double y, x, m, b;
        y = p.getY();
        x = p.getX();
        m = slope;
        b = (m*x) - y;
        if (slope == Double.POSITIVE_INFINITY)
            return "x = " + x;
        return ("y = " + slope + "x + " + Double.toString(b));
    }
}