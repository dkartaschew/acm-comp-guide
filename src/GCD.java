
import java.util.Scanner;

public class GCD {

  /**
   * Compute the Greatest Common Divisor
   *
   * @param m number 1, greater or equal to 0.
   * @param n number 2, greater or equal to 0.
   * @return the GCD
   */
  public static int gcd(int m, int n) {
    int r;
    while (n != 0) {
      r = m % n;
      m = n;
      n = r;
    }
    return m;
  }

  /**
   * Main
   */
  public static void main(String[] args) {

    Scanner in = new Scanner(System.in);

    // get first line and get the number of cases to test.
    int caseCount = Integer.parseInt(in.nextLine());

    // Keep reading each line while caseCount > 0
    while (caseCount-- > 0) {
      String line = in.nextLine();

      // extract numbers ints
      Scanner sc = new Scanner(line);
      int a = sc.nextInt();
      int b = sc.nextInt();

      // Output the gcd for the two given numbers.
      System.out.printf("%d%n", gcd(a,b));
    }
  }
}
