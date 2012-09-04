
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class KruskalsMinimumSpanningTree {

  int vertexCount;
  int edgeCount;
  PriorityQueue<edge> edges;
  ArrayList<edge> tree;
  private final static String OUTPUT_STRING = "Test %d, minimum spanning tree weight = %d\n";

  /**
   * Main
   */
  public static void main(String[] args) {
    new KruskalsMinimumSpanningTree().run();
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

      // Create priority queue and MST.
      edge comp = new edge(0, 0, 0);
      edges = new PriorityQueue<edge>(edgeCount, comp);
      tree = new ArrayList<edge>();

      // Read all edges into the adjacency list.
      for (int i = 0; i < edgeCount; i++) {
        edges.add(new edge(in.nextInt(), in.nextInt(), in.nextInt()));
      }

      // Build connectivity map for Union-Find operation.
      unode[] vertexMap = new unode[vertexCount];
      for(int i = 0; i < vertexCount; i++){
        // Set all vertices to be in their own trees.
        vertexMap[i] = new unode(null, 0);
      }
      
      // Begin Kruskal's Algorithm with Union-Find
      while (!edges.isEmpty()) {
        edge currentEdge = edges.poll();
        unode p = find(vertexMap[currentEdge.source]); // Get parent of tree that source is in
        unode q = find(vertexMap[currentEdge.destination]); // Get parent of tree that dest is in.
        if(q != p){
          // If different parents, add the edge in.
          tree.add(currentEdge);
          minimumWeight += currentEdge.weight;
          union(p,q); // Join the two, ensuring minimum depth.
        }
      }
      System.out.printf(OUTPUT_STRING, ++loopCount, minimumWeight);
    }
  }

  /**
   * Edge container class.
   */
  public class edge implements Comparator<edge> {

    /**
     * destination vertex and weight.
     */
    public int source, destination, weight;

    /**
     * Constructor
     *
     * @param v The destination vertex
     * @param w The weight of the edge.
     */
    public edge(int source, int destination, int weight) {
      this.source = source;
      this.destination = destination;
      this.weight = weight;
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

  /**
   * Parent container class for Union-Find data structure.
   */
  public class unode {

    public unode parent;
    public int depth;

    public unode(unode parent, int depth) {
      this.parent = parent;
      this.depth = depth;
    }
  }

  /**
   * FIND method.
   */
  public unode find(unode current) {
    if (current.parent == null) {
      return current;
    }
    return (current.parent = find(current.parent));
  }

  /**
   * UNION method.
   */
  public void union(unode p, unode q) {
    if (p.depth > q.depth) {
      q.parent = p;
    } else if (p.depth < q.depth) {
      p.parent = q;
    } else {
      p.parent = q;
      p.depth += 1;
    }
  }
}
