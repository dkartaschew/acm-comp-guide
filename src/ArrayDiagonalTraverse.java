
public class ArrayDiagonalTraverse {

  public static void ArrayDiagonalTraverse(int[][] matrix, int width, int height) {
    int start_x = 0;
    int start_y = 0;
    int column;
    int row;

    // Keep looping until exit condition.
    while (true) {

      // Initialise the starting location in the matrix for the current slice.
      column = start_x;
      row = start_y;

      // Traverse the current slice.
      while (column >= 0 && row < height) {
        System.out.printf("%d ", matrix[row][width-column-1]);
        column--;
        row++;
      }

      // Update the start location for the next slice.
      if (start_x < width - 1) {
        start_x++;
      } else if (start_y < height - 1) {
        start_y++;
      } else {
        // Exit the method, as start locations are now out of matrix bounds.
        break; 
      }
    }
  }

  /**
   * Main
   */
  public static void main(String[] args) {
    int[][] matrix = new int[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {9, 10, 11}};
    for (int y = 0; y < 4; y++) {
      for (int x = 0; x < 3; x++) {
        System.out.printf("%3d ", matrix[y][x]);
      }
      System.out.println();
    }
    System.out.println();
    ArrayDiagonalTraverse(matrix, 3, 4);

  }
}
