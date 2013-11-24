package detection;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

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
        det.run("points.txt");
	}

    private void run(String s) {
        Point[] points = getPoints(s);
        createLines();
    }

    private void createLines() {

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
        System.out.println("Number of points: " + n);
        System.out.println("Max X: " + xMax);
        System.out.println("Max Y: " + yMax);
        System.out.println("Min X: " + xMin);
        System.out.println("Min Y: " + yMin);
        //System.out.println("Number of lines: " + lines.size());
        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);
        return points;
    }

}
