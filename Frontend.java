import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Frontend implements FrontendInterface{

  public Backend backend;
  public Scanner input;

  public Frontend(Backend backend, Scanner input) {
    this.backend = backend;
    this.input = input;
  }

  public Frontend(Backend backend) {
    this.backend = backend;
    this.input = new Scanner(System.in);
  }
  @Override
  public void start(){

    System.out.println("Welcome to Flight Router");
    System.out.println("Choose one of the options to begin with:");
    String options = "";
    options += "1) Load Data from a file\n";
    options += "2) Show Stats\n";
    options += "3) Find Shortest Route from one city to another\n";
    options += "4) Exit the program";
    System.out.println(options);

    int exit = 0;
    while (exit == 0) {
      int optionSelected = 0;
      while (!input.hasNextInt()) {
        input.nextLine();
        System.out.println("Select a correct option from 1 to 4");
      }
      optionSelected = input.nextInt();
      while (optionSelected > 4 || optionSelected < 1) {
        System.out.println("Select a correct option from 1 to 4");
        optionSelected = input.nextInt();
      }
      switch (optionSelected) {
        case 1:
          loadFile();
          break;
        case 2:
          showStats();
          break;
        case 3:
          findShortestRoute();
          break;
        case 4:
          exit();
          exit++;

      }

      if (exit == 0) {
        System.out.println("\nWhich option would you like to select now?");
        System.out.println(options);
      }

    }
  }

  @Override
  public void loadFile(){
    System.out.println("Please enter the file path");
    String filePath = input.next();
    File file = new File(filePath);
    try {
      backend.readData(file);
      System.out.println("Data loaded successfully");
    } catch (FileNotFoundException e) {
      System.out.println("Please enter a valid file");
    }
  }

  @Override
  public void showStats() {
    String stats = backend.getSummary();
    System.out.println(stats);
  }

  @Override
  public void findShortestRoute() {

      System.out.println("Please select a starting airport: ");
      String start = input.next();
      System.out.println("Please select a destination airport: ");
      String destination = input.next();
      try {
        ShortestPathInterface shortestPath = backend.getShortestRoute(start, destination);

        if (shortestPath != null) {
          // Route found, proceed with displaying information
          System.out.println("Airports in the route: ");
          System.out.println(shortestPath.getRoute());

          for (int i = 0; i < shortestPath.getDistances().size(); i++) {
            if (shortestPath.getDistances().size() == 1) {
              break;
            } else {
              System.out.println("Distance for segment " + (i + 1) + ": " + shortestPath.getDistances().get(i) + " mi");
            }
          }

          System.out.println("Total Distance: " + shortestPath.getTotalDistance() + " mi");
        } else {
          // Handle case when shortestPath is null (no route found)
          System.out.println("No route found between " + start + " and " + destination);
        }
      } catch (NoSuchElementException e) {
      }
    }

  @Override
  public void exit() {
    System.out.println("Exited successfully!");
  }

  public static void main(String[] args) throws FileNotFoundException{
    Scanner scnr = new Scanner(System.in);
    Backend backend =
        new Backend(new DijkstraGraph(new HashtableMap()));
    Frontend frontend = new Frontend(backend,scnr);
    frontend.start();
  }
}
