
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class ConvexHull {

  private double costPost = 1.0;
  private double costFencePerM = 5.0;
  ArrayList<Point> points;  // general list of points in the set.
  PriorityQueue<Point> pointsQueue; // The points sorted by radial angle from the anchor.
  Stack<Point> convexHull; // The resultant convex hull
  Point anchor; // The anchor point.

  /**
   * Main
   */
  public static void main(String[] args) {
    new ConvexHull().run();
  }

  /**
   * Main runner.
   */
  public void run() {
    Scanner scn = new Scanner(System.in);
    int numberOfTestCases = scn.nextInt();

    while (numberOfTestCases-- > 0) {

      int numberOfPoint = scn.nextInt();
      points = new ArrayList<Point>(numberOfPoint);
      pointsQueue = new PriorityQueue<Point>(numberOfPoint);
      convexHull = new Stack<Point>();

      // read in all our points.
      while (numberOfPoint-- > 0) {
        points.add(new Point(scn.nextInt(), scn.nextInt()));
      }

      // Sort our list of points, based on y and x values;
      Collections.sort(points, new PointSort());
      anchor = points.get(0); // Get the start of the list, this is our anchor.

      // Add the points to the priority queue.
      for (Point point : points) {
        pointsQueue.add(point); // These are sorted based on angle between anchor and point.
      }

      // Don't bother building a convex hull with 3 or less points.
      if (points.size() > 3) {
        grahamScan();
      } else {
        // Just add the points to the convex hull...
        while (!pointsQueue.isEmpty()) {
          convexHull.push(pointsQueue.poll());
        }
      }
      System.out.printf("$%.2f\n", calculateFenceCost());
    }
  }

  /**
   * Perform a scan of the points queue, building the convex hull.
   */
  private void grahamScan() {
    convexHull.push(pointsQueue.poll()); // Add our anchor point to the convex hull
    convexHull.push(pointsQueue.poll()); // Add our next point to the convex hull
    while (!pointsQueue.isEmpty()) {
      Point pt2 = convexHull.pop(); // Get our prev point
      Point pt1 = convexHull.peek();  // Get our curr point
      convexHull.push(pt2);
      Point pt3 = pointsQueue.poll(); // Get out next point
      //System.out.printf("Test Points: (%s), (%s), (%s)\n", pt1, pt2, pt3);
      if (turnsLeft(pt1, pt2, pt3)) {
        convexHull.push(pt3); // Add our last point since we turned left
      } else {
        pointsQueue.add(pt3); // Add our just tested point back into the queue
        convexHull.pop(); // Get rid of the last point as we turned right.
      }
    }
  }

  /**
   * Generic class to hold a point
   */
  public class Point implements Comparable<Point> {

    public int x = 0;
    public int y = 0;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    /**
     * Comparable function based on radial angle from the anchor.
     */
    @Override
    public int compareTo(Point point) {
      // Determine the angle from the anchor to this vs point o.
      double anglePoint = getAngle(point);
      double angleThis = getAngle(this);
      if (anglePoint > angleThis) {
        return -1;
      } else if (anglePoint < angleThis) {
        return 1;
      }
      // Angles are the SAME! so determine by distance from anchor.
      anglePoint = vectorLength(findVector(anchor, point));
      angleThis = vectorLength(findVector(anchor, this));
      if (anglePoint > angleThis) {
        return -1;
      } else if (anglePoint < angleThis) {
        return 1;
      }
      return 0;
    }

    @Override
    public String toString() {
      return String.format("%d %d", x, y);
    }

    /**
     * Return the angle between the anchor (as origin) and point.
     */
    private double getAngle(Point point) {
      return Math.atan2(point.y - anchor.y, point.x - anchor.x);
    }
  }

  /**
   * Generic Comparator to sort a list by y, and x values.
   */
  private class PointSort implements Comparator<Point> {

    @Override
    public int compare(Point point1, Point point2) {
      if (point1.y < point2.y) {
        return -1;
      } else if (point1.y > point2.y) {
        return 1;
      } else if (point1.x < point2.x) {
        return -1;
      } else if (point1.x > point2.x) {
        return 1;
      }
      return 0;
    }
  }

  /**
   * Determine the vector from point 1 to point 2.
   */
  private Point findVector(Point point1, Point point2) {
    return new Point(point2.x - point1.x, point2.y - point1.y);
  }

  /**
   * Determine the vector length from origin.
   */
  private double vectorLength(Point point) {
    return Math.sqrt(Math.pow(point.x, 2) + Math.pow(point.y, 2));
  }

  /**
   * Determines if there is a left turn between 2 points. This is done by using
   * the difference in the cross product of all points.
   */
  private boolean turnsLeft(Point p1, Point p2, Point p3) {
    int result = (p3.x - p2.x) * (p1.y - p2.y) - (p3.y - p2.y) * (p1.x - p2.x);
    return (result > 0);
  }

  /**
   * Calculate the cost of the fence around the convex hull.
   *
   * @return
   */
  double calculateFenceCost() {
    double numPosts = convexHull.size();
    double metersOfFence = 0;

    Point first = convexHull.peek();
    Point previous = convexHull.pop();

    // Work around the convex hull and determine the length of the fence.
    while (!convexHull.isEmpty()) {
      Point next = (Point) convexHull.pop();
      metersOfFence += vectorLength(findVector(previous, next));
      previous = next;
    }

    // Add in the final section of fence (from the last point back to anchor).
    metersOfFence += vectorLength(findVector(previous, first)); // join fence back to start

    // Return the cost.
    return (costPost * numPosts) + (costFencePerM * metersOfFence);
  }
}
