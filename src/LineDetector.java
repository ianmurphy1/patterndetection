import detection.Point;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

import java.util.*;


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
        det.run("points.txt");
    }

    public void run(String a) {
        Point[] points = getPoints(a);

        for (int i = 0; i < points.length; i++) {
            ArrayList<ArrayList<Point>> lns = createLines(points[i], Arrays.copyOfRange(points, (i + 1), points.length));

            if (lns.size() > 0) lines.addAll(lns);
        }

        //removeDups();
        //drawLines();

        System.out.println("Line count is: " + lines.size());
        System.out.println("Count is: " + count);

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
        System.out.println("Lines: " + lines.size());

        for (int i = 0; i < lines.size(); i++) {
            ArrayList<Point> ln = lines.get(i);
            Collections.min(ln).drawTo(Collections.max(ln));
            for (Point p : ln) {
                    StdOut.print(p);
                    if (!p.equals(Collections.max(ln))) StdOut.print(" -> ");
                    p.draw();
            }
        }
    }

    private ArrayList<ArrayList<Point>> createLines(Point p, Point[] points) {
        ArrayList<ArrayList<Point>> temps = new ArrayList<ArrayList<Point>>();
        Arrays.sort(points, p.SLOPE_ORDER);
        double slope1, slope2;

        for (int i = 0; i < points.length; i++) {
            ArrayList<Point> line = new ArrayList<Point>();
            //System.out.println("Create Lines Method: " + line.size());
            line.add(p);
            line.add(points[i]);
            printLine(line);
            slope1 = p.slopeTo(points[i]);
            System.out.println("Added: " + p + " to line.");
            for (int j = i + 1; j < points.length; j++) {
                slope2 = p.slopeTo(points[j]);
                int slopeComp = Double.compare(slope1, slope2);
                System.out.println(points[j]);
                count++;
                boolean comp = slope1 == slope2;
                System.out.println("Slope 1: " + slope1 + ", Slope 2: " + slope2 + ", Comparison: " + comp);
                if (slope1 == slope2) {
                    line.add(points[j]);
                    printLine(line);
                }
                System.out.println("Line size " + line.size());
               //     if (j == points.length - 1) i = points.length;
             //   } else {
            //        i = j - 1;
            //        break;
            //    }
            }

            System.out.println("Create Lines Method: " + line.size());
            if (line.size() > 2) {
                temps.add(line);
            }
        }

        return temps;
    }

    private void printLine(ArrayList<Point> line) {
        Collections.sort(line);
        Collections.min(line).drawTo(Collections.max(line));
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
