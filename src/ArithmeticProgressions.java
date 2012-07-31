import java.util.Scanner;

public class ArithmeticProgressions {

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);

      // get first line and check for end of test cases
      String line = in.nextLine();
      while (!line.equals("0")) {

         // extract three ints
         Scanner sc = new Scanner(line);
         int a = sc.nextInt();
         int b = sc.nextInt();
         int c = sc.nextInt();

         // check progression: gap between a and b is same as between b and c
         if (b - a == c - b) {
            System.out.printf("%d %d %d yes%n", a, b, c);
         } else {
            System.out.printf("%d %d %d NO%n", a, b, c);
         }

         // get next line
         line = in.nextLine();
      }
   }
}
