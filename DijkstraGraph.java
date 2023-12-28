// --== CS400 File Header Information ==--
// Name: Karan Kapur
// Email: kkapur5@wisc.edu
// Group and Team: F15
// Group TA: Anvay Grover
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class extends the BaseGraph data structure with additional methods for
 * computing the total cost and list of node data along the shortest path
 * connecting a provided starting to ending nodes. This class makes use of
 * Dijkstra's shortest path algorithm.
 */
public class DijkstraGraph<NodeType, EdgeType extends Number>
        extends BaseGraph<NodeType, EdgeType>
        implements GraphADT<NodeType, EdgeType> {

    /**
     * While searching for the shortest path between two nodes, a SearchNode
     * contains data about one specific path between the start node and another
     * node in the graph. The final node in this path is stored in its node
     * field. The total cost of this path is stored in its cost field. And the
     * predecessor SearchNode within this path is referened by the predecessor
     * field (this field is null within the SearchNode containing the starting
     * node in its node field).
     *
     * SearchNodes are Comparable and are sorted by cost so that the lowest cost
     * SearchNode has the highest priority within a java.util.PriorityQueue.
     */
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;

        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }

        public int compareTo(SearchNode other) {
            if (cost > other.cost)
                return +1;
            if (cost < other.cost)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor that sets the map that the graph uses.
     * @param map the map that the graph uses to map a data object to the node
     *        object it is stored in
     */
    public DijkstraGraph(MapADT<NodeType, Node> map) {
        super(map);
    }

    /**
     * This helper method creates a network of SearchNodes while computing the
     * shortest path between the provided start and end locations. The
     * SearchNode that is returned by this method is represents the end of the
     * shortest path that is found: it's cost is the cost of that shortest path,
     * and the nodes linked together through predecessor references represent
     * all of the nodes along that shortest path (ordered from end to start).
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return SearchNode for the final end node within the shortest path
     * @throws NoSuchElementException when no path from start to end is found
     *                                or when either start or end data do not
     *                                correspond to a graph node
     */
    protected SearchNode computeShortestPath(NodeType start, NodeType end) throws NoSuchElementException {
        PriorityQueue<SearchNode> queue = new PriorityQueue<>();

        Map<NodeType, Double> shortestKnownPath = new HashMap<>();
        shortestKnownPath.put(start, 0.0);

        queue.add(new SearchNode(nodes.get(start), 0, null));

        while (!queue.isEmpty()) {
            SearchNode currentNode = queue.poll();

            if (currentNode.node.data.equals(end)) {
                return currentNode;
            }

            for (Edge edge : currentNode.node.edgesLeaving) {
                NodeType neighborData = edge.successor.data;
                double newCost = currentNode.cost + edge.data.doubleValue();

                if (!shortestKnownPath.containsKey(neighborData) || newCost < shortestKnownPath.get(neighborData)) {
                    shortestKnownPath.put(neighborData, newCost);
                    queue.add(new SearchNode(edge.successor, newCost, currentNode));
                }
            }
        }

        throw new NoSuchElementException("No path from start point to end found.");
    }


    /**
     * Returns the list of data values from nodes along the shortest path
     * from the node with the provided start value through the node with the
     * provided end value. This list of data values starts with the start
     * value, ends with the end value, and contains intermediary values in the
     * order they are encountered while traversing this shorteset path. This
     * method uses Dijkstra's shortest path algorithm to find this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return list of data item from node along this shortest path
     */
    public List<NodeType> shortestPathData(NodeType start, NodeType end) throws NoSuchElementException {
        SearchNode node = computeShortestPath(start, end);

        List<NodeType> list = new ArrayList<>();
        SearchNode currNode = node;
        while (currNode != null) {
            list.add(0, currNode.node.data);
            currNode = currNode.predecessor;
        }

        return list;
	}

    /**
     * Returns the cost of the path (sum over edge weights) of the shortest
     * path freom the node containing the start data to the node containing the
     * end data. This method uses Dijkstra's shortest path algorithm to find
     * this solution.
     *
     * @param start the data item in the starting node for the path
     * @param end   the data item in the destination node for the path
     * @return the cost of the shortest path between these nodes
     */
    public double shortestPathCost(NodeType start, NodeType end) throws NoSuchElementException{
        return computeShortestPath(start,end).cost;
    }


    /**
     * Checks if the shortest path and the shortest cost is same as we computed with hand
     */
    @Test
    public void test1(){
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");

        graph.insertEdge("A", "B", 9);
        graph.insertEdge("A", "A", 1);
        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 5);
        graph.insertEdge("C", "A", 2);
        graph.insertEdge("C", "A", 4);
        graph.insertEdge("A", "C", 3);

        List<String> actualPath = graph.shortestPathData("C","B");
        Double current = graph.shortestPathCost("C","B");


        Assertions.assertEquals(8, current);
        Assertions.assertEquals("C", actualPath.get(0).toString());
        Assertions.assertEquals("A", actualPath.get(1).toString());
        Assertions.assertEquals("B", actualPath.get(2).toString());
    }

    /**
     * Checks if the shortest path and the shortest cost is same as we computed with hand
     */
    @Test
    public void test2() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        //inserting nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        //inserting edged
        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        List<String> expectedPath = new ArrayList<>();
        expectedPath.add("A");
        expectedPath.add("B");
        expectedPath.add("D");
        expectedPath.add("E");

        List<String> actualPath = graph.shortestPathData("A", "E");

        double expectedCost = 8;

        double actualCost = graph.shortestPathCost("A", "E");

        Assertions.assertEquals(expectedPath, actualPath);
        Assertions.assertEquals(expectedCost, actualCost);
    }
    /**
     * Checks if the NoSuchElementException is thrown if there is a node that doesn't exist
     */
    @Test
    public void test3() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());
        graph.insertNode("H");
        graph.insertNode("I");
        graph.insertNode("J");

        graph.insertEdge("H", "I", 2);
        graph.insertEdge("I", "J", 3);

        Assertions.assertThrows(NoSuchElementException.class, () -> graph.computeShortestPath("Z"
            , "H"));
    }
    /**
     * Checks for invalid path for nodes that don't exist
     */
    @Test
    public void test4() {
        DijkstraGraph<String, Integer> graph = new DijkstraGraph<>(new PlaceholderMap<>());

        //inserting nodes
        graph.insertNode("A");
        graph.insertNode("B");
        graph.insertNode("C");
        graph.insertNode("D");
        graph.insertNode("E");
        graph.insertNode("F");
        graph.insertNode("G");
        graph.insertNode("H");

        //inserting edged
        graph.insertEdge("A", "B", 4);
        graph.insertEdge("A", "C", 2);
        graph.insertEdge("A", "E", 15);
        graph.insertEdge("B", "E", 10);
        graph.insertEdge("B", "D", 1);
        graph.insertEdge("C", "D", 5);
        graph.insertEdge("D", "E", 3);
        graph.insertEdge("D", "F", 0);
        graph.insertEdge("F", "D", 2);
        graph.insertEdge("F", "H", 4);
        graph.insertEdge("G", "H", 4);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            graph.shortestPathData("A", "G");
        });

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            graph.shortestPathCost("A", "G");
        });
    }
}
