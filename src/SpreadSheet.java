
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class SpreadSheet {

  static Vertex[][] cells;

  public static void main(String[] args) {
    new SpreadSheet().run();
  }

  private void run() {
    int numCols;
    int numRows;


    Scanner sc = new Scanner(System.in);
    int numSheets = sc.nextInt();

    for (int sheet = 1; sheet <= numSheets; sheet++) {
      numCols = sc.nextInt();
      numRows = sc.nextInt();
      sc.nextLine();

      // Create a new spread sheet.
      cells = new Vertex[numRows][numCols];
      ArrayList<Vertex> graph = new ArrayList<Vertex>();

      // Read in our rows.
      for (int row = 0; row < numRows; row++) {
        String rowStr = sc.nextLine();
        String[] columns = rowStr.split(" ");
        for (int col = 0; col < numCols; col++) {
          if (cells[row][col] == null) {
            cells[row][col] = new Vertex(row, col, columns[col]);
          } else {
            cells[row][col].setContents(columns[col]);
          }
          // Add in all the vertices into the main graph.
          graph.add(cells[row][col]);
        }
      }

      // Get our path around the spread sheet.
      ArrayList<Vertex> path = TopologicalSort(graph);
      System.out.printf("%d:\n", sheet);
      if (path == null) {
        // No path found, so print we have no path.
        System.out.println("Not computable!");
      } else {
        // Path found, so update the values in each cell.
        for (Vertex vertex : path) {
          if (!vertex.adjList.isEmpty()) {
            // have dependencies, so calculate
            vertex.calculate();
          }
        }
        // print the output.
        for (int row = 0; row < numRows; row++) {
          for (int col = 0; col < numCols; col++) {
            System.out.printf("%d ", cells[row][col].value);
          }
          System.out.println();
        }
      }
    }
  }

  /**
   * Perform a topological sort of the graph.
   *
   * @param graph The graph to sort.
   * @return A List of vertices processed in order, or null if a cycle was
   * detected.
   */
  public ArrayList<Vertex> TopologicalSort(ArrayList<Vertex> graph) {

    Stack<Vertex> vertexStack = new Stack<Vertex>();
    ArrayList<Vertex> vertexVisitedOrder = new ArrayList<Vertex>();

    // Add all vertices with an indegree of 0 to the stack.
    for (Vertex vertex : graph) {
      if (vertex.inCount == 0) {
        vertexStack.push(vertex);
      }
    }

    //initialise the vistied index.
    int i = 1;

    while (!vertexStack.empty()) {
      // get our next vertex from the stack, and add it to the visited array.
      Vertex currentVertex = vertexStack.pop();
      currentVertex.order = i++;
      vertexVisitedOrder.add(currentVertex);

      // Get all edges from this vertex.
      for (Cell cell : currentVertex.adjList) {
        Vertex w = cells[cell.row][cell.column];
        w.inCount--;
        // Decrement the in degree count, and if 0, add to the processing list.
        if (w.inCount == 0) {
          vertexStack.add(w);
        }
      }
    }
    // If we have processed all edges, then return the visited order array.
    if (i > graph.size()) {
      return vertexVisitedOrder;
    }
    return null; // return an error.
  }

  /**
   * Basic class to hold the location of a Cell (used in the adjacent list).
   */
  public class Cell {

    public int row = 0;
    public int column = 0;

    /**
     * Basic constructor to build via row, col values.
     * @param row
     * @param col 
     */
    public Cell(int row, int col) {
      this.row = row;
      this.column = col;
    }

    /**
     * Basic constructor to build via a spreadsheet cell reference.
     * @param cellID The reference to this cell in A0 format.
     */
    public Cell(String cellID) {
      setAddress(cellID);
    }

    /**
     * Set the cell address via the spreadsheet cell reference format.
     * @param address 
     */
    private void setAddress(String address) {
      int index = 0;
      // Find the first number in the address reference
      for (int cindex = 0; cindex < address.length(); cindex++) {
        if (Character.isLetter(address.charAt(cindex))) {
          index = cindex + 1;
        }
      }

      // create a new array of char to hold the alpha part of the address.
      char[] colAddress = new char[index];
      address.getChars(0, index, colAddress, 0);

      // Convert both to column and row references.
      column = convertToColNum(colAddress);
      row = Integer.parseInt(address.substring(index)) - 1;
    }

    /**
     * Convert a column Alphabet reference to a zero indexed number.
     * @param column array of char to convert to number format.
     * @return 
     */
    private int convertToColNum(char[] column) {
      int colAddress = -1;
      String alpha = "_ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // A = 1, Z = 26

      for (int c = 0; c < column.length; c++) {
        colAddress += alpha.indexOf(column[c]) * Math.pow(26, column.length - c - 1);
      }

      return colAddress;
    }
  }

  /**
   * Class definition of the Vertex Information, with vertices in 2D space.
   */
  public class Vertex {

    public int order = 0;
    public ArrayList<Cell> adjList;
    public int inCount = 0;
    public int value = 0;
    public int row;
    public int col;

    /**
     * Default constructor.
     */
    public Vertex(int row, int col) {
      this.row = row;
      this.col = col;
      this.inCount = 0;
      adjList = new ArrayList<Cell>();
    }

    /**
     * Default Constructor.
     * @param row Row of this vertex
     * @param col Column of this vertex
     * @param contents The contents of this cell.
     */
    public Vertex(int row, int col, String contents) {
      this.row = row;
      this.col = col;
      this.inCount = 0;
      adjList = new ArrayList<Cell>();
      setContents(contents);
    }

    /**
     * Set the value to be contained in this cell
     * @param contents 
     */
    public final void setContents(String contents) {
      // Attempt to get a plain number.
      try {
        value = Integer.parseInt(contents);
      } catch (NumberFormatException e) {
        // Must have a formula.
        String f = contents.substring(1); //remove leading "="
        String[] elements = f.split("\\+");
        // Add each element in the formula.
        for (String item : elements) {
          Cell element = new Cell(item);
          // See if our reference already exists?
          if (cells[element.row][element.column] == null) {
            // create a new vertex.
            cells[element.row][element.column] = new Vertex(element.row, element.column);
          }
          // Add that we have a cell that we reference.
          this.inCount++;
          // Let the cell we reference, know that we reference it.  (add us to it's adjacency list).
          cells[element.row][element.column].adjList.add(new Cell(this.row, this.col));
        }
      }
    }

    /**
     * Update the value of vertex
     */
    public void calculate() {
      // Update the value of the cells, on our adjacent list with our value.
      for (Cell element : adjList) {
        cells[element.row][element.column].value += this.value;
      }
    }
  }
}
