Assignment 2 (50%)
======================
B.Sc.* Year 2, Algorithms 2013
-------------------------------
1. Pattern Detection
-----------------------------------

Write a program to recognise line patterns in a given set of points as follows: Given a set of N distinct points in the plane, find every line segment that connects a subset of 4 or more of the points.

Your skeleton program classes are here - please do not change the name/methods of this class.

You will need to complete the following:
 * The compareTo() method should compare points by their y-coordinates, breaking ties by their x-coordinates. Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
 * The slopeTo() method should return the slope between the invoking point (x0, y0) and the argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0). Treat the slope of a horizontal line segment as positive zero; treat the slope of a vertical line segment as positive infinity; treat the slope of a degenerate line segment (between a point and itself) as negative infinity.
 * The SLOPE_ORDER comparator should compare points by the slopes they make with the invoking point (x0, y0). Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0). Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.
The “brute force” approach to this problem will have a growth rate of N4. A more efficient sorting-based solution is as follows: Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.
Think of p as the origin.
For each other point q, determine the slope it makes with p.
Sort the points according to the slopes they makes with p.
Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear(on the same line).

The program should take, from the command line, a data file consisting of an integer N, followed by N pairs of integers (x, y) between 0 and 30000. An example can be found here.

Output format. Print the line segments that your program discovers in the format below (number of collinear points in the line segment, followed by the points).

5: (14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)

Also, plot the points and the line segments using standard draw. Using the hashmapimplementation.Point.Point data type supplied, p.draw() draws the point p and p.drawTo(q) draws the line segment from p to q. Before drawing, scale the coordinate system so that coordinates between 0 and 30000 fit in the graphics window. Write suitable JUnit test cases to test your algorithm.
