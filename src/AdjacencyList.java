
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class AdjacencyList {

  static ArrayList<ArrayList<edge>> adjacencyList;
  static ArrayList<HashSet<Integer>> adjacencyEdges;

  /**
   * Main
   */
  public static void main(String[] args) {
    new AdjacencyList().run();
  }

  /**
   * Run interface to be called from main().
   */
  public void run() {
    Scanner in = new Scanner(System.in);

    // get first line and get the number of cases to test.
    int caseCount = Integer.parseInt(in.nextLine());
    int loopCount = 0;
    while (caseCount - loopCount > 0) {
      String line = in.nextLine();

      // extract numbers, this is the node count and edge count.
      Scanner sc = new Scanner(line);
      int vertexCount = sc.nextInt();
      int edgeCount = sc.nextInt();

      // Create the adjacency List.
      adjacencyList = new ArrayList<ArrayList<edge>>();
      adjacencyEdges = new ArrayList<HashSet<Integer>>();
      for (int n = 0; n < vertexCount; n++) {
        adjacencyList.add(new ArrayList<edge>());
        adjacencyEdges.add(new HashSet<Integer>());
      }

      while (edgeCount-- > 0) {
        // Get our next edge...
        line = in.nextLine();
        // extract numbers, this is the node count and edge count.
        sc = new Scanner(line);
        int source = sc.nextInt();
        int dest = sc.nextInt();
        edge edge1 = new edge(dest, 0);
        edge edge2 = new edge(source, 0);
        if (!adjacencyEdges.get(source).contains(edge1.hashCode())) {
          adjacencyEdges.get(source).add(edge1.hashCode());
          adjacencyList.get(source).add(edge1);
        }
        if (!adjacencyEdges.get(dest).contains(edge2.hashCode())) {
          adjacencyEdges.get(dest).add(edge2.hashCode());
          adjacencyList.get(dest).add(edge2);
        }
      }

      System.out.printf("%d\n", ++loopCount);
      // Print the resulting adjacency list.
      for (int i = 0; i < vertexCount; i++) {
        // Print the vertex number.
        System.out.printf("%d: ", i);
        ArrayList<edge> edges = adjacencyList.get(i);
        // Sort the array list.
        Collections.sort(edges);
        // Print the contents of the array list.
        for (int node = 0; node < edges.size(); node++) {
          if (node + 1 == edges.size()) {
            System.out.print(edges.get(node).vertex);
          } else {
            System.out.printf("%d, ", edges.get(node).vertex);
          }
        }
        System.out.println();
      }
    }
  }

  /**
   * Generic Edge List to store our edge information.
   */
  public class edge implements Comparable<edge> {

    /**
     * destination vertex and weight.
     */
    public int vertex, weight;

    /**
     * Constructor
     *
     * @param v The destination vertex
     * @param w The weight of the edge.
     */
    public edge(int v, int w) {
      vertex = v;
      weight = w;
    }

    /**
     * Compare function for collection sort.
     */
    @Override
    public int compareTo(edge o) {
      if (this.vertex < o.vertex) {
        return -1;
      } else if (this.vertex > o.vertex) {
        return 1;
      } else {
        return 0;
      }
    }

    @Override
    public int hashCode() {
      int hash = 1;
      hash = 100007 * hash + this.vertex;
      hash = 100007 * hash + this.weight;
      return hash;
    }
  }
}
