import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * This class stores the graph data in a graph data structure that implements the GraphADT. Also, it
 *  accesses the data according to the commands from the frontend.
 *
 */
public interface BackendInterface {
  
  /**
  // This is the constructor. Create a backend that takes an instance of the GraphADT to implement the 
  // required functions
  public IndividualBackendInterface(GraphADT adtInstance);
  */
  
  /**
   * This method reads in graph data from a given DOT file. Then, the data are stored in a graph data 
   * structure that implements the GraphADT
   * 
   * Edge Cases: the file is not a DOT file or the file doesn't exist.
   * 
   * @param file the DOT file to read
   */
  public void readData(File file) throws FileNotFoundException;
  
  /**
   * This method returns a a list of airports along the shortest route for a given start and a 
   * destination airport.
   * 
   * Edge Cases: the start or destination is not in the nodes we have.
   * 
   * @param start the airport to travel from
   * @param destination the airport to travel to
   * @return a list of airports along the shortest route
   */
  public ShortestPathInterface getShortestRoute(String start, String destination);
  
  /**
   * This method gives a summary of the dataset, including the number of nodes, the number of edges,
   * and the total miles for all edges in the graph.
   * 
   * Edge Cases: we have not read a file.
   * 
   * @return a string with statistics about the dataset
   */
  public String getSummary();
  
}
