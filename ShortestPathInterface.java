import java.util.ArrayList;

/**
 * This class stores the results of a shortest path search.
 *
 */
public interface ShortestPathInterface {
  
  
  /**
   // This is a constructor. Create a ShortestPath class to store the results of a shortest path search.
   public ShortestPathInterface(ArrayList<String> route, ArrayList<Double> distances, double totalDis);
   */
  
  /**
   * This method is a getter method for all airports along the shortest route.
   * 
   * @return a list of airports along the route
   */
  public ArrayList<String> getRoute();
  
  /**
   * This method is a getter method for the distances between adjacent airports.
   * 
   * @return a list of the miles to travel for each segments of the route
   */
  public ArrayList<Double> getDistances();
  
  
  /**
   * This method is a getter method for the total mile for the shortest route.
   * 
   * @return the total miles for the route
   */
  public double getTotalDistance();
}
