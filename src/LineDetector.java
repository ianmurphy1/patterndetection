import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.util.*;


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
        Point[] points = getPoints(a);

        for (int i = 0; i < points.length; i++) {

            points[i].draw();
            List<SortedSet<Point>> theseLines = createLines(points[i], Arrays.copyOfRange(points, i + 1, points.length));

            if (theseLines.size() > 0) lines.addAll(i, theseLines);
            System.out.println(theseLines.size());
        }

        removeDups();
        drawLines();
        System.out.println("Line count is: " + lines.size());
        System.out.println("Count is: " + count);
    }

    private void removeDups() {
        for (int i = 0; i < lines.size(); i++) {
            SortedSet<Point> ln = lines.get(i);
            for (int j = i + 1; j < lines.size() - 1; j++) {
                SortedSet<Point> lne = lines.get(j);
                if (ln.containsAll(lne)) {
                    lines.remove(lne);
                }
            }
        }
    }

    private void drawLines() {
        for (int i = 0; i < lines.size(); i++) {
            SortedSet<Point> ln = lines.get(i);

            ln.first().drawTo(ln.last());

            for (Point p : ln) {
                p.draw();
            }
        }
    }

    private List<SortedSet<Point>> createLines(Point p, Point[] rest) {
        Arrays.sort(rest, p.SLOPE_ORDER);
        List<SortedSet<Point>> theseLines = new ArrayList<SortedSet<Point>>();
        SortedSet<Point> line = new TreeSet<Point>();
        double slope1, slope2;

        for (int i = 0; i < rest.length; i++) {
            line.add(p);
            line.add(rest[i]);
            slope1 = p.slopeTo(rest[i]);
            for (int j = i + 1; j < rest.length; j++) {
                slope2 = p.slopeTo(rest[j]);
                count++;
                if (slope1 == slope2) {
                    line.add(rest[j]);
                    if (j == rest.length - 1) i = rest.length;
                } else {
                    i = j - 1;
                    break;
                }
            }
            if (line.size() > 3) theseLines.add(line);
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

        System.out.println("Number of points: " + n);
        System.out.println("Max X: " + xMax);
        System.out.println("Max Y: " + yMax);
        System.out.println("Min X: " + xMin);
        System.out.println("Max Y: " + yMin);

        //System.out.println("Number of lines: " + lines.size());


        StdDraw.setXscale(xMin, xMax);
        StdDraw.setYscale(yMin, yMax);
        return points;
    }

}
