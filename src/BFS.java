import java.util.ArrayList;
import java.util.LinkedList;

public class BFS {
  
  /**
   * Traverse a graph (as represented by an Adjacency Matrix) starting at
   * root, printing the Vertex ID to console.
   * @param matrix The Adjacency Matrix representing the Graph
   * @param root The starting vertex
   */
  public void BFSTravese(int[][] matrix, int root){
    // Create a queue
    LinkedList<Integer> queue = new LinkedList<Integer>();
    // Create a visited list.
    ArrayList<Integer> visited = new ArrayList<Integer>();
    
    // Insert the start vertex into the queue.
    queue.offer(root);
    
    // While the queue is not empty
    while(!queue.isEmpty()){
      int node = queue.poll();
      visited.add(node);
      System.out.printf("%d ", node); // Print the node ID to console.
      
      for(int i = 0; i < matrix.length; i++){
        // If node not in queue or in visited add to queue.
        if(matrix[node][i] != 0){ // Make sure we are an edge
          if(!queue.contains(i) && !visited.contains(i)){
            queue.offer(i);
          }
        }
      }
    }
  }
  
}
