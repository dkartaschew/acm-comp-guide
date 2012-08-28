import java.util.ArrayList;

public class PrimMST {

  private final static String OUTPUT_STRING 
          = "Test %d, minimum spanning tree weight = %d\n";
  
  /**
   * Construct a MST based on the Prim-Jarnik Algorithm. As this implementation
   * is based on an Adjacency Matrix, offers O(n^2) performance.
   *
   * @param matrix An adjacency matrix defining a graph
   * @param root The starting vertex
   * @return An array with the connecting vertex ID in each element
   */
  public static int[] PrimJarnikMST(int[][] matrix, int root) {
    // shortest known distance to MST, typically this may be a priority 
    // queue, but here we use an array, and linear scan the array to find
    // the lowest value.
    int[] distance = new int[matrix.length];

    // preceeding vertex in tree
    int[] pred = new int[matrix.length];

    boolean[] visited = new boolean[matrix.length]; // all false initially

    // Set all distances to inf, except root vertex, which we set to 0,
    // so that the main loop starts at this vertex.
    for (int i = 0; i < distance.length; i++) {
      distance[i] = Integer.MAX_VALUE;
    }
    distance[root] = 0;

    // Main loop, which terminates when all vertices have been visited.
    for (int i = 0; i < distance.length; i++) {
      int next = minVertex(distance, visited); // Get next lowest cost edge
      visited[next] = true;

      // The edge from pred[next] to next is in the MST (if next != root)
      int[] neighbours = getNeighbours(matrix,next);
      for (int j = 0; j < neighbours.length; j++) {
        int vertex = neighbours[j];
        int weight = matrix[next][vertex];
        if (distance[vertex] > weight || distance[vertex] == 0) {
          distance[vertex] = weight; // Update best weight
          pred[vertex] = next; // Set best vertex to reach this vertex
        }
      }
    }
    return pred;
  }

  /**
   * Find the minimum distance in the current best distance vector
   *
   * @param dist The vector of current minimum distance.
   * @param v An array of vertices that have been visited.
   * @return The shortest known distance to an unvisited vertex.
   */
  private static int minVertex(int[] distance, boolean[] visited) {
    int weight = Integer.MAX_VALUE;
    int vertex = -1;   // graph not connected, or no unvisited vertices
    for (int i = 0; i < distance.length; i++) {
      if (!visited[i] && distance[i] < weight) {
        vertex = i; // set lowest weight vertex.
        weight = distance[i]; // get the weight, for later comparison
      }
    }
    return vertex;
  }
  
  private static int[] getNeighbours(int[][] matrix, int row){
    ArrayList<Integer> neighbours = new ArrayList<Integer>();
    for(int i = 0; i < matrix.length; i++){
      if(matrix[row][i] > 0){
        neighbours.add(i);
      }
    }
    int[] temp = new int[neighbours.size()];
    int i = 0;
    for(Integer vertex: neighbours){
      temp[i++] = vertex;
    }
    return temp;
  }
}
