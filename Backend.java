import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Backend implements BackendInterface {
    private DijkstraGraph<String,Double> graph;
    private int totalMiles;
    private int airports;
    private int flights;

     // This is the constructor. Create a backend that takes an instance of the GraphADT to implement the
     // required functions
     public Backend(DijkstraGraph<String, Double> graph) {
         this.graph = graph;
     }


    /**
     * This method reads in graph data from a given DOT file. Then, the data are stored in a graph data
     * structure that implements the GraphADT
     *
     * Edge Cases: the file is not a DOT file or the file doesn't exist.
     *
     * @param fileName the DOT file to read
     */
    @Override
    public void readData(File fileName) throws FileNotFoundException {

        totalMiles = 0;
        flights = 0;
        airports = 0;

        //open the requested file
        Scanner scanner = new Scanner(fileName);

        //skip the first line which is always graph {
        scanner.nextLine();
        //while there are more values in the file
        while (scanner.hasNextLine()) {

            String firstCity = scanner.next();
            if (firstCity.equals("}")) {
                break;
            }

            firstCity = firstCity.replace("\"", "");
            if (!graph.containsNode(firstCity)) {
                graph.insertNode(firstCity);
                ++airports;
            }

            String field = scanner.next();
            if (field.startsWith("[")) {
                field = field.substring(7);
                String state = scanner.nextLine().substring(0, 2);
                field = field + state;
                continue;
            }

            String secondCity = scanner.next().replace("\"", "");
            if (!graph.containsNode(secondCity)) {
                graph.insertNode(secondCity);
                ++airports;
            }

            String field2 = scanner.nextLine();
            Double miles = Double.valueOf(field2.substring(8, field2.length() - 2));
            graph.insertEdge(firstCity, secondCity, miles);
            graph.insertEdge(secondCity, firstCity, miles);
            flights += 2;
            totalMiles += miles;
        }
    }


    /**
     * This method returns a list of airports along the shortest route for a given start and a
     * destination airport.
     *
     * Edge Cases: the start or destination is not in the nodes we have.
     *
     * @param start the airport to travel from
     * @param destination the airport to travel to
     * @return a list of airports along the shortest route
     */
    @Override
    public ShortestPathInterface getShortestRoute(String start, String destination) {
        ShortestPathInterface path = null;
        try {
            ArrayList<String> route = new ArrayList<>();
            for (String airport : graph.shortestPathData(start, destination)) {
                route.add(airport);
            }

            ArrayList<Double> distances = new ArrayList<>();
            for (int i = 0; i < route.size()-1; ++i) {
                distances.add(graph.getEdge(route.get(i), route.get(i+1)));
            }

            double totalDistance = graph.shortestPathCost(start, destination);
            path = new ShortestPath(route, distances, totalDistance);
        } catch (NoSuchElementException e) {
            return null;
        }

        return path;
    }

    /**
     * This method gives a summary of the dataset, including the number of nodes, the number of edges,
     * and the total miles for all edges in the graph.
     *
     * Edge Cases: we have not read a file.
     *
     * @return a string with statistics about the dataset
     */
    @Override
    public String getSummary() {
        String summary = "";
        summary += "Airports: " + airports + "\n";
        summary += "Flights: " + (flights / 2) + "\n";
        summary += "Total Miles: " + totalMiles + "\n";
        return summary;
    }
}
