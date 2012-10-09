
import java.util.ArrayList;
import java.util.Scanner;

public class Taxi {

  final int BLOCK_SIZE = 200; // meters
  ArrayList<Vertex> taxis; // Graph X
  ArrayList<Vertex> people; // Grpah Y
  int numPeople;
  int numTaxis;
  int taxiSpeed;
  int timeToCollect;

  /**
   * Main
   */
  public static void main(String[] args) {
    new Taxi().run();
  }

  /**
   * Main runner.
   */
  public void run() {
    Scanner sc = new Scanner(System.in);

    int numCases = sc.nextInt(); // Number of test cases
    for (int ncase = 1; ncase <= numCases; ncase++) {
      numPeople = sc.nextInt(); // number of persons
      numTaxis = sc.nextInt(); // number of taxis
      taxiSpeed = sc.nextInt(); // speed of taxis (m/s)
      timeToCollect = sc.nextInt(); // time limit to collect (seconds)

      taxis = new ArrayList<Vertex>();
      people = new ArrayList<Vertex>();

      // read in our people locations
      while (numPeople-- > 0) {
        int x = sc.nextInt();
        int y = sc.nextInt();
        people.add(new Vertex(x, y));
      }

      // read in our taxi locations
      while (numTaxis-- > 0) {
        int x = sc.nextInt();
        int y = sc.nextInt();
        taxis.add(new Vertex(x, y));
      }
      // Create edges between people and taxis, if the taxi is able to service the person.
      createEdges();

      // Output the cardinality
      System.out.printf("%d: %d%n", ncase, MaximumBipartiteMatching(taxis, people));
    }
  }

  /**
   * Create edges between taxis and people, if the taxi is able to service a
   * person.
   */
  void createEdges() {
    for (Vertex taxi : taxis) {
      for (Vertex person : people) {
        // If the travel time from the taxi to person is below the required tim
        // then the taxi can service the person.
        if (travelTime(taxi, person) <= timeToCollect) {
          // Taxi is able to service this person so add an edge between them
          taxi.adjList.add(person);
          person.adjList.add(taxi);
        }
      }
    }
  }

  /**
   * Determine the time needed to move from source to destination.
   *
   * @param source The source location.
   * @param destination The destination location.
   * @return the time needed
   */
  int travelTime(Vertex source, Vertex destination) {
    return manhattanDist(source, destination) * BLOCK_SIZE / taxiSpeed;
  }

  /**
   * Calculate the number of blocks to traverse in Manhattan Distance.
   *
   * @param source The source location.
   * @param destination The destination location.
   * @return The number of blocks to traverse.
   */
  int manhattanDist(Vertex source, Vertex destination) {
    return Math.abs(source.x - destination.x) + Math.abs(source.y - destination.y);
  }

  /**
   * Find the cardinality of Graph G, with set X and Y being left and right
   * sides.
   *
   * @param graphX Left hand side graph
   * @param graphY Right hand side graph
   * @return The cardinality of Graph G.
   */
  public int MaximumBipartiteMatching(ArrayList<Vertex> graphX, ArrayList<Vertex> graphY) {
    int cardinality = 0;
    for (Vertex vertexX : graphX) {
      for (Vertex vertexY : graphY) {
        vertexY.visited = false;  // Set all right side to not visited.
      }
      if (FindAugmentingPath(vertexX)) {  // If a path exists...
        cardinality++;
      }
    }
    return cardinality;
  }

  /**
   * Determine is an augmented path exist from vertex.
   *
   * @param vertex The source vertex.
   * @return True, If a path exists
   */
  public boolean FindAugmentingPath(Vertex vertex) {
    // Process all vertices in the adjacency list.
    for (Vertex vertexD : vertex.adjList) {
      if (!vertexD.visited) { // Only query vertex if not already visited.
        vertexD.visited = true;
        if (vertexD.matched == null || FindAugmentingPath(vertexD.matched)) {
          // d and v are now matched and we have a new path.
          vertex.matched = vertexD;
          vertexD.matched = vertex;
          return true;
        }
      }
    }
    return false; // no path...
  }

  /**
   * Class definition of the Vertex Information, with vertices in 2D space.
   */
  public class Vertex {

    public int x;
    public int y;
    public String id;
    public boolean visited = false;
    public Vertex matched = null;
    public ArrayList<Vertex> adjList;

    /**
     * Default constructor.
     */
    public Vertex(int x, int y) {
      this.x = x;
      this.y = y;
      adjList = new ArrayList<Vertex>();
    }

    /**
     * Default constructor.
     */
    public Vertex(String id) {
      this.id = id;
      adjList = new ArrayList<Vertex>();
    }
  }
}
