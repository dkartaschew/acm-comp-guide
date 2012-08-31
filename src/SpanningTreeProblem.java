
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SpanningTreeProblem {

  static int vertexCount;
  static int edgeCount;
  static PriorityQueue<edge> edges;
  static ArrayList<ArrayList<edge>> adjacencyList;
  static Boolean[] visited;
  private final static String OUTPUT_STRING = "Test %d, minimum spanning tree weight = %d\n";

  /**
   * Main
   */
  public static void main(String[] args) {
    new SpanningTreeProblem().run();
  }

  /**
   * Run interface to be called from main().
   */
  public void run() {
    Scanner in = new Scanner(System.in);
    int caseCount = Integer.parseInt(in.nextLine());
    int loopCount = 0;

    // Keep looping while we have cases to test.
    while (caseCount - loopCount > 0) {
      int minimumWeight = 0;
      vertexCount = in.nextInt();
      edgeCount = in.nextInt();

      // Construct visited map and adjacency list.
      visited = new Boolean[vertexCount];
      adjacencyList = new ArrayList<ArrayList<edge>>();

      // Create priority queue.
      edge comp = new edge( 0, 0);
			edges = new PriorityQueue<edge>(edgeCount, comp);

      // Set all vertices to not visited and ensure Adjacency list has items.
      for (int n = 0; n < vertexCount; n++) {
        visited[n] = false;
        adjacencyList.add(new ArrayList<edge>());
      }

      // Read all edges into the adjacency list.
      while (edgeCount-- > 0) {
        int source = in.nextInt();
        int dest = in.nextInt();
        int weight = in.nextInt();
        adjacencyList.get(source).add(new edge(dest, weight));
        adjacencyList.get(dest).add(new edge(source, weight));
      }

      // Set our source vertex to be visited.
      visited[0] = true;

      // Add all vertex 0 edges to the priority queue
      addEdges(0);

      // While not visited all vertices, continue to loop
      while (!edges.isEmpty()) {
        edge e = edges.poll();

        // if vertex is not yet connected
        if (visited[e.vertex] == false) {

          // take edge and process all edges incident to this edge
          // each edge is in priority queue only once
          minimumWeight += e.weight;
          addEdges(e.vertex);
        }
      }

      System.out.printf(OUTPUT_STRING, ++loopCount, minimumWeight);
    }
  }

  /**
   * Add edges of vertex to the priority queue, whose edge has not been visited.
   *
   * @param vertex
   */
  public void addEdges(int vertex) {
    visited[vertex] = true;
    for (edge e : adjacencyList.get(vertex)) {
      // Add in edge, if neighbour has NOT been visited, to avoid cycles.
      if (visited[e.vertex] == false) {
        edges.add(e);
      }
    }
  }

  /**
   * Edge container class.
   */
  public class edge implements Comparator<edge> {

    /**
     * destination vertex and weight.
     */
    public int vertex, weight;

    /**
     * Constructor
     * @param v The destination vertex
     * @param w The weight of the edge.
     */
    public edge(int v, int w) {
      vertex = v;
      weight = w;
    }

    /**
     * Compare function for priority queue.
     */
    @Override
    public int compare(edge e1, edge e2) {
      if (e1.weight < e2.weight) {
        return -1;
      } else if (e1.weight > e2.weight) {
        return 1;
      } else {
        return 0;
      }
    }
  }
}
