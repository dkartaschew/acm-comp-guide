
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

public class ConvexHull {

  private double costPost = 1.0;
  private double costFencePerM = 5.0;
  ArrayList<Point> points;
  PriorityQueue<Point> pointsQueue;
  Stack<Point> convexHull;
  Point anchor;

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
    convexHull.push(anchor);
    convexHull.push(pointsQueue.poll());
    int i = 1;
    while (!pointsQueue.isEmpty()) {
      Point pt1 = convexHull.pop();
      Point pt2 = convexHull.peek();
      convexHull.push(pt1);
      Point pt3 = pointsQueue.poll();
      if (turnsLeft(pt1, pt2, pt3)) {
        convexHull.push(pt3);
      } else {
        convexHull.pop();
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

    @Override
    public int compareTo(Point point) {
      // Determine the angle from the anchor to this vs point o.
      double anglePoint = getAngle(point);
      double angleThis = getAngle(this);
      if (anglePoint > angleThis) {
        return 1;
      } else if (anglePoint < angleThis) {
        return -1;
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
      if (point1.y < point1.y) {
        return -1;
      } else if (point1.y > point1.y) {
        return 1;
      } else if (point1.x < point1.x) {
        return -1;
      } else if (point1.x > point1.x) {
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
   * Determines if there is a left turn between 2 points.
   */
  private boolean turnsLeft(Point p1, Point p2, Point p3) {
    int result = (p3.x - p2.x) * (p1.y - p2.y) - (p3.y - p2.y) * (p1.x - p2.x);
    if (result > 0) {
      return true;
    }
    return false;
  }

  double calculateFenceCost() {
    double numPosts = convexHull.size();
    double metersOfFence = 0;

    Point first = convexHull.peek();
    Point previous = convexHull.pop();

    while (!convexHull.isEmpty()) {
      Point next = (Point) convexHull.pop();
      metersOfFence += vectorLength(findVector(previous, next));
      previous = next;
    }

    metersOfFence += vectorLength(findVector(previous, first)); // join fence back to start

    return (costPost * numPosts) + (costFencePerM * metersOfFence);
  }
}
