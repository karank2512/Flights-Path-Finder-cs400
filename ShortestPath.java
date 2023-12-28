import java.util.ArrayList;

public class ShortestPath implements ShortestPathInterface{

    private ArrayList<String> route;
    private ArrayList<Double> distances;
    private double totalDis;

     // This is a constructor. Create a ShortestPath class to store the results of a shortest path search.
     public ShortestPath(ArrayList<String> route, ArrayList<Double> distances, double totalDis) {
         this.route = route;
         this.distances = distances;
         this.totalDis = totalDis;
     }

    /**
     * This method is a getter method for all airports along the shortest route.
     *
     * @return a list of airports along the route
     */
    @Override
    public ArrayList<String> getRoute() {
        return this.route;
    }

    /**
     * This method is a getter method for the distances between adjacent airports.
     *
     * @return a list of the miles to travel for each segments of the route
     */
    @Override
    public ArrayList<Double> getDistances() {
        return this.distances;
    }


    /**
     * This method is a getter method for the total mile for the shortest route.
     *
     * @return the total miles for the route
     */
    @Override
    public double getTotalDistance(){
        return this.totalDis;
    }
}
