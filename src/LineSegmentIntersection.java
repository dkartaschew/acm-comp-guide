
public class LineSegmentIntersection {

  /**
   * Generic class to hold a point.
   */
  public class Point {

    public double x = 0.0;
    public double y = 0.0;
  }

  /**
   * Determine if 2 lines intersect, and provide intersection point.
   *
   * @param Line1 The vector of the first line
   * @param Line2 The vector of the second line
   * @return Point if there is an intersection, otherwise null.
   */
  public Point GetLineIntersection(Point[] Line1, Point[] Line2) {
    Point result = new Point();
    // Precalculate some points;
    double x4_x3 = (Line2[1].x - Line2[0].x);
    double x2_x1 = (Line1[1].x - Line1[0].x);
    double y4_y3 = (Line2[1].y - Line2[0].y);
    double y2_y1 = (Line1[1].y - Line1[0].y);
    double x1_x3 = (Line1[0].x - Line2[0].x);
    double y1_y3 = (Line1[0].y - Line2[0].y);

    // Get the demonitator
    double dem = (y4_y3 * x2_x1) - (x4_x3 * y2_y1);
    
    // Avoid overflow. Lines are parallel in this case.
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
    result.x = Line1[0].x + Ua * (x2_x1);
    result.y = Line1[0].y + Ub * (y2_y1);
    return result;
  }
}
