package setimplementation;

import Point.Point;
import edu.princeton.cs.introcs.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
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
        det.run("input10000.txt");
    }

    /**
     *
     * @param a
     */
    public void run(String a) {
        Stopwatch stopwatch = new Stopwatch();
        StdDraw.show(0);
        Point[] points = getPoints(a);
        for (int i = 0; i < points.length; i++) {
            ArrayList<SortedSet<Point>> theseLines = createLines(points[i], Arrays.copyOfRange(points, i + 1, points.length));
            if (theseLines.size() > 0) lines.addAll(theseLines);
        }
        StdAudio.play("inputs/success.wav");
        removeDups();
        drawLines();
        StdDraw.show(0);
        StdDraw.save("outputs/" + a.replace(".txt", ".png"));
        StdOut.println("Number of lines: " + lines.size());
        StdOut.println("Done in: " + stopwatch + "s");
        StdOut.println("Op Count: " + count);
    }

    /**
     *
     */
    private void removeDups() {
        for (int i = 0; i < lines.size(); i++) {
            SortedSet<Point> ln = lines.get(i);
            for (int j = i + 1; j < lines.size() - 1; j++) {
                SortedSet<Point> lne = lines.get(j);
                if (ln.containsAll(lne)) {
                    lines.remove(lne);
                    j--;
                }  // If ln contains all of the items in lne then lne is a subset of ln
                   // so it can be removed from the list.
                   // Minus j as removing element decrements all indexes.
            }
        }
    }

    /**
     *
     */
    private void drawLines() {
        for (int i = 0; i < lines.size(); i++) {
            SortedSet<Point> ln = lines.get(i);
            StdDraw.setPenColor(Color.getHSBColor((float) Math.random(), .8f, .8f)); //Random colour for line
            ln.first().drawTo(ln.last());
            StdDraw.setPenColor(StdDraw.BLACK);
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

    /**
     *
     * @param p
     * @param points
     * @return
     */
    private ArrayList<SortedSet<Point>> createLines(Point p, Point[] points) {
        Arrays.sort(points, p.SLOPE_ORDER);
        ArrayList<SortedSet<Point>> theseLines = new ArrayList<SortedSet<Point>>();
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
                    if (line.size() > 3) theseLines.add(line);
                    if (j == points.length - 1) i = points.length;
                } else {
                    i = j - 1;
                    break;
                }
            }
        }
        return theseLines;
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
}
