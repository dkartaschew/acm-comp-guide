
import java.util.ArrayList;

public class MaximumBipartiteMatching {

  /**
   * Main
   */
  public static void main(String[] args) {
    new MaximumBipartiteMatching().run();
  }

  /**
   * Main runner.
   */
  public void run() {
    ArrayList<Vertex> graphX = new ArrayList<Vertex>();
    ArrayList<Vertex> graphY = new ArrayList<Vertex>();
    String[] names = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};

    // Add graph X and grpah Y
    for (int i = 0; i < 5; i++) {
      graphX.add(new Vertex(names[i]));
    }
    for (int i = 5; i < 9; i++) {
      graphY.add(new Vertex(names[i]));
    }
    // Add A edges.
    graphX.get(0).adjList.add(graphY.get(0));
    graphX.get(0).adjList.add(graphY.get(2));
    // Add B edges.
    graphX.get(1).adjList.add(graphY.get(0));
    // Add C edges.
    graphX.get(2).adjList.add(graphY.get(1));
    graphX.get(2).adjList.add(graphY.get(3));
    // Add D edges.
    graphX.get(3).adjList.add(graphY.get(1));
    graphX.get(3).adjList.add(graphY.get(3));
    // Add E edges.
    graphX.get(4).adjList.add(graphY.get(2));
    // Add F edges.
    graphY.get(0).adjList.add(graphX.get(0));
    graphY.get(0).adjList.add(graphX.get(1));
    // Add G edges.
    graphY.get(1).adjList.add(graphX.get(2));
    graphY.get(1).adjList.add(graphX.get(3));
    // Add H edges.
    graphY.get(2).adjList.add(graphX.get(0));
    graphY.get(2).adjList.add(graphX.get(4));
    // Add I edges.
    graphY.get(3).adjList.add(graphX.get(2));
    graphY.get(3).adjList.add(graphX.get(3));
    
    System.out.println(MaximumBipartiteMatching(graphX, graphY));
  }

  /**
   * Find the cardinality of Graph G, with set X and Y being left and right sides.
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
      // Print current matches.
      System.out.println("Matches after vertex " + vertexX.toString() + " processed");
      for(Vertex x: graphX){
        if(x.matched != null){
         System.out.println("v: " + x + " m: " + x.matched);
        } else {
          System.out.println("v: " + x + " m: null");
        }
      }
      System.out.printf("C = %d\n", cardinality);
    }
    return cardinality;
  }

  /**
   * Determine is an augmented path exist from vertex.
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

    public String id;
    public boolean visited = false;
    public Vertex matched = null;
    public ArrayList<Vertex> adjList;

    /**
     * Default constructor.
     */
    public Vertex(String vertexID) {
      this.id = vertexID;
      adjList = new ArrayList<Vertex>();
    }
    
    @Override
    public String toString(){
      return id;
    }
  }
}
