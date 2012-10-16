
import java.util.Scanner;

public class ArrayTraverse {

  /**
   * Main
   */
  public static void main(String[] args) {
    new ArrayTraverse().run();
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
      if(width > 0 && height > 0){
        ArrayDiagonalTraverse(width, height);
      }
      System.out.println("\n");
      // Get next input
      width = sc.nextInt();
      height = sc.nextInt();
    }
  }

  public void ArrayDiagonalTraverse(int width, int height) {
    
    char[] alpha = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    
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
        System.out.printf("%c%d ",  alpha[column], row+1);
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
}
