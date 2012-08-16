
import java.util.Scanner;


public class LightMoreLight {
  
  /**
   * Main
   */
  public static void main(String[] args){
    Scanner in = new Scanner(System.in);
    String line = in.nextLine();
    while(!line.equals("0")){
      int number = Integer.parseInt(line);
      
      if(Math.floor(Math.sqrt(number)) == Math.sqrt(number)){
        System.out.println("yes");
      } else {
        System.out.println("no");
      }
      
      line = in.nextLine();
    }
  }
}
