
public class AdjacencyMatrix<E> {

  private E[][] matrix;
  private boolean directedGraph = false;

  /**
   * Create a new adjacency matrix of size n.
   *
   * @param numberOfVertex The number of vertices in the graph.
   * @param directedGraph If the graph is directed or undirected.
   */
  public void AdjacencyMatrix(int numberOfVertex, boolean directedGraph) {
    // Create a matrix
    matrix = (E[][]) new Object[numberOfVertex][numberOfVertex];
    // Set all egdes to 0.
    for (int i = 0; i < numberOfVertex; i++) {
      for (int j = 0; j < numberOfVertex; j++) {
        setEdge(i, j, null);
      }
    }
    // Set if graph is directed.
    this.directedGraph = directedGraph;
  }

  /**
   * Find if the specified edge exists.
   *
   * @param i Source vertex
   * @param j Destination vertex
   * @return true, if an edge is present otherwise false.
   */
  public boolean isEdge(int i, int j) {
    return (matrix[i][j] != null);
  }

  /**
   * Set the weight of an edge.
   *
   * @param i Source vertex
   * @param j Destination vertex
   * @param weight The new weight to be applied to this edge. (May be zero to
   * remove the edge from the graph).
   */
  public void setEdge(int i, int j, E weight) {
    matrix[i][j] = weight;
    if (!directedGraph) {
      // If undirected graph then set the back edge as well.
      matrix[j][i] = weight;
    }
  }

  /**
   * Get the weight of an edge.
   *
   * @param i Source vertex
   * @param j Destination vertex
   * @return The current weight of an edge, or null if no edge.
   */
  public E getEdge(int i, int j) {
    return matrix[i][j];
  }
  
  /**
   * Retrieve the matrix as a string suitable for printing.
   * @return String representation of the matrix.
   */
  @Override
  public String toString(){
    return matrix.toString();
  }
}
