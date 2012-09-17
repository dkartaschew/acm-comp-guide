
import java.util.Scanner;

public class KnapsackProblem {

  /**
   * Main
   */
  public static void main(String[] args) {
    new KnapsackProblem().run();
  }

  public void run() {
    int vehicleCapacity;
    int numItems;

    Scanner in = new Scanner(System.in);

    int testCases = in.nextInt();
    while (testCases-- > 0) {

      // Get our vehicle capacity, and maximum number of items.
      vehicleCapacity = in.nextInt();
      numItems = in.nextInt();

      Item[] items = new Item[numItems];
      // Read in the weights and values for our items.
      for (int i = 0; i < numItems; i++) {
        items[i] = new Item();
        items[i].weight = in.nextInt();
      }
      for (int i = 0; i < numItems; i++) {
        items[i].value = in.nextInt();
      }

      // Calculate the max weight and output.
      System.out.println(Knapsack(items, vehicleCapacity));

    }
  }

  /**
   * Default class to hold an item.
   */
  public class Item {
    public int value;
    public int weight;
  }

  /**
   * Determine the maximum weight to hold.
   * @param items Array of Item to be considered.
   * @param capacity The maximum capacity of the container.
   * @return The maximum weight of items.
   */
  public int Knapsack(Item[] items, int capacity) {
    int[][] solution = new int[capacity+1][items.length + 1];

    for (int k = 1; k < items.length + 1; k++) {
      for (int w = 1; w < capacity+1; w++) {
        if (items[k - 1].weight > w) {
          solution[w][k] = solution[w][k - 1];
        } else {
          solution[w][k] = Math.max(solution[w][k - 1],
                  solution[w - items[k - 1].weight][k - 1] + items[k - 1].value);
        }
      }
    }
    return solution[capacity][items.length];
  }
}
