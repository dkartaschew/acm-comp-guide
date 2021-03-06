
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * A* Search for Vertices that exist in 2D space. 
 */
public class aStar {
  
  /**
   * Perform a search from start vertex to goal vertex, and return the cost of
   * the path
   * @param vertices An Array of Vertices within the graph.
   * @param start The ID of the starting vertex.
   * @param end The ID of the goal vertex.
   * @return The weight of the path, or Double.POSITIVE_INFINITY if no path!
   */
  public Double aStarSearch(Vertex[] vertices, int start, int goal) {
    PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();

    vertices[start].gScore = 0.0;
    vertices[goal].isGoal = true;
    vertexQueue.add(vertices[start]);

    while (!vertexQueue.isEmpty()) {
      
      Vertex current = vertexQueue.poll();
      if (current.isGoal) {
        return vertices[goal].gScore;
      }

      current.visited = true;
      vertices[current.vertexID].visited = true;

      // for all vertex adjacent to current do
      for (int n = 0; n < vertices.length; n++) {
        if (current.adjList.contains(n) && !vertices[n].visited) {
          Vertex adj = vertices[n];
          Double gScore = current.gScore + euclidianDistance(current, adj);
          
          // if adj not in Q or gScore < adj.gScore 
          if (!vertexQueue.contains(adj) || gScore < adj.gScore) {
            // Update the Scores for the adjacent vertex.
            vertexQueue.remove(adj);
            adj.gScore = gScore;
            adj.hScore = euclidianDistance(adj, vertices[goal]);
            adj.fScore = adj.gScore + adj.hScore;
            // Add this vertex back into the priority queue with updated information.
            vertexQueue.add(adj);
          }
        }
      }
    }

    // Failed.
    return Double.POSITIVE_INFINITY; 
  }

  /**
   * Return the distance between vertex A and vertex B, using euclidian distance
   *
   * @param vertexA The first vertex
   * @param vertexB The second vertex
   * @return The distance between to two vertices.
   */
  private  Double euclidianDistance(Vertex vertexA, Vertex vertexB) {
    Double dist = Math.sqrt(Math.pow((double) Math.abs(vertexA.xCoordinate - vertexB.xCoordinate), 2.0)
            + Math.pow((double) Math.abs(vertexA.yCoordinate - vertexB.yCoordinate), 2.0));
    return dist;
  }

  /**
   * Class definition of the Vertex Information, with vertices in 2D space.
   */
  public class Vertex implements Comparable<Vertex> {

    public int xCoordinate; // Coordinate in 2D space.
    public int yCoordinate; // Coordinate in 2D space.
    public int vertexID;  // The id of the Vertex
    public Double gScore;
    public Double fScore;
    public Double hScore;
    public HashSet<Integer> adjList;
    public Boolean visited; // Has this vertex been visited.
    public Boolean isGoal; // Is this the goal vertex.

    /**
     * Default constructor.
     *
     * @param xCoord X Coordinate in 2D space.
     * @param yCoord Y Coordinate in 3D space.
     * @param entityId The id of the Vertex
     */
    public Vertex(int xCoord, int yCoord, int entityId) {
      xCoordinate = xCoord;
      yCoordinate = yCoord;
      vertexID = entityId;
      gScore = Double.POSITIVE_INFINITY;
      fScore = Double.POSITIVE_INFINITY;
      hScore = Double.POSITIVE_INFINITY;
      adjList = new HashSet<Integer>();
      visited = false;
      isGoal = false;
    }

    @Override
    public int compareTo(Vertex o) {
      if (fScore < o.fScore) {
        return -1;
      } else if (fScore > o.fScore) {
        return 1;
      } else {
        return 0;
      }
    }
  }
}
