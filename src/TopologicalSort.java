
import java.util.ArrayList;
import java.util.Stack;

public class TopologicalSort {
  
  /**
   * Perform a topological sort of the graph.
   * @param graph The graph to sort.
   * @return A List of vertices processed in order, or null if a cycle was detected.
   */
  public ArrayList<Vertex> TSort(ArrayList<Vertex> graph){
    
    Stack<Vertex> vertexStack = new Stack<Vertex>();
    ArrayList<Vertex> vertexVisitedOrder = new ArrayList<Vertex>();
    
    // Add all vertices with an indegree of 0 to the stack.
    for(Vertex vertex: graph){
      if(vertex.inCount == 0){
        vertexStack.push(vertex);
      }
    }
    
    //initialise the vistied index.
    int i = 1;
    
    while(!vertexStack.empty()){
      // get our next vertex from the stack, and add it to the visited array.
      Vertex currentVertex = vertexStack.pop();
      currentVertex.order = i++;
      vertexVisitedOrder.add(currentVertex);
      
      // Get all edges from this vertex.
      for(Vertex w: currentVertex.adjList){
        w.inCount--;
        // Decrement the in degree count, and if 0, add to the processing list.
        if(w.inCount == 0){
          vertexStack.add(w);
        }
      }
    }
    // If we have processed all edges, then return the visited order array.
    if(i > graph.size()){
      return vertexVisitedOrder;
    }
    return null; // return an error.
  }

  /**
   * Class definition of the Vertex Information, with vertices in 2D space.
   */
  public class Vertex {

    public int id = 0;
    public int order = 0;
    public ArrayList<Vertex> adjList;
    public int inCount = 0;

    /**
     * Default constructor.
     */
    public Vertex(int vertexID, int indeg) {
      this.id = vertexID;
      this.inCount = indeg;
      adjList = new ArrayList<Vertex>();
    }
  }
  
  public static void main(String[] args){
    new TopologicalSort().run();
  }
  
  private void run(){
    ArrayList<Vertex> graph = new ArrayList<Vertex>();
    char[] alpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    
    for (int i = 0; i < 7; i++){
      graph.add(new Vertex(i, 0));
      
    }
    // Add A edges.
    graph.get(0).adjList.add(graph.get(1));
    graph.get(0).adjList.add(graph.get(3));
    graph.get(0).inCount = 0;
    // Add B edges.
    graph.get(1).adjList.add(graph.get(2));
    graph.get(1).adjList.add(graph.get(4));
    graph.get(1).inCount = 2;
    // Add C edges.
    graph.get(2).adjList.add(graph.get(4));
    graph.get(2).inCount = 1;
    // Add D edges.
    graph.get(3).adjList.add(graph.get(1));
    graph.get(3).adjList.add(graph.get(4));
    graph.get(3).adjList.add(graph.get(5));
    graph.get(3).inCount = 1;
    // Add E edges.
    graph.get(4).adjList.add(graph.get(6));
    graph.get(4).inCount = 4;
    // Add F edge.
    graph.get(5).adjList.add(graph.get(4));
    graph.get(5).adjList.add(graph.get(6));
    graph.get(5).inCount = 1;
    // Add F edge.
    graph.get(6).inCount = 2;
    
    ArrayList<Vertex> visited = TSort(graph);
    if(visited == null){
      System.out.println("Cycle Detected");
    } else {
      for(Vertex v: visited){
        System.out.printf("%d: %c\n", v.order, alpha[v.id]);
      }
    }
  }
}
