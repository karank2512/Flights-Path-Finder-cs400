import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FrontendDeveloperTests {

  Backend backend =
      new Backend(new DijkstraGraph(new HashtableMap()));
  /**
   * Test if the start method works correctly and outputs correct statements
   */
  @Test
  public void testStart(){

    // 1. Create a new TextUITester object for each test, and
    // pass the text that you'd like to simulate a user typing as only argument.

    TextUITester tester = new TextUITester("4\n");

    // 2. Create a Scanner, and run then code that you want to test here:

    //Scanner scnr = new Scanner(System.in);
    Frontend frontend = new Frontend(backend);

    frontend.start();

    // 3. Check whether the output printed to System.out matches your expectations.
    String output = tester.checkOutput();
    if( !output.contains("Welcome to Flight Router\n" +
        "Choose one of the options to begin with:\n" +
        "1) Load Data from a file\n" + "2) Show Stats\n" + "3) Find Shortest Route " +
            "from one city to another\n" + "4) Exit the program\n" + "Exited successfully!")

    ) {
      Assertions.fail();
    }

  }

  /**
   * tests that correct data is loaded when the file is provided by the user
   */

  @Test
  public void testLoadFile() throws FileNotFoundException {
    TextUITester tester = new TextUITester("flights.dot");
    Scanner scnr = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, scnr) {};

    frontend.loadFile();

    String output = tester.checkOutput();
    if(!output.contains("Data loaded successfully")) {
      Assertions.fail();
    }

  }

  /**
   * tests that correct stats are used when requested by the user
   */

  @Test
  public void testShowStats(){
    TextUITester tester = new TextUITester("");

    Scanner scnr = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, scnr) {};

    frontend.showStats();

    String output = tester.checkOutput();

    if( !output.contains("Airports: ") ||
        !output.contains("Flights: ") ||
        !output.contains("Total Miles: ")) {
      Assertions.fail();
    }

  }

  /**
   * tests that shortest route is displayed when requested by the user
   */

  @Test
  public void testFindShortestRoute(){
    TextUITester tester = new TextUITester("ORD\nDFW\n");
    Scanner scnr = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, scnr);

    frontend.findShortestRoute();

    String output = tester.checkOutput();

    if( !output.contains("Please select a starting airport: \n") ||
        !output.contains ("Please select a destination airport: \n") ||
        !output.contains("Airports in the route: \n[ORD, DFW]\n") ||
        !output.contains("Total Distance: 802.0 mi\n")) {
      Assertions.fail();
    }
  }

  /**
   * Tests if program is exited correctly
   */
  @Test
  public void testExit(){
    TextUITester tester = new TextUITester("4\n");

    Scanner scnr = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, scnr) {};
    frontend.exit();
    String output = tester.checkOutput();
    if(!output.equals("Exited successfully!\n")){
      Assertions.fail();
    }
  }

  /**
   * An integration test to verify the working of readData() method through the frontend and
   * verify backend handles it correctly.
   */
  @Test
  public void testBackendIntegrationReadData() {
    TextUITester tester = new TextUITester("1\nflights.dot\n4\n");
    Scanner scnr = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, scnr);
    frontend.start();

    String output = tester.checkOutput();
    if(!output.contains("Data loaded successfully")) {
      Assertions.fail();
    }
  }

  /**
   * An integration test to verify the working of getShortestRoute() method through the frontend
   * and verify backend handles it correctly.
   */
  @Test
  public void testBackendIntegrationFindShortestRoute() {
    TextUITester tester = new TextUITester("1\nflights.dot\n3\nORD\nDFW\n4\n");
    Scanner scnr = new Scanner(System.in);
    Frontend frontend = new Frontend(backend, scnr);
    frontend.start();

    String output = tester.checkOutput();
    if( !output.contains("Please select a starting airport: \n") ||
        !output.contains ("Please select a destination airport: \n") ||
        !output.contains("Airports in the route: \n[ORD, DFW]\n") ||
        !output.contains("Total Distance: 802.0 mi\n")) {
      Assertions.fail();
    }
  }
}
