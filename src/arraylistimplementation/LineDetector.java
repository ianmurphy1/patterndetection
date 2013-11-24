package arraylistimplementation;

import Point.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.Stopwatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    public void run(String a) {
        Stopwatch stopwatch = new Stopwatch();
        Point[] points = getPoints(a);

        for (int i = 0; i < points.length; i++) {
            points[i].draw();
            ArrayList<ArrayList<Point>> lns = createLines(points[i], Arrays.copyOfRange(points, (i + 1), points.length));
            if (lns.size() > 0) lines.addAll(lns);
        }

        removeDups();
        drawLines();
        StdOut.println("Number of lines: " + lines.size());
        StdOut.println("Done in: " + stopwatch.elapsedTime() + "s");
        StdOut.println("Op Count: " + count);
    }

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

    private void drawLines() {
        for (int i = 0; i < lines.size(); i++) {
            ArrayList<Point> ln = lines.get(i);
            Collections.sort(ln);
            Collections.min(ln).drawTo(Collections.max(ln));
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

    private ArrayList<ArrayList<Point>> createLines(Point p, Point[] points) {
        ArrayList<ArrayList<Point>> temps = new ArrayList<ArrayList<Point>>();
        Arrays.sort(points, p.SLOPE_ORDER);

       // for (int i = 0; i < points.length; i++) {
      //      System.out.println(p + "'s slope to " + points[i] + ": " + p.slopeTo(points[i]));
       // }

        double slope1, slope2;

        for (int i = 0; i < points.length - 2; i++) {
            ArrayList<Point> line = new ArrayList<Point>();
            line.add(p);
            line.add(points[i]);
            slope1 = p.slopeTo(points[i]);
            for (int j = i + 1; j < points.length; j++) {
                slope2 = p.slopeTo(points[j]);
                count++;
                if (slope1 == slope2) {
                    //printLine(line);
                    line.add(points[j]);
                    if (j == points.length - 1) i = points.length;
                } else {
                    i = j - 1;
                    break;
                }
            }
            if (line.size() > 3) {
                //printLine(line);
                temps.add(line);
            }
        }

        return temps;
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
            i++;
        }

        System.out.println("Number of points: " + n);
        System.out.println("Max X: " + xMax);
        System.out.println("Max Y: " + yMax);
        System.out.println("Min X: " + xMin);
        System.out.println("Min Y: " + yMin);
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);

        return points;
    }
}
