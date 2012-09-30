
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class LineSegmentIntersection {

  /**
   * Main
   */
  public static void main(String[] args) {
    new LineSegmentIntersection().run();
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
   * @param segments A list of segments to consider.
   * @return  A list of points of intersection.
   */
  public List<Point> Naive(List<Segment> segments){
    ArrayList<Point> intersections = new ArrayList<Point>();
    for(int i = 0; i < segments.size(); i++){
      for(int j = 0; j < segments.size(); j++){
        Point intersection = GetLineIntersection(segments.get(i), segments.get(j));
        if(intersection != null){
          intersections.add(intersection);
        }
      }
    }
    return intersections;
  }
  
   private double eventTrigger;
  
  /**
   * Perform a line sweep on the enclosed segments.
   *
   * @param segments A list of segments to consider.
   * @return A list of points of intersection.
   */
  public List<Point> BentleyOttmann(List<Segment> segments) {
    ArrayList<Point> intersections = new ArrayList<Point>();
    PriorityQueue<Event> eventQueue = new PriorityQueue<Event>();
    TreeMap<Segment, Segment> sweepline = new TreeMap<Segment, Segment>();

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

      } else if (event.point == event.segment.start) {
        // event is a line start.
        eventTrigger = event.point.x;
        sweepline.put(event.segment, event.segment);
        Segment segE = event.segment;
        Segment segA = sweepline.lowerKey(segE);
        Segment segB = sweepline.higherKey(segE);
        
        if (segA != null) {
          addIntersection(eventQueue, segA, segE);
        }
        if (segB != null) {
          addIntersection(eventQueue, segB, segE);
        }

      } else {
        // event is a line end
        eventTrigger = event.point.x;
        sweepline.remove(event.segment);
        Segment segE = event.segment;
        Segment segA = sweepline.lowerKey(segE);
        Segment segB = sweepline.higherKey(segE);
        if (segA != null && segB != null) {
          addIntersection(eventQueue, segA, segB);
        }
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

    public Event(Segment seg, Point pt) {
      point = pt;
      segment = seg;
    }

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

    @Override
    public int compareTo(Segment o2) {
      // Compare y positions of the segment at the x co-ordinate of the
      // event trigger.
      // Determine slope of o1.
      double slopeO1 = (end.y - start.y) / (end.x - start.x);
      // Determine y intercept of o1.

      // Determine y of o1.
      double posYO1 = slopeO1 * eventTrigger + (start.y - slopeO1 * start.x);

      // Determine slope of o2.
      double slopeO2 = (o2.end.y - o2.start.y) / (o2.end.x - o2.start.x);

      // Determine y of o2.
      double posYO2 = slopeO2 * eventTrigger + (o2.start.y - slopeO2 * o2.start.x);

      double result = (posYO1 - posYO2);
      if(result > 0){
        return 1;
      } else if (result < 0){
        return -1;
      }
      return 0;
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

    // Avoid overflow. Lines are near parallel in this case.
    if (dem < 0.0000001) {
      return null;
    }

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
    return result;
  }
}
