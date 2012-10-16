
import java.util.PriorityQueue;
import java.util.Scanner;

public class minCostMaxFlow {

  int[][] capacity; // Capacity Matrix for the Graph
  int[][] costs; // Cost Matrix for the Graph
  int[][] flow; // Flow Matrix for the Graph
  Vertex[] vertices; // Vector of Vertices in the Graph
  int numVertices; // Number of Vertices in the Graph
  int sourceVertex; // Index of the Source Vertex.
  int sinkVertex; // Index of the Sink Vertex.
  final int SENTINEL = -1;
  final int INFINITY = Integer.MAX_VALUE;

  /**
   * Main
   */
  public static void main(String[] args) {
    new minCostMaxFlow().run();
  }

  /**
   * Main runner.
   */
  public void run() {
    Scanner sc = new Scanner(System.in);

    int numCases = sc.nextInt();

    for (int caseNum = 1; caseNum <= numCases; caseNum++) {
      numVertices = sc.nextInt(); // number of vertices
      int numEdges = sc.nextInt(); // edges
      sourceVertex = sc.nextInt(); // source
      sinkVertex = sc.nextInt(); // sink

      // Create the matrix for capacity, cost and flows
      capacity = new int[numVertices][numVertices];
      costs = new int[numVertices][numVertices];
      flow = new int[numVertices][numVertices];
      vertices = new Vertex[numVertices];
      for (int n = 0; n < numVertices; n++) {
        vertices[n] = new Vertex(n);
      }

      for (int edge = 0; edge < numEdges; edge++) { // edges:
        int edgeSource = sc.nextInt(); // input Vi
        int edgeDestination = sc.nextInt(); // input Vj
        int edgeCapacity = sc.nextInt(); // input Cap
        int edgeCost = sc.nextInt(); // input Cost

        capacity[edgeSource][edgeDestination] = edgeCapacity; // directed capacity

        costs[edgeSource][edgeDestination] = edgeCost; // undirected cost
        costs[edgeDestination][edgeSource] = edgeCost;

        flow[edgeSource][edgeDestination] = 0; // zero initial flow
        flow[edgeDestination][edgeSource] = 0; // zero back flow
      }

      System.out.printf("Test %d: Minimum Cost = %d%n", caseNum, minCostFlowEdmondsKarp()); //output minimum cost
    }
  }

  /**
   * Calculate the Minimum Cost of the Maximum Flow within the graph.
   *
   * @return
   */
  private int minCostFlowEdmondsKarp() {
    while (findAugmentingPath() > 0);
    return calculateMinimumCost();
  }

  /**
   * Determine the flow of the an augmenting path within the graph.
   *
   * @return The flow value of a path. (0 being no path).
   */
  private int findAugmentingPath() {
    PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>();

    // initialise parents
    int[] parent = new int[numVertices];
    for (int n = 0; n < numVertices; n++) {
      parent[n] = SENTINEL; // sentinel value
    }
    // initialise queued nodes
    for (int i = 0; i < numVertices; i++) {
      vertices[i].lowestCost = INFINITY;
      if (i == sourceVertex) {
        vertices[i].lowestCost = 0; // source cost
      } else if (capacity[sourceVertex][i] - flow[sourceVertex][i] > 0) {
        vertices[i].lowestCost = costs[sourceVertex][i]; // nodes from source
        parent[i] = sourceVertex;
        Q.add(vertices[i]);
      }
    }

    while (!Q.isEmpty()) {
      Vertex current = Q.poll();
      if (current.id == sinkVertex) // check if sink reached
      {
        break;
      }

      for (int i = 0; i < numVertices; i++) {
        if (capacity[current.id][i] - flow[current.id][i] > 0) {
          int potential = current.lowestCost;
          if (flow[current.id][i] < 0) {
            potential -= costs[current.id][i];
          } else {
            potential += costs[current.id][i];
          }

          if (potential < vertices[i].lowestCost) {
            Q.remove(vertices[i]);
            vertices[i].lowestCost = potential;
            parent[i] = current.id;
            Q.add(vertices[i]);
          }
        }
      }
    }

    int augmentingFlow = INFINITY;
    int current = sinkVertex;

    // sentinel value, ie no augmenting path was found
    if (parent[sinkVertex] == SENTINEL) {
      return 0;
    }

    do {
      int residualCap = capacity[parent[current]][current] - flow[parent[current]][current];
      if (residualCap < augmentingFlow) {
        augmentingFlow = residualCap; // find minimum residual capacity for augmenting path
      }
      current = parent[current]; // walk backwards
    } while (current != sourceVertex);

    current = sinkVertex; // starting from the sink
    do {
      flow[parent[current]][current] += augmentingFlow; // forwards flow (real flow)
      flow[current][parent[current]] -= augmentingFlow; // backwards flow
      current = parent[current]; // walk backwards
    } while (current != sourceVertex);

    return augmentingFlow;
  }

  /**
   * Determine the minimum cost of the maximum flow.
   *
   * @return The minimum cost
   */
  private int calculateMinimumCost() {
    int totalCost = 0;
    for (int i = 0; i < numVertices; i++) {
      for (int j = 0; j < numVertices; j++) {
        if (flow[i][j] > 0) { // ignoring backwards flows
          totalCost += flow[i][j] * costs[i][j];
        }
      }
    }
    return totalCost;
  }

  /**
   * Generic Vertex Information. Note: Adjacency Information is stored
   * separately.
   */
  class Vertex implements Comparable<Vertex> {

    public int lowestCost;
    public int id;

    public Vertex(int i) {
      id = i;
    }

    @Override
    public int compareTo(Vertex other) {
      // before case (lower cost)
      if (this.lowestCost < other.lowestCost) {
        return -1;
      } else if (this.lowestCost > other.lowestCost) {
        // after case (greater lowest Cost)
        return 1;
      }
      return 0; // equality case (equal cost)
    }
  }
}
