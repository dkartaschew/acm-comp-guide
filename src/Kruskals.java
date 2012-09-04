
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Kruskals {

  /**
   * Find Minimum Spanning Tree using Kruskal's Algorithm
   * @param edges Priority queue of all edges available within the graph
   * @param vertexCount The number of vertices in the graph.
   * @return Edges that make up the minimum spanning tree.
   */
  public ArrayList<edge> Kruskals(PriorityQueue<edge> edges, int vertexCount) {
    int minimumWeight = 0;
    ArrayList<edge> tree = new ArrayList<edge>();
    // Build connectivity map for Union-Find operation.
    unode[] vertexMap = new unode[vertexCount];
    for (int i = 0; i < vertexCount; i++) {
      // Set all vertices to be in their own trees.
      vertexMap[i] = new unode(null, 0);
    }

    // Begin Kruskal's Algorithm with Union-Find
    while (!edges.isEmpty()) {
      edge currentEdge = edges.poll();
      unode p = find(vertexMap[currentEdge.source]); // Get parent of tree that source is in
      unode q = find(vertexMap[currentEdge.destination]); // Get parent of tree that dest is in.
      if (q != p) {
        // If different parents, add the edge in.
        tree.add(currentEdge);
        minimumWeight += currentEdge.weight;
        union(p, q); // Join the two, ensuring minimum depth.
      }
    }
    return tree;
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
