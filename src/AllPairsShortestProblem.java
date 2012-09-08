
import java.util.Scanner;

public class AllPairsShortestProblem {

  private int[][] pathWeights;
  private int[][] adjacencyMatrix;

  public static void main(String[] args) {
    new AllPairsShortestProblem().run();
  }

  public void run() {
    Scanner scn = new Scanner(System.in);
    int testCount = scn.nextInt();

    // Loop through all test cases.
    for (int testItem = 1; testItem <= testCount; testItem++) {
      int numVertices = scn.nextInt();
      int numEdges = scn.nextInt();

      // create adjacency Matrix
      adjacencyMatrix = new int[numVertices][numVertices];
      for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
          adjacencyMatrix[i][j] = 0;
        }
      }

      for (int edge = 0; edge < numEdges; edge++) {
        int s = scn.nextInt(); // source vertex
        int d = scn.nextInt(); // destination vertex
        int w = scn.nextInt(); // distance from s to d

        // add to adjacency matrix
        adjacencyMatrix[s][d] = w;
      }
      
      // Create the matrix with wieght values.
      AllPairsShortestPath(adjacencyMatrix);
      
      // Output the resulting Distance from source.
      System.out.printf("%d\n", testItem);
      for (int i = 0; i < numVertices; i++) {
        for (int j = 0; j < numVertices; j++) {
          if(pathWeights[i][j] == Integer.MAX_VALUE){
            System.out.print("NP ");
          } else {
            System.out.printf("%d ", pathWeights[i][j]);
          }
        }
        System.out.println();
      }
    }

  }

  public void AllPairsShortestPath(int[][] matrix) {
    int numberVertices = matrix.length;

    // create new storage container for path and weight information
    pathWeights = new int[numberVertices][numberVertices];

    // Initialise containers;
    for (int i = 0; i < numberVertices; i++) {
      for (int j = 0; j < numberVertices; j++) {
        // If no direct path, set weight to Infinity.
        pathWeights[i][j] = matrix[i][j] > 0 ? matrix[i][j] : Integer.MAX_VALUE;
        if(i == j){
          pathWeights[i][j] = 0;
        }
      }
    }
      
    // Main loop.
    for (int k = 0; k < numberVertices; k++) {
      for (int i = 0; i < numberVertices; i++) {
        for (int j = 0; j < numberVertices; j++) {
          // Cast these to long to avoid overflow! 
          if ((long)pathWeights[i][j] > (long)pathWeights[i][k] + (long)pathWeights[k][j]) {
            // Store new min weight
            pathWeights[i][j] = pathWeights[i][k] + pathWeights[k][j];
          }
        }
      }
    }
  }
}
