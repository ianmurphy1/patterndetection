package setimplementation;

import Point.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

import java.awt.Color;
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

    /**
     *
     * @param a
     */
    public void run(String a) {
        //Stopwatch stopwatch = new Stopwatch();
        StdDraw.show(0);
        Point[] points = getPoints(a);
        for (int i = 0; i < points.length; i++) {
            List<SortedSet<Point>> theseLines = createLines(points[i], Arrays.copyOfRange(points, i + 1, points.length));
            if (theseLines.size() > 0) lines.addAll(theseLines);
        }
        //removeDups();
        //drawLines();
        //StdDraw.show(0);
        //StdDraw.save("outputs/" + a.replace(".txt", ".png"));
        //StdOut.println("Number of lines: " + lines.size());
        //StdOut.println("Done in: " + stopwatch.elapsedTime() + "s");
        //StdOut.println("Op Count: " + count);
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
                }  // If ln contains items in lne, remove it from list.
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
            StdDraw.setPenColor(Color.getHSBColor((float) Math.random(), .8f, .8f));
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
     * @param rest
     * @return
     */
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
                if (Double.compare(slope1, slope2) == 0) {
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
            //points[i].draw();
            i++;
        }
        //StdOut.println("Number of points: " + n);
       // StdOut.println("Min: " + min);
       // StdOut.println("Max: " + max);
        //StdDraw.setScale(min, max * 1.1);
      //  double rad = StdDraw.getPenRadius();
      //  StdDraw.setPenRadius(rad * 1.5);
      //  StdDraw.setPenColor(StdDraw.BOOK_RED);
     //   StdDraw.line(min, 0, max, 0);
     //   StdDraw.line(0, min, 0, max);
     //   StdDraw.setPenColor(StdDraw.BLACK);
     //   StdDraw.setPenRadius(rad);
     //   StdDraw.text(max * 1.05, 0, "x");
     //   StdDraw.text(15, max * 1.05, "y");
        return points;
    }
}
