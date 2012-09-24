
import java.util.Scanner;

public class EditDistanceProblem {

  public static void main(String[] args) {
    new EditDistanceProblem().run();
  }

  public void run() {
    Scanner sc = new Scanner(System.in);
    // number of test cases
    int cases = sc.nextInt();
    sc.nextLine();

    while (cases-- > 0) {
      // calculate the distance and output
      System.out.println(editDistance(sc.nextLine(), sc.nextLine()));
    }
  }

  /**
   * Return the the cost of the difference between the two characters.
   *
   * @param a The first character
   * @param b The second character
   */
  public int diff(char a, char b) {
    return (a == b ? 0 : 1);
  }

  /**
   * Calculate the edit distance between the two strings.
   *
   * @param source The source string
   * @param target The resulting string.
   * @return The distance between both strings
   */
  public int editDistance(String source, String target) {
    // create the matrix
    int editMatrix[][] = new int[source.length() + 1][target.length() + 1];

    // initialise first column
    for (int i = 0; i <= source.length(); i++) {
      editMatrix[i][0] = i;
    }

    // initialise first row
    for (int i = 0; i <= target.length(); i++) {
      editMatrix[0][i] = i;
    }

    // Complete the matrix
    for (int i = 1; i <= source.length(); i++) {
      for (int j = 1; j <= target.length(); j++) {
        editMatrix[i][j] = Math.min(Math.min(editMatrix[i - 1][j] + 1,
                editMatrix[i][j - 1] + 1),
                editMatrix[i - 1][j - 1] + diff(source.charAt(i - 1), target.charAt(j - 1)));
      }
    }

    return editMatrix[source.length()][target.length()];
  }
}
