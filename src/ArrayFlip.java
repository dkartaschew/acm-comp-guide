
import java.util.Scanner;

public class ArrayFlip {

  /**
   * Flip an Array in a vertical direction
   *
   * @param source The source array
   * @param target The target array
   * @param width The width of the source array
   * @param height The height of the target array
   */
  public static void ArrayVerticalFlip(int[][] source, int[][] target, int width, int height) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        target[(height - y - 1 )][x] = source[y][x];
      }
    }
  }

  /**
   * Flip an Array in a horizontal direction
   *
   * @param source The source array
   * @param target The target array
   * @param width The width of the source array
   * @param height The height of the target array
   */
  public static void ArrayHorizontalFlip(int[][] source, int[][] target, int width, int height) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        target[y][(width - x - 1)] = source[y][x];
      }
    }
  }

  /**
   * Main
   */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    // get the size of the matrix to rotate.
    String matrixSizeLine = in.nextLine();
    Scanner matrixSize = new Scanner(matrixSizeLine);
    int width = matrixSize.nextInt();
    int height = matrixSize.nextInt();

    // Keep reading in a matrix until exit condition
    while (!((width == 0) && (height == 0))) {

      // Ignore the line if either width or height = 0.
      if (!((width == 0) || (height == 0))) {

        // Read in our matrix.
        int[][] matrix = new int[height][width];
        for (int count = 0; count < height; count++) {
          String[] matrixLine = in.nextLine().split("\\s+");
          for (int element = 0; element < matrixLine.length; element++) {
            matrix[count][element] = Integer.parseInt(matrixLine[element]);
          }
        }

        // Flip the matrix and print.
        int[][] target = new int[height][width];
        ArrayVerticalFlip(matrix, target, width, height);
        ArrayHorizontalFlip(target, matrix, width, height);

        // Print the resultant matrix.
        for (int row = 0; row < width; row++) {
          for (int column = 0; column < height; column++) {
            System.out.print(matrix[row][column]);
          }
          System.out.println();
        }

        // Print line between each matrix output
        System.out.println();
      }

      // get the size of the matrix to rotate.
      matrixSizeLine = in.nextLine();
      matrixSize = new Scanner(matrixSizeLine);
      width = matrixSize.nextInt();
      height = matrixSize.nextInt();
    }
  }
}
