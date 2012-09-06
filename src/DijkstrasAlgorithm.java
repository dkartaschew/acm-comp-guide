
import java.util.Stack;

public class DijkstrasAlgorithm {

  /**
   * Find the shortest path from source vertex to destination vertex
   *
   * @param adjacencyMatrix The graph represented as an adjacency matrix.
   * @param sourceVertex The vertex to start from.
   * @param destinationVertex The destination vertex
   * @return A stack with the path from source to destination.
   */
  public Stack<Integer> DijkstraShortestPath(int[][] adjacencyMatrix, int sourceVertex, int destinationVertex) {
    final int UNDEFINED = -1;
    
    // Define data structures needed for implementation.
    Stack<Integer> path = new Stack<Integer>();
    boolean[] visited = new boolean[adjacencyMatrix.length];
    int[] previousVertex = new int[adjacencyMatrix.length];
    int[] pathWeights = new int[adjacencyMatrix.length];

    // Clear all arrays being used.
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      pathWeights[i] = Integer.MAX_VALUE; // Infinity
      previousVertex[i] = UNDEFINED;
      visited[i] = false;
    }

    // Set the starting vertex.
    pathWeights[sourceVertex] = 0;

    // Follow all vertices, calculating paths/distances.
    for (int k = 0; k < adjacencyMatrix.length; k++) {
      int min = -1;

      // Find shortest path to vertex not yet visited.
      for (int i = 0; i < adjacencyMatrix.length; i++) {
        if (!visited[i] && (min == -1 || pathWeights[i] < pathWeights[min])) {
          min = i;
        }
      }

      // Flag we are at a new vertex.
      visited[min] = true;

      // perform edge relaxation
      for (int i = 0; i < adjacencyMatrix.length; i++) {
        if (adjacencyMatrix[min][i] > 0) {
          if (pathWeights[min] + adjacencyMatrix[min][i] < pathWeights[i]) {
            pathWeights[i] = pathWeights[min] + adjacencyMatrix[min][i];
            previousVertex[i] = min;
          }
        }
      }
    }

    // All vertices have been visited, so now construct a path
    path.push(destinationVertex);
    while (previousVertex[destinationVertex] != UNDEFINED) {
      path.push(previousVertex[destinationVertex]);
      destinationVertex = previousVertex[destinationVertex];
    }

    return path;
  }

  /**
   * Main
   */
  public static void main(String[] args) {
    new DijkstrasAlgorithm().run();
  }

  public void run() {
    int[][] matrix = {
      {0, 5, 0, 10, 0, 0, 0},
      {5, 0, 6, 3, 2, 0, 0},
      {0, 6, 0, 0, 7, 0, 0},
      {10, 3, 0, 0, 4, 8, 0},
      {0, 2, 7, 4, 0, 12, 6},
      {0, 0, 0, 8, 12, 0, 1},
      {0, 0, 0, 0, 6, 1, 0}};
    
    String[] vertex = { "A", "B", "C", "D", "E", "F", "G" };

    Stack<Integer> path = DijkstraShortestPath(matrix, 0, 5);
    while (!path.empty()) {
      System.out.printf("-> %s ", vertex[path.pop()]);
    }
    System.out.println();
  }
}
