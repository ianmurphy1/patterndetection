package detection;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.util.*;

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
        det.run("input80.txt");
	}

    private void run(String s) {
        Point[] points = getPoints(s);

        for (int i = 0; i < points.length; i++) {
            createLines(points[i], Arrays.copyOfRange(points, i + 1, points.length));
        }
        drawLines();
    }

    private void drawLines() {
        for (Map.Entry line: lines.entrySet()) {
            System.out.println(line.getKey() + ", " + line.getValue());
        }
    }


    private void createLines(Point p, Point[] points) {
        Arrays.sort(points, p.SLOPE_ORDER);
        double slope1, slope2;
        for (int i = 0; i < points.length; i++) {
            SortedSet<Point> line = new TreeSet<Point>();
            line.add(p);
            line.add(points[i]);
            slope1 = p.slopeTo(points[i]);
            for (int j = 0; j < points.length - 1; j++) {
                slope2 = p.slopeTo(points[j]);
                if (slope1 == slope2) {
                    line.add(points[j]);
                    String slope = Double.toString(slope1);
                    if (!lines.containsKey(slope)) lines.put(slope, line);
                    else if (lines.containsKey(slope) && lines.get(slope).size() < line.size()) lines.put(slope, line);
                    if (j == points.length - 1) i = points.length;
                } else {
                    i = j - 1;
                    break;
                }
            }
        }

        System.out.println(lines.size());
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
