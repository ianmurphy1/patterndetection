package arraylistimplementation;

import Point.Point;
import edu.princeton.cs.introcs.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *@author
 */
public class LineDetector {

    private ArrayList<ArrayList<Point>> lines;
    private int count;

    public LineDetector() {
        lines = new ArrayList<ArrayList<Point>>();
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
     * @param s
     */
    public void run(String s) {
        Stopwatch stopwatch = new Stopwatch();
        StdDraw.show(0); //Turn off animation
        Point[] points = getPoints(s);
        for (int i = 0; i < points.length; i++) {
            ArrayList<ArrayList<Point>> lns = createLines(points[i], Arrays.copyOfRange(points, (i + 1), points.length));
            if (lns.size() > 0) lines.addAll(lns);
        }
        StdAudio.play("inputs/success.wav");
        removeDups();
        drawLines();
        StdDraw.show(0); //Turn it back on and show all lines and points
        StdDraw.save("outputs/" + s.replace(".txt", ".png"));
        StdOut.println("Number of lines: " + lines.size());
        StdOut.println("Done in: " + stopwatch.elapsedTime() + "s");
        StdOut.println("Op Count: " + count);
    }

    /**
     *
     */
    private void removeDups() {
        for (int i = 0; i < lines.size(); i++) {
            ArrayList<Point> ln = lines.get(i);
            for (int j = i + 1; j < lines.size() - 1; j++) {
                ArrayList<Point> lne = lines.get(j);
                if (ln.containsAll(lne)) {
                    lines.remove(lne);
                    j--;
                }
            }
        }
    }

    /**
     *
     */
    private void drawLines() {
        for (int i = 0; i < lines.size(); i++) {
            ArrayList<Point> ln = lines.get(i);
            Collections.sort(ln);
            StdDraw.setPenColor(Color.getHSBColor((float) Math.random(), .8f, .8f));
            Collections.min(ln).drawTo(Collections.max(ln));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdOut.print(ln.size() + ": ");
            double rad = StdDraw.getPenRadius();
            StdDraw.setPenRadius(rad * 3);
            for (Point p : ln) {
                StdOut.print(p);
                if (!p.equals(Collections.max(ln))) StdOut.print(" -> ");
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
    private ArrayList<ArrayList<Point>> createLines(Point p, Point[] points) {
        ArrayList<ArrayList<Point>> temps = new ArrayList<ArrayList<Point>>();
        Arrays.sort(points, p.SLOPE_ORDER);
        double slope1, slope2;
        for (int i = 0; i < points.length - 2; i++) {
            ArrayList<Point> line = new ArrayList<Point>();
            line.add(p);
            line.add(points[i]);
            slope1 = p.slopeTo(points[i]);
            for (int j = i + 1; j < points.length; j++) {
                slope2 = p.slopeTo(points[j]);
                count++;
                if (Double.compare(slope1, slope2) == 0) {
                    line.add(points[j]);
                    if (j == points.length - 1) i = points.length;
                } else {
                    i = j - 1;
                    break;
                }
            }
            if (line.size() > 3) temps.add(line); //Size of line is bigger that 3, add it to list being returned.
        }
        return temps;
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
