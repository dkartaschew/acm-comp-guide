
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class LineSegmentIntersectionTreeMap {

  /**
   * Main
   */
  public static void main(String[] args) {
    new LineSegmentIntersectionTreeMap().run();
  }

  /**
   * Main runner.
   */
  public void run() {
    int testcase = 1;
    ArrayList<Segment> segments;

    Scanner scn = new Scanner(System.in);
    String line = scn.nextLine();

    // Keep looping until we get a # by itself on a line.
    while (line.compareTo("#") != 0) {
      segments = new ArrayList<Segment>();
      // Read in our segments.
      while (line.compareTo("#") != 0) {
        String[] values = line.split(" ");
        Point start = new Point();
        Point end = new Point();
        start.x = Double.parseDouble(values[0]);
        start.y = Double.parseDouble(values[1]);
        end.x = Double.parseDouble(values[2]);
        end.y = Double.parseDouble(values[3]);
        segments.add(new Segment(start, end));
        line = scn.nextLine();
      }
      // Process the segments
      //List<Point> intersections = Naive(segments);
      List<Point> intersections = BentleyOttmann(segments);
      Collections.sort(intersections); // Sort the points.

      // Output the corner information.
      System.out.printf("Test Case %d:\n%d corners\n", testcase++, intersections.size());
      for (Point p : intersections) {
        System.out.println(p.toString());
      }
      line = scn.nextLine();
    }
  }

  /**
   * Naive approach to finding intersections from n-segments.
   *
   * @param segments A list of segments to consider.
   * @return A list of points of intersection.
   */
  public List<Point> Naive(List<Segment> segments) {
    ArrayList<Point> intersections = new ArrayList<Point>();
    for (int i = 0; i < segments.size(); i++) {
      for (int j = 0; j < segments.size(); j++) {
        Point intersection = GetLineIntersection(segments.get(i), segments.get(j));
        if (intersection != null && !intersections.contains(intersection)) {
          intersections.add(intersection);
        }
      }
    }
    return intersections;
  }

  /**
   * Perform a line sweep on the enclosed segments.
   *
   * @param segments A list of segments to consider.
   * @return A list of points of intersection.
   */
  public List<Point> BentleyOttmann(List<Segment> segments) {
    ArrayList<Point> intersections = new ArrayList<Point>();
    PriorityQueue<Event> eventQueue = new PriorityQueue<Event>();
    Sweepline sweepline = new Sweepline();

    // Add all our endpoints to the event queue.
    for (Segment segment : segments) {
      eventQueue.add(new Event(segment, segment.start));
      eventQueue.add(new Event(segment, segment.end));
    }

    // Continue while we have item in queue.
    while (!eventQueue.isEmpty()) {
      Event event = eventQueue.poll();  // Get our next event.

      if (event.segment == null) {
        // Intersection.
        // Add the point to the known intersections.
        intersections.add(event.point);
        
        // Get the segment above and below the current event point.
        Segment segE1 = sweepline.above(event.point);
        Segment segE2 = sweepline.below(event.point);
        Segment segA = sweepline.prev(segE1);
        Segment segB = sweepline.next(segE2);
        if (segA != null && segE2 != null) {
          addIntersection(eventQueue, segA, segE2);
        }
        if (segB != null && segE1 != null) {
          addIntersection(eventQueue, segB, segE1);
        }

      } else if (event.point == event.segment.start) {
        // event is a line start.
        eventTrigger = event.point.x;
        sweepline.add(event.segment);
        System.out.printf("x = %.2f  -> ", eventTrigger);
        System.out.println(sweepline.getLine());
        Segment segE = event.segment;
        Segment segA = sweepline.prev(segE);
        Segment segB = sweepline.next(segE);

        if (segA != null) {
          addIntersection(eventQueue, segA, segE);
        }
        if (segB != null) {
          addIntersection(eventQueue, segB, segE);
        }

      } else {
        // event is a line end
        eventTrigger = event.point.x;
        Segment segE = event.segment;
        Segment segA = sweepline.prev(segE);
        Segment segB = sweepline.next(segE);
        sweepline.remove(event.segment);
        if (segA != null && segB != null) {
          addIntersection(eventQueue, segA, segB);
        }
        System.out.printf("x = %.2f  -> ", eventTrigger);
        System.out.println(sweepline.getLine());
      }
    }
    return intersections;
  }

  /**
   * Add in intersection to the event queue, if an intersection exists between 2
   * segments.
   *
   * @param eventQueue The event queue.
   * @param segA The first segment
   * @param segB The second segment
   */
  public void addIntersection(PriorityQueue<Event> eventQueue, Segment segA, Segment segB) {
    Point segBIntersection = GetLineIntersection(segA, segB);
    if (segBIntersection != null) {
      Event segBEvent = new Event(null, segBIntersection);
      if (!eventQueue.contains(segBEvent)) {
        eventQueue.add(segBEvent);
      }
    }
  }

  /**
   * Generic class to hold an event to be used by BentleyOttmann algorithm.
   */
  public class Event implements Comparable<Event> {

    public Point point;
    public Segment segment;

    /**
     * Constructor
     *
     * @param seg The Segment related to the event.
     * @param pt The point which will trigger the event.
     */
    public Event(Segment seg, Point pt) {
      point = pt;
      segment = seg;
    }

    /**
     * Comparable method, that compares the event point.
     */
    @Override
    public int compareTo(Event o) {
      return point.compareTo(o.point);
    }
  }

  /**
   * Generic class to hold a line segment.
   */
  public class Segment implements Comparable<Segment> {

    public Point start;
    public Point end;

    /**
     * Main Constructor. Note: Points are sorted based on x location, so that
     * the start point is always to the left of the end point.
     *
     * @param start The start point
     * @param end The end point
     */
    public Segment(Point start, Point end) {
      if (end.compareTo(start) < 0) {
        // swap them.
        this.start = end;
        this.end = start;
        return;
      }
      this.start = start;
      this.end = end;
    }

    /**
     * Comparable method, that compares the event point y location at
     * intersection with the sweep line
     */
    @Override
    public int compareTo(Segment o2) {
      // Compare y positions of the segment at the x co-ordinate of the
      // event trigger.
      double seg1 = getY(this);
      double seg2 = getY(o2);
      System.out.printf("seg1 (%s) = %.2f ; seg2 (%s) = %.2f\n", this, seg1, o2, seg2);
      if (seg1 > seg2) {
        return 1;
      } else if (seg1 < seg2) {
        return -1;
      }
      // Same location, so lets determine by slope.
      seg1 = getSlope(this);
      seg2 = getSlope(o2);
      if (seg1 > seg2) {
        return 1;
      } else if (seg1 < seg2) {
        return -1;
      }
      return 0; // Same y and same slope!
    }

    @Override
    public String toString() {
      return String.format("\"S: %.0f, %.0f, E: %.0f, %.0f\"", start.x, start.y, end.x, end.y);
    }
  }

  /**
   * Get the y intersection of a segment to the sweep line.
   *
   * @param segment The segment to test against.
   * @return The y location.
   */
  public double getY(Segment segment) {
    // Determine slope of segment.
    double slopeO2 = getSlope(segment);
    // Determine y of segment
    return slopeO2 * eventTrigger + (segment.start.y - slopeO2 * segment.start.x);
  }

  /**
   * Get the slope of the segment.
   *
   * @param segment The segment whose slope is needed.
   * @return The slope of the segment.
   */
  public double getSlope(Segment segment) {
    return (segment.end.y - segment.start.y) / (segment.end.x - segment.start.x);
  }
  /**
   * X location of the event trigger (the location of the sweep line).
   */
  private static double eventTrigger;

  /**
   * Generic class to hold the segments that the sweep line intersects. 
   */
  public class Sweepline {

    private TreeSet<Segment> sweepline;

    public Sweepline() {
      sweepline = new TreeSet<Segment>();
    }

    /**
     * Add segment to the list
     */
    public void add(Segment segment) {
      sweepline.add(segment);
    }

    /**
     * Remove a segment from the list
     */
    public void remove(Segment segment) {
      sweepline.remove(segment);
    }

    /**
     * Get the entire list of segments in the sweep line.
     *
     * @return
     */
    public TreeSet<Segment> getLine() {
      return sweepline;
    }

    /**
     * Get the segment next to the current segment, or null if none exist.
     */
    public Segment next(Segment segment) {
      if (segment == null) {
        return null;
      }
      return sweepline.higher(segment);
    }

    /**
     * Get the segment previous to the current segment, or null is none exist.
     */
    public Segment prev(Segment segment) {
      if (segment == null) {
        return null;
      }
      return sweepline.lower(segment);
    }

    /**
     * Get the segment above the point indicated.
     */
    private Segment above(Point point) {
      if (point == null) {
        return null;
      }
      return sweepline.higher(new Segment(point, point));
    }

    /**
     * Get the segment below the point indicated.
     */
    private Segment below(Point point) {
      if (point == null) {
        return null;
      }
      return sweepline.lower(new Segment(point, point));
    }
  }

  /**
   * Generic class to hold a point
   */
  public class Point implements Comparable<Point> {

    public double x = 0.0;
    public double y = 0.0;

    @Override
    public int compareTo(Point o) {
      if (o.x == this.x) {
        double temp = o.y - this.y;
        if (temp < 0) {
          return 1;
        } else if (temp == 0) {
          return 0;
        }
        return -1;
      }
      double temp = o.x - this.x;
      if (temp < 0) {
        return 1;
      } else if (temp == 0) {
        return 0;
      }
      return -1;
    }

    @Override
    public String toString() {
      return String.format("%.2f %.2f", x, y);
    }
  }

  /**
   * Determine if 2 lines intersect, and provide intersection point.
   *
   * @param Line1 The vector of the first line
   * @param Line2 The vector of the second line
   * @return Point if there is an intersection, otherwise null.
   */
  public Point GetLineIntersection(Segment Line1, Segment Line2) {
    Point result = new Point();
    // Precalculate some points;
    double x4_x3 = (Line2.end.x - Line2.start.x);
    double x2_x1 = (Line1.end.x - Line1.start.x);
    double y4_y3 = (Line2.end.y - Line2.start.y);
    double y2_y1 = (Line1.end.y - Line1.start.y);
    double x1_x3 = (Line1.start.x - Line2.start.x);
    double y1_y3 = (Line1.start.y - Line2.start.y);

    // Get the demonitator
    double dem = (y4_y3 * x2_x1) - (x4_x3 * y2_y1);

    // Determine U values.
    double Ua = ((x4_x3 * y1_y3) - (y4_y3 * x1_x3)) / dem;
    double Ub = ((x2_x1 * y1_y3) - (y2_y1 * x1_x3)) / dem;

    // Ensure our U values are in scope
    if (Ua < 0 || Ua > 1 || Ub < 0 || Ub > 1) {
      return null;
    }

    // Calculate the intersection point.
    result.x = Line1.start.x + Ua * (x2_x1);
    result.y = Line1.start.y + Ua * (y2_y1);

    if (Double.isNaN(result.x) || Double.isNaN(result.y)) {
      return null;
    }

    return result;
  }
}