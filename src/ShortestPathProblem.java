
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class ShortestPathProblem {

  private static int INFINITY = Integer.MAX_VALUE;

  public static void main(String[] args) {
    new ShortestPathProblem().run();
  }

  public void run() {

    Vertex[] vertexList;  // List of all vertices in graph
    PriorityQueue<Vertex> verticesQueue;

    Scanner scn = new Scanner(System.in);
    int testCount = scn.nextInt();

    // Loop through all test cases.
    for (int testItem = 1; testItem <= testCount; testItem++) {
      int numVertices = scn.nextInt();
      int numEdges = scn.nextInt();
      int sourceVertex = scn.nextInt(); // source vertex

      // create array to store N vertices
      vertexList = new Vertex[numVertices];
      for (int n = 0; n < numVertices; n++) {
        vertexList[n] = new Vertex(numVertices);
      }

      vertexList[sourceVertex].distance = 0; // set source (distance = 0 at source!)
      
      for (int edge = 0; edge < numEdges; edge++) {
        int s = scn.nextInt(); // source vertex
        int d = scn.nextInt(); // destination vertex
        int w = scn.nextInt(); // distance from s to d

        // add to adjacency list
        vertexList[s].adjacencyList[d] = w;
      }

      // Create priority queue of N elements, and queue all elements.
      Vertex comp = new Vertex(numVertices);
      verticesQueue = new PriorityQueue<Vertex>(numVertices, comp);
      for (Vertex v : vertexList) {
        verticesQueue.add(v);
      }

      // Start main Dijkstra'a Algorithm
      while (!verticesQueue.isEmpty()) {
        Vertex uVertex = verticesQueue.poll();

        if (uVertex.distance == INFINITY) // vertex can't be reached
        {
          break;
        }

        // Loop through all neighbouring vertices
        for (int n = 0; n < numVertices; n++) {
          if (uVertex.adjacencyList[n] != INFINITY && verticesQueue.contains(vertexList[n])) {
            verticesQueue.remove(vertexList[n]); // Remove old D[n] value.
            vertexList[n].setDist(uVertex.distance + uVertex.adjacencyList[n]); // relax distance
            verticesQueue.add(vertexList[n]); // Add updated D[n] value to priority Queue
          }
        }
      }

      // Output the resulting Distance from source.
      System.out.printf("%d\n", testItem);
      for (Vertex vertex : vertexList) {
        vertex.printDistance();
      }
      System.out.println();
    }
  }

  /**
   * Class to define a vertex, including it's neighbouring vertices.
   */
  public class Vertex implements Comparator<Vertex> {

    public int distance;  // known distance from source vertex
    public int[] adjacencyList; // weights to neighbouring vertices.

    /**
     * Default Constructor.
     * @param numNeighbours The number of vertices in the graph. 
     */
    public Vertex(int numNeighbours) {
      distance = INFINITY;

      // Create a list of weights to neighbours and set the weight to INFINITY.
      adjacencyList = new int[numNeighbours];
      for (int i = 0; i < numNeighbours; i++) {
        adjacencyList[i] = INFINITY;
      }
    }

    /**
     * Update the distance for this vertex.
     * @param newDistance 
     */
    public void setDist(int newDistance) {
      if (newDistance != INFINITY) {
        if (distance == INFINITY || newDistance < distance) {
          distance = newDistance;
        }
      }
    }

    /**
     * Print the distance from source vertex to this vertex.
     */
    public void printDistance() {
      if (distance == INFINITY) {
        System.out.print("NP ");
      } else {
        System.out.printf("%d ", distance);
      }
    }

    /**
     * Comparator method for priority queue.
     */
    @Override
    public int compare(Vertex o1, Vertex o2) {
      if (o1.distance < o2.distance) {
        return -1;
      } else if (o1.distance > o2.distance) {
        return 1;
      } else {
        return 0;
      }
    }
  }
}
