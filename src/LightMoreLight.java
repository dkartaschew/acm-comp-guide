
import java.util.Scanner;


public class LightMoreLight {
  
  /**
   * Main
   */
  public static void main(String[] args){
    Scanner in = new Scanner(System.in);
    String line = in.nextLine();
    while(!line.equals("0")){
      double number = Math.sqrt(Integer.parseInt(line));
      
      if(Math.floor(number) == number){
        System.out.println("yes");
      } else {
        System.out.println("no");
      }
      
      line = in.nextLine();
    }
  }
}
