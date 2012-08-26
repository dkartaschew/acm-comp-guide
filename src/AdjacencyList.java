
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class AdjacencyList {

  /**
   * Define generic Vertex class to hold our information.
   */
  public static class Vertex {

    public int vertexID;
    public Edge edgeList;
    // Insert additional information related to the Vertex here.

    /**
     * Adds the edge into the adjacency list.
     *
     * @param source
     * @param destination
     */
    public void addEdge(Vertex[] adjacencyList, int source, int destination) {

      // Get edge list for source.
      Edge edge = adjacencyList[source].edgeList;
      if (edge != null) {
        while (edge.next != null && edge.node.vertexID != destination) {
          edge = edge.next;
        }
        if (edge.node.vertexID != destination) {
          edge.next = new Edge(adjacencyList[destination], 0, edge.next);
        }

      } else {
        // Doesn't exist, so add it in.
        adjacencyList[source].edgeList = 
                  new Edge(adjacencyList[destination], 0, null);
      }

      // Repeat same in reverse, as this is an undirected graph.
      edge = adjacencyList[destination].edgeList;
      if (edge != null) {
        while (edge.next != null && edge.node.vertexID != source) {
          edge = edge.next;
        }
        if (edge.node.vertexID != source) {
          edge.next = new Edge(adjacencyList[source], 0, edge.next);
        }

      } else {
        // Doesn't exist, so add it in.
        adjacencyList[destination].edgeList = 
                  new Edge(adjacencyList[source], 0, null);
      }
    }
  }

  /**
   * Generic Edge List to store our edge information.
   */
  public static class Edge {

    public Vertex node = null;
    public int weight = 0;
    public Edge next = null;

    public Edge(Vertex vertex, int weight, Edge edge) {
      node = vertex;
      this.weight = weight;
      next = edge;
    }
  }

  /**
   * Main
   */
  public static void main(String[] args) {

    Vertex[] adjacencyList;

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

      // Create the vector of Vertices, and set the vertex number in each Vertex
      adjacencyList = new Vertex[vertexCount];
      for (int i = 0; i < vertexCount; i++) {
        adjacencyList[i] = new Vertex();
        adjacencyList[i].vertexID = i;
      }
      
      while (edgeCount-- > 0) {
        // Get our next edge...
        line = in.nextLine();
        // extract numbers, this is the node count and edge count.
        sc = new Scanner(line);
        int source = sc.nextInt();
        int destination = sc.nextInt();
        adjacencyList[0].addEdge(adjacencyList, source, destination);
      }

      System.out.printf("%d\n", ++loopCount);
      // Print the resulting adjacency list.
      for (int i = 0; i < vertexCount; i++) {
        // Print the vertex number.
        System.out.printf("%d: ", adjacencyList[i].vertexID);
        Edge edge = adjacencyList[i].edgeList;
        // Cycle through all the edges, and add to array list.
        ArrayList<Integer> edges = new ArrayList<Integer>();
        while (edge != null) {
          edges.add(edge.node.vertexID);
          edge = edge.next;
        }
        // Sort the array list.
        Collections.sort(edges);
        // Print the contents of the array list.
        for(int node = 0; node < edges.size(); node++){
          if(node+1 == edges.size()){
            System.out.print(edges.get(node));
          } else {
            System.out.printf("%d, ", edges.get(node));
          }
        }
        System.out.println();
      }
    }
  }
}
