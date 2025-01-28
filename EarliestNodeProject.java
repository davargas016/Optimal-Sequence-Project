// Import necessary libraries
import java.io.*;
import java.util.*;

// Project1 Class
public class EarliestNodeProject {

	/**
	 * Main Driver class
	 * @param args	the inputs from the command line
	 */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            //Read first line for the number of nodes (n) and number of edges (m)
            int n = scanner.nextInt();
            int m = scanner.nextInt();

            //Read each edge and put them in matrix
            int[][] edges = new int[m][3];
            //for loop to iterate through each edge and assign each value
            for (int i = 0; i < m; i++) {
                edges[i][0] = scanner.nextInt();
                edges[i][1] = scanner.nextInt();
                edges[i][2] = scanner.nextInt();
            }

            //Calculate earliest meeting time
            int result = findEarliestMeetingTime(n, m, edges);

            //Print the result
            System.out.println(result);

        } 
        
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }//End of Main

    //Class for edges
    static class Edge {
    	//initialize the destination and weight variables for edges
        int destination, weight;

        //Method to assign destination and weight
        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }//end of edge
    }//End of edge

    /**
     * Method to calculate the earliest time	
     * @param n			number of nodes	
     * @param m			number of edges
     * @param edges		list of edges
     * @return			minimum meeting time
     */
    public static int findEarliestMeetingTime(int n, int m, int[][] edges) {
        // Initialize the graph
        List<List<Edge>> graph = new ArrayList<>();
        //create the empty nodes in the graph
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        //for loop to add every edges to the graph
        for (int[] edge : edges) {
        	//Assign the node to start from for the edge, the node we go to, and the weight
            int u = edge[0], v = edge[1], w = edge[2];
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w));
        }

        //Create the shortest time arrays from each start point
        int[] timeFromXyra = dijkstra(graph, n, 1);
        int[] timeFromOrion = dijkstra(graph, n, n);

        //Find the minimum meeting time
        int minMeetingTime = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
        	// Find the maximum arrival time at each node (they can wait if needed)
            int maxArrivalTime = Math.max(timeFromXyra[i], timeFromOrion[i]);
            if (timeFromXyra[i] != Integer.MAX_VALUE && timeFromOrion[i] != Integer.MAX_VALUE) {
                minMeetingTime = Math.min(minMeetingTime, maxArrivalTime);
            }
        }
        //return the minimum time or -1 if the nodes are disconnected
        return (minMeetingTime == Integer.MAX_VALUE) ? -1 : minMeetingTime;
    }//end of findEarliestMeetingTime

    /**
     * Method to calculate the shortest path to each node
     * @param graph		the graph of all nodes
     * @param n			the number of nodes
     * @param start		the starting node's number
     * @return			returns the minimum times for each node 
     */
    private static int[] dijkstra(List<List<Edge>> graph, int n, int start) {
    	//Create the minimum time arrays for the current robot
        int[] minTime = new int[n + 1];
        //Fills the array with the max value
        Arrays.fill(minTime, Integer.MAX_VALUE);
        //Sets the time for the current node to be 0
        minTime[start] = 0;

        //creates a priority queue and compares weights
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.offer(new Edge(start, 0));

        //while loop to check all nodes
        while (!pq.isEmpty()) {
        	//remove the closest node to the starting node
            Edge current = pq.poll(); 
            int u = current.destination;
            
            //if there is a shorter way, then skip
            if (current.weight > minTime[u]) continue;
            //explore all neighboring nodes
            for (Edge edge : graph.get(u)) {
                int v = edge.destination, time = edge.weight;
                //Check if new path through u is shorter
                if (minTime[u] + time < minTime[v]) {
                    minTime[v] = minTime[u] + time;
                    pq.offer(new Edge(v, minTime[v]));
                }
            }
        }
        //return the array of times
        return minTime;
    }//end of dijkstra
}//end of program 