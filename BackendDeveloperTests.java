import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BackendDeveloperTests {

    /**
     * Testing the readData method from the backend implementation
     * test when a file is properly read and when a file is not
     * properly read
     */
    @Test
    public void testReadData() {
        //create a new backend to call methods
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);

        //Check that if a file exists it is found
        try {
            backend.readData(new File("flights.dot"));
        } catch (FileNotFoundException e) {
            Assertions.fail("Could not find the file", e);
        }

        //Check that if a file does not exist it is not found
        try {
            backend.readData(new File("fakefile.dot"));
            Assertions.fail("File should not exist");
        } catch (FileNotFoundException ignored) {
        }

    }

    /**
     * Testing the getRoute method from the ShortestPathInterface
     * and makes sure the correct list is returned
     */
    @Test
    public void testGetRoute() {
        //create new variables
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        try {
            backend.readData(new File("flightsTest.dot"));
        } catch (FileNotFoundException e) {
            Assertions.fail("Failed to find flightsTests.dot", e);
        }
        ShortestPathInterface shortestPath;
        shortestPath = backend.getShortestRoute("ORD", "SMF");
        //temporarily the shortest path will be [A, B, C, D, E]
        //create a new arraylist with these values
        ArrayList<String> expected = new ArrayList<>();
        expected.add("ORD");
        expected.add("RNO");
        expected.add("DTW");
        expected.add("SMF");
        //use getRoute to get the list for this shortest route
        ArrayList<String> route = shortestPath.getRoute();
        Assertions.assertEquals(expected, route);

    }

    /**
     * Testing the getShortestRoute method from the backend implementation
     * also tests edge cases where the start or destination nodes do not exist
     */
    @Test
    public void testGetShortestRoute() {
        //create new variables to use in the test
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        try {
            backend.readData(new File("flightsTest.dot"));
        } catch (FileNotFoundException e) {
            Assertions.fail("Failed to find flightsTests.dot", e);
        }
        ShortestPathInterface shortestPathFail;
        ShortestPathInterface shortestPathSuccess;

        //check that an error is properly returned if one of the two nodes
        //do not exist
        try {
            shortestPathFail = backend.getShortestRoute("ORD", "B");
        } catch (NoSuchElementException e) {
            Assertions.assertTrue(true);
        }

        //check that if the two nodes exist the proper list is returned
        shortestPathSuccess = backend.getShortestRoute("ORD", "SMF");
        Assertions.assertEquals("[ORD, RNO, DTW, SMF]", shortestPathSuccess.getRoute().toString());

    }

    /**
     * Testing the getSummary method from the BackendInterface
     * The edge case is that no file has been read yet
     */
    @Test
    public void testGetSummary() {
        //create new variables to use in the test
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        try {
            backend.readData(new File("flightsTest.dot"));
        } catch (FileNotFoundException e) {
            Assertions.fail("Failed to find flightsTests.dot", e);
        }

        /*
        test try and catch block once readData has been implemented
         */

        //create an example string of what the getSummary might return
        //message subject to change once implementation is complete
        String expected = "Airports: 7\nFlights: 8\nTotal Miles: 9353\n";
        String actual = backend.getSummary();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Test the getDistance method from the ShortestPathInterface
     */
    @Test
    public void testGetDistances() {
        //create new variables to use in the test
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        //read in the test file
        try {
            backend.readData(new File("flightsTest.dot"));
        } catch (FileNotFoundException e) {
            Assertions.fail("Failed to find flightsTests.dot", e);
        }

        ShortestPathInterface shortestPath = backend.getShortestRoute("ORD", "SMF");

        //using my test file, create an expected ArrayList and compare to output
        ArrayList<Double> expected = new ArrayList<>();
        expected.add(1671.0);
        expected.add(113.0);
        expected.add(390.0);
        ArrayList<Double> actual = shortestPath.getDistances();
        Assertions.assertEquals(expected, actual);
    }

    /**
     * Test the getTotalDistance method from the ShortestPathInterface
     */
    @Test
    public void testGetTotalDistances() {
        //create new variables to use in the test
        DijkstraGraph<String, Double> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        Backend backend = new Backend(graph);
        //read in the test file
        try {
            backend.readData(new File("flightsTest.dot"));
        } catch (FileNotFoundException e) {
            Assertions.fail("Failed to find flightsTests.dot", e);
        }
        ShortestPathInterface shortestPath = backend.getShortestRoute("ORD", "SMF");

        //using my test file, create an expected total distance and compare it to the actual
        double expected = 2174;
        double actual = shortestPath.getTotalDistance();
        Assertions.assertEquals(expected, actual);
    }
}
