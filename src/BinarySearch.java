import java.util.Scanner;

public class BinarySearch {

  /**
   * Perform binary search of array (vector) for item (key).
   *
   * @param vector array of numbers
   * @param key item to look for in array
   * @param low start position of array to search
   * @param high end position of array to search
   * @return index of key in vector, or -1 is not present
   */
  public static int BinarySearch(int[] vector, int key, int low, int high){
    if(low > high){
      return -1;
    }
    int mid = (low+high)/2;
    if(vector[mid] == key){
      return mid;
    } else {
      if(vector[mid] > key){
        return BinarySearch(vector, key, low, (mid-1));
      } else {
        return BinarySearch(vector, key, (mid+1), high);
      }
    }
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

      // split by white space. so we have an array of numbers
      String[] numStrs = in.nextLine().split("\\s+");

      // create an array to hold our numbers, and convet the array of strings to
      // numbers. Note: numStrs[0] is the key value
      int[] nums = new int[numStrs.length - 1];
      for (int i = 1; i < nums.length; i++) {
        nums[i - 1] = Integer.parseInt(numStrs[i]);
      }

      // Output the index of the key in the vector
      System.out.printf("%d%n", 
        BinarySearch(nums, Integer.parseInt(numStrs[0]), 0, nums.length-1));
    }
  }
} 
