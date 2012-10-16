import java.util.Scanner;

public class ArrayTraverseLinear {

  /**
   * Main
   */
  public static void main(String[] args) {
    new ArrayTraverseLinear().run();
  }

  /**
   * Main runner.
   */
  public void run() {
    Scanner sc = new Scanner(System.in);
    int width = sc.nextInt();
    int height = sc.nextInt();
    while (width != 0 || height != 0) {
      // Ensure we have both positive values
      if (width > 0 && height > 0) {
        ColumnWiseTraverse(width, height);
      }
      System.out.println("\n");
      // Get next input
      width = sc.nextInt();
      height = sc.nextInt();
    }
  }

  /**
   * Traverse a matrix in column order. 
   */
  public void ColumnWiseTraverse(int width, int height) {
    for (int col = 0; col < width; col++) {
      for (int row = 0; row < height; row++) {
        System.out.printf("(%d,%d) ", col+1, row+1);
      }
    }
  }

  public void RowWiseTraverse(int width, int height) {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        System.out.printf("%d,%d ", row, col);
      }
    }
  }
}
