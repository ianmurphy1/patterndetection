package hashmapimplementation;

import Point.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.SortedSet;
import java.util.Collections;

/**
 *
 */
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
        det.run("input56.txt");
	}

    /**
     *
     * @param s
     */
    public void run(String s) {
        Stopwatch stopwatch = new Stopwatch();
        StdDraw.show(0);
        Point[] points = getPoints(s);
        for (int i = 0; i < points.length; i++) {
            createLines(points[i], Arrays.copyOfRange(points, i + 1, points.length));
        }
        drawLines();
        StdDraw.show(0);
        StdDraw.save("outputs/" + s.replace(".txt", ".png"));
        StdOut.println("Number of lines: " + lines.size());
        StdOut.println("Done in: " + stopwatch.elapsedTime() + "s");
        StdOut.println("Op Count: " + count);
    }

    /**
     *
     */
    private void drawLines() {
        for (Map.Entry<String, SortedSet<Point>> line: lines.entrySet()) {
            StdDraw.setPenColor(Color.getHSBColor((float) Math.random(), .8f, .8f));
            line.getValue().first().drawTo(line.getValue().last());
            StdDraw.setPenColor(StdDraw.BLACK);
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

    /**
     *
     * @param p
     * @param points
     */
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
                    String key = equationOfLine(slope1, points[i]) ; //Create key for map
                    if (line.size() > 3) { //If line has 4 or more points try adding to map.
                        if (!lines.containsKey(key)) lines.put(key, line);
                        else if (lines.containsKey(key) && lines.get(key).size() < line.size()) lines.put(key, line);
                        //Check if a set with a key exists, if it doesn't put it into map
                        //if the map has entry with the same key check if its the biggest, if it is bigger
                        //than the current entry, add it otherwise leave the current one there.
                    }
                    if (j == points.length - 1) i = points.length;
                } else { //if not equal, might as well stop checking as array is sorted by slope.
                    i = j - 1;
                    break;
                }
            }
        }
    }

    /**
     *
     * @param file
     * @return
     */
    private Point[] getPoints(String file) {
        In in = new In("inputs/" + file);
        if (!in.exists()) System.exit(1);
        int n = in.readInt();
        Point[] points = new Point[n];
        double min, max;
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
        int i = 0;
        while (i < n) {
            int x = in.readInt(), y = in.readInt();
            double tMax, tMin;
            tMax = Math.max(x, y);
            tMin = Math.min(x, y);
            if (tMax > max) max = tMax;
            if (tMin < min) min = tMin;
            points[i] = new Point(x, y);
            points[i].draw();
            i++;
        }
        StdOut.println("Number of points: " + n);
        StdOut.println("Min: " + min);
        StdOut.println("Max: " + max);
        StdDraw.setScale(min, max * 1.1);
        double rad = StdDraw.getPenRadius();
        StdDraw.setPenRadius(rad * 1.5);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        StdDraw.line(min, 0, max, 0);
        StdDraw.line(0, min, 0, max);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(rad);
        StdDraw.text(max * 1.05, 0, "x");
        StdDraw.text(15, max * 1.05, "y");
        return points;
    }

    /**
     *
     * @param slope
     * @param p
     * @return
     */
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
