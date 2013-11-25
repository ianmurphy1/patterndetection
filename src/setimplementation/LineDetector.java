package setimplementation;


import Point.Point;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;



/**
 * @author Ian Murphy - 20057028
 *         Date: 25/11/13
 */
public class LineDetector {
    private ArrayList<SortedSet<Point>> lines;
    private int count;
    public LineDetector() {
        lines = new ArrayList<SortedSet<Point>>();
        count = 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        LineDetector det = new LineDetector();
        det.run("points.txt");
    }

    public void run(String a) {
        Stopwatch stopwatch = new Stopwatch();
        Point[] points = getPoints(a);

        for (int i = 0; i < points.length; i++) {
            points[i].draw();
            List<SortedSet<Point>> theseLines = createLines(points[i], Arrays.copyOfRange(points, i + 1, points.length));
            if (theseLines.size() > 0) lines.addAll(theseLines);
        }

        removeDups();
        drawLines();
        StdOut.println("Number of lines: " + lines.size());
        StdOut.println("Done in: " + stopwatch.elapsedTime() + "s");
        StdOut.println("Op Count: " + count);
    }

    private void removeDups() {
        for (int i = 0; i < lines.size(); i++) {
            SortedSet<Point> ln = lines.get(i);
            for (int j = i + 1; j < lines.size() - 1; j++) {
                SortedSet<Point> lne = lines.get(j);
                if (ln.containsAll(lne)) {
                    lines.remove(lne);
                    j--;
                }  // If line has a subset of
            }
        }
    }

    private void drawLines() {
        for (int i = 0; i < lines.size(); i++) {
            SortedSet<Point> ln = lines.get(i);

            ln.first().drawTo(ln.last());
            StdOut.print(ln.size() + ": ");
            double rad = StdDraw.getPenRadius();
            StdDraw.setPenRadius(rad * 3);
            for (Point p: ln) {
                StdOut.print(p);
                if (!p.equals(ln.last())) StdOut.print(" -> ");
                else StdOut.print("\n");
                p.draw();
            }
            StdDraw.setPenRadius(rad);
        }
    }

    private List<SortedSet<Point>> createLines(Point p, Point[] rest) {
        Arrays.sort(rest, p.SLOPE_ORDER);
        List<SortedSet<Point>> theseLines = new ArrayList<SortedSet<Point>>();
        SortedSet<Point> line = new TreeSet<Point>();
        double slope1, slope2;

        for (int i = 0; i < rest.length - 2; i++) {
            line.add(p);
            line.add(rest[i]);
            slope1 = p.slopeTo(rest[i]);
            for (int j = i + 1; j < rest.length; j++) {
                slope2 = p.slopeTo(rest[j]);
                count++;
                if (slope1 == slope2) {
                    line.add(rest[j]);
                    if (line.size() > 3) theseLines.add(line);
                    if (j == rest.length - 1) i = rest.length;
                } else {
                    i = j - 1;
                    break;
                }
            }
        }
        return theseLines;
    }

    private Point[] getPoints(String file) {

        In in = new In("inputs/" + file);

        if (!in.exists()) System.exit(1);

        int n = in.readInt();
        Point[] points = new Point[n];

        double xMin, xMax, yMin, yMax;
        xMax = xMin = yMin = yMax = 0d;
        int i = 0;
        while (i < n) {
            int x = in.readInt(), y = in.readInt();
            if (x < xMin) xMin = x;
            if (x > xMax) xMax = x;
            if (y < yMin) yMin = y;
            if (y > yMax) yMax = y;
            points[i] = new Point(x, y);
            i++;
        }

        StdOut.println("Number of points: " + n);
        StdOut.println("Max X: " + xMax);
        StdOut.println("Max Y: " + yMax);
        StdOut.println("Min X: " + xMin);
        StdOut.println("Max Y: " + yMin);
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);
        return points;
    }
}
