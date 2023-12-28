import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * An interface for a class that implements the functionality of the frontend for the flight router
 * app.
 */
public interface FrontendInterface {

  /**
   * A constructor that accepts a reference to backend and a java.util.Scanner instance to read user
   * input.
   *
   * @param backend
   * @param input
   */
   //public FrontendInterface(Backend backend, Scanner input);

  /**
   * <p>
   * This method is the main command loop for the frontend which prompts the user to enter the
   * required data that is requested by the following commands and runs the app.
   * </p>
   * The command [1] will prompt the user to load a specific data file when the user enters 1.
   * <p>
   * The command [2] will display the statistics about the dataset that includes the number of
   * airports, flights, and the total number of miles to the user when the user enters 2.
   * </p>
   * <p>
   * The command [3] will prompt the user to enter start and end destination airports and it will
   * return the shortest route between the airports, including all airports on the way, the distance
   * for each segment, and the total number of miles from start to destination, when the user enters
   * 3.
   * </p>
   * <p>
   * When the user enters 4, i.e. command [4], the app will terminate.
   * </p>
   * <p>
   * If the user enters anything other than the four commands, it will be handled accordingly.
   * </p>
   */
  public void start() throws FileNotFoundException;

  /**
   * This method takes the input file as entered by the user from the main command loop and sends
   * the input file to be read by the backend and returns no output if successfully read, otherwise
   * any exceptions thrown will be handled accordingly.
   */
  public void loadFile() throws FileNotFoundException;

  /**
   * This method will display the statistics about the dataset that includes the number of airports,
   * the number of flights, and the total number of miles to the user, when the command [2] is
   * entered from the main command loop. It will query the backend to get the required data and
   * return the expected value. If there are any exceptions thrown, they will be handled
   * accordingly.
   */
  public void showStats();

  /**
   * This method will list the shortest route between the start and destination airports as
   * specified by the user from the main command loop, when entering the command 3. It will query
   * the backend to return the required info such as the airports along the way of the route,
   * distance for each segment, and total number of miles for the route, and print the result to the
   * user. If there are any exceptions thrown, they will be handled accordingly.
   */
  public void findShortestRoute();

  /**
   * This method will ensure successful termination of the app once the user enters the command 4 in
   * the main command loop.
   */
  public void exit();

}
