import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;


public class LineDetector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LineDetector det = new LineDetector();

        det.run(args[0]);
	}

    public void run(String a) {
        Point[] points = getPoints(a);


    }

    private Point[] getPoints(String file) {

        In in = new In(file);

        if (!in.exists()) System.exit(1);

        int n = in.readInt();
        Point[] points = new Point[n];

        double xMin, xMax, yMin, yMax;
        xMax = xMin = yMin = yMax = 0d;

        int i = 0;

        while (in.hasNextLine()) {
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
