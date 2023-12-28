import java.io.File;
import java.util.ArrayList;

public class BackendPlaceholder implements BackendInterface{
  public int flights;
  public BackendPlaceholder(DijkstraGraph graph){

    this.flights = graph.getNodeCount();

  }
  @Override
  public void readData(File file) {
    // I read the file data
  }

  @Override
  public ShortestPathInterface getShortestRoute(String start, String destination) {
    return new ShortestPathInterface() {
      @Override
      public ArrayList<String> getRoute() {
        ArrayList<String> routeList = new ArrayList<>();
        routeList.add("ORD");
        routeList.add("DFW");
        routeList.add("RNO");
        return routeList;
      }

      @Override
      public ArrayList<Double> getDistances() {
        ArrayList<Double> distanceList = new ArrayList<>();
        distanceList.add(970.0);
        distanceList.add(640.0);
        return distanceList;
      }

      @Override
      public double getTotalDistance() {
        return 1610.0;
      }
    };
  }

  @Override
  public String getSummary() {
    return "summary";
  }
}
