
import java.util.ArrayList;

public class FloydWarshall {

  private int[][] path;
  private int[][] pathWeights;
  ArrayList<Integer> sdpath;
  private final static int NULL = -1;

  /**
   * Perform Floyd-Warshall Algorithm on weighted directed graph.
   * @param matrix Adjacency Matrix representation of grpah.
   * @return Matrx of costs for graph.
   */
  public int[][] AllPairsShortestPath(int[][] matrix) {
    int numberVertices = matrix.length;

    // create new storage container for path and weight information
    path = new int[numberVertices][numberVertices];
    pathWeights = new int[numberVertices][numberVertices];

    // Initialise containers;
    for (int i = 0; i < numberVertices; i++) {
      for (int j = 0; j < numberVertices; j++) {
        // If no direct path, set weight to Infinity.
        pathWeights[i][j] = matrix[i][j] > 0 ? matrix[i][j] : Integer.MAX_VALUE;
        if(i == j){
          pathWeights[i][j] = 0;
        }
        path[i][j] = NULL;
      }
    }

    // Main loop.
    for (int k = 0; k < numberVertices; k++) {
      for (int i = 0; i < numberVertices; i++) {
        for (int j = 0; j < numberVertices; j++) {
          if ((long)pathWeights[i][j] > (long)pathWeights[i][k] + (long)pathWeights[k][j]) {
            // Store new min weight
            pathWeights[i][j] = pathWeights[i][k] + pathWeights[k][j];
            // Store new path through k.
            path[i][j] = k;
          }
        }
      }
    }
    return pathWeights;
  }

  /**
   * Find the shortest Path from source to destination, based on a previous
   * run of the Floyd-Warshall algorithm.
   * @param source Source Vertex
   * @param destination Destination Vertex
   * @return Path from source to destination
   */
  public ArrayList<Integer> ShortestPath(int source, int destination) {
    sdpath = new ArrayList<Integer>();
    SPath(source, destination);
    return sdpath;
  }

  /**
   * Recursively follow the path from source to destination.
   */
  private void SPath(int source, int destination) {
    if (path[source][destination] == NULL) {
      sdpath.add(path[source][destination]);
    } else {
      SPath(source, path[source][destination]);
      SPath(path[source][destination], destination);
    }
  }
}
