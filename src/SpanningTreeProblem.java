
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SpanningTreeProblem {

  static int vertexCount;
  static int edgeCount;
  static PriorityQueue<edge> edges;
  static Boolean[] visited;
  private final static String OUTPUT_STRING 
          = "Test %d, minimum spanning tree weight = %d\n";

  public static void main(String[] args) {
    new SpanningTreeProblem().run();
  }

  public void run() {
    Scanner in = new Scanner(System.in);
    int caseCount = Integer.parseInt(in.nextLine());
    int loopCount = 0;

    // Keep looping while we have cases to test.
    while (caseCount - loopCount > 0) {
      int minimumWeight = 0;
      vertexCount = in.nextInt();
      edgeCount = in.nextInt();

      // Construct our priority queue, and visited map.
      edges = new PriorityQueue<edge>(edgeCount, new edge(0, 0, 0));
      visited = new Boolean[vertexCount];

      // Set all vertices to not visited.
      for (int n = 0; n < vertexCount; n++) {
        visited[n] = false;
      }

      // Read all edges into the priority queue.
      while (edgeCount-- > 0) {
        edges.add(new edge(in.nextInt(), in.nextInt(), in.nextInt()));
      }

      // Set our source vertex to be visited.
      visited[edges.peek().sourceVertex] = true;

      // While not visited all vertices, continue to loop
      while (!visitedAllVertices()) {
        LinkedList<edge> tmpEdges = new LinkedList<edge>();
        int sourceVertex, destinationVertex;

        edge minEdge = edges.poll();
        Boolean useThisEdge = false;

        // find min edge with one node in tree and one node not in tree
        do {
          sourceVertex = minEdge.sourceVertex;
          destinationVertex = minEdge.destinationVertex;
          
          // check for source being visited and destination not visited
          if (visited[sourceVertex] && !visited[destinationVertex]) {
            useThisEdge = true;
          } else if (!visited[sourceVertex] && visited[destinationVertex]) {
            // check for other direction
            int tmp = sourceVertex; // swap source and destination
            sourceVertex = destinationVertex;
            destinationVertex = tmp;
            useThisEdge = true;
          }

          if (!useThisEdge) {
            tmpEdges.add(minEdge); // store unused edges for later
            minEdge = edges.poll(); // get next edge to try
          }
        } while (!useThisEdge);

        minimumWeight += minEdge.weight;
        visited[destinationVertex] = true;

        // copy unused edges back to priority queue
        for(edge e: tmpEdges){
          edges.add(e);
        }
      }

      System.out.printf(OUTPUT_STRING, ++loopCount, minimumWeight);
    }
  }

  public Boolean visitedAllVertices() {
    for (Boolean v : visited) {
      if (!v) {
        return false;
      }
    }
    return true;
  }

  public class edge implements Comparator<edge> {

    public int sourceVertex, destinationVertex, weight;

    public edge(int s, int d, int w) {
      sourceVertex = s;
      destinationVertex = d;
      weight = w;
    }

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
