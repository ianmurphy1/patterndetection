import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.util.*;


public class LineDetector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LineDetector det = new LineDetector();
        det.run("input80.txt");
	}

    public void run(String a) {
        Point[] points = getPoints(a);

        for (int i = 0; i < points.length; i++) {
            points[i].draw();
            ArrayList<SortedSet<Point>> lines = createLines(points[i], Arrays.copyOfRange(points, i + 1, points.length));
            drawLines(lines);
        }
    }

    private void drawLines(ArrayList<SortedSet<Point>> lines) {
        for (int i = 0; i < lines.size(); i++) {
            SortedSet<Point> ln = lines.get(i);

            ln.first().drawTo(ln.last());

            for (Point p : ln) {
                p.draw();
            }
        }
    }

    private ArrayList<SortedSet<Point>> createLines(Point p, Point[] rest) {
        Arrays.sort(rest, p.SLOPE_ORDER);
        SortedSet<Point> line = new TreeSet<Point>(p.SLOPE_ORDER);
        ArrayList<SortedSet<Point>> lines = new ArrayList<SortedSet<Point>>();
        double slope1, slope2;

        for (int i = 0; i < rest.length; i++) {
            line.add(rest[i]);
            slope1 = p.slopeTo(rest[i]);
            for (int j = i + 1; j < rest.length; j++) {
                slope2 = p.slopeTo(rest[j]);
                if (slope1 == slope2) line.add(rest[j]);
            }
            if (line.size() > 3) lines.add(line);
            line.clear();
        }

        for (int i = 0; i < lines.size(); i++) {
            SortedSet<Point> ln = lines.get(i);
            for (int j = i + 1; j < lines.size() - 1; j++) {
                SortedSet<Point> lne = lines.get(j);
                if (ln.containsAll(lne)) {
                    lines.remove(lne);
                }
            }
        }

        return lines;

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

        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);
        return points;
    }

}
