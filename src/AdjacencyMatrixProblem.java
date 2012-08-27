
import java.util.Scanner;

public class AdjacencyMatrixProblem {

  public static void main(String[] args) {
    int[][] adjacencyMatrix;

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

      // Create the matrix, and clear the contents.
      adjacencyMatrix = new int[vertexCount][vertexCount];
      for (int i = 0; i < vertexCount; i++) {
        for (int j = 0; j < vertexCount; j++) {
          adjacencyMatrix[i][j] = 0;
        }
      }
      
      // Read in all the edges.
      while (edgeCount-- > 0) {
        // Get our next edge...
        line = in.nextLine();
        // extract numbers, this is the node count and edge count.
        sc = new Scanner(line);
        int source = sc.nextInt();
        int destination = sc.nextInt();
        int weight = sc.nextInt();
        adjacencyMatrix[source][destination] = weight;
      }
      
      // Print out the matrix.
      System.out.printf("%d\n    ", ++loopCount);
      // Print the row header.
      for(int i = 0; i < vertexCount; i++){
        System.out.printf("%5d", i);
      }
      System.out.println();
      // Print the matrix contents
      for (int i = 0; i < vertexCount; i++) {
        System.out.printf("%4d", i); // Row number
        for (int j = 0; j < vertexCount; j++) {
          System.out.printf("%5d",adjacencyMatrix[i][j]); // Cell
        }
        System.out.println();
      }
    }
  }
}
