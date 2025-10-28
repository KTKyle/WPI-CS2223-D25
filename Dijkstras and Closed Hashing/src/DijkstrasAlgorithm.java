import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DijkstrasAlgorithm {

    /**
     * Function's purpose is to read a matrix from a file and create a 2d array from it
     * @param filePath the file path containing the file we are going to read from
     * @return the 2d array representing the matrix we read from the file
     */
    public int[][] readFile(String filePath) {
        int[][] graph = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<int[]> rows = new ArrayList<>(); // list to store rows of the matrix
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" "); // split the line by spaces
                int[] row = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Integer.parseInt(parts[i]); // parse each number
                }
                rows.add(row); // add the row to the list
            }
            // convert the list of rows into a 2D array
            graph = new int[rows.size()][];
            for (int i = 0; i < rows.size(); i++) {
                graph[i] = rows.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace(); // handle file reading errors
        }
        return graph;
    }

    /**
     * Function runs Dijkstra's Algorithm utilizing priority queues to find the shortest path from the root node to destination node
     * @param graph the matrix/graph we are searching through
     * @param rootNode node we are starting at
     * @param destNode node we want to find the shortest path towards
     */
    public void runDijkstras(int[][] graph, int rootNode, int destNode){
        int graphLength = graph.length;   // length of graph

        // array to store the shortest distances from root node
        int[] distances = new int[graphLength];
        Arrays.fill(distances, Integer.MAX_VALUE);   // initializes our array with the integer max value (representing infinity)
        distances[rootNode] = 0;   // distance from the root node to itself is 0

        /*
         priority queue with custom case to order nodes by current shortest distance
            - each element in the queue is an int[] where:
            - [0] represents the node
            - [1] represents the current shortest distance to that node
        */ 
        PriorityQueue <int[]> nodes = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        nodes.offer(new int[]{rootNode, 0});   // adds root node to queue to start off with

        boolean[] seen = new boolean[graphLength];   // array to track nodes we've already seen

        // array to store path taken
        int[] path = new int[graphLength];
        Arrays.fill(path, -1);   // initialize path to -1 (represents null since we haven't moved yet)

        // process nodes in the queue
        while(!nodes.isEmpty()){
            int[] curr = nodes.poll();   // removes head of queue (node with smallest distance)
            int currNode = curr[0];
            int currDist = curr[1];

            // break if we've reached desired node
            if(currNode == destNode){
                break;
            }

            // skips nodes we've already seen
            if(seen[currNode]){
                continue;
            }

            seen[currNode] = true;   // sets current node to seen since it passed earlier check

            // updates distance for all neighbors of the current node we are looking at
            for(int i = 0; i < graphLength; i++){
                int edgeVal = graph[currNode][i];   // weight of the node between currNode and i
                if(edgeVal > 0 && !seen[i]){        // if there is another edge node and that we haven't seen it
                    int newDist = currDist + edgeVal;   // updates distance
                    distances[i] = newDist;  
                    path[i] = currNode;   // updates predecessor (total length so far up to this node)
                    nodes.offer(new int[]{i, newDist});   // add neighboring node ot queue
                }
            }
        }

        // reconstruct the shortest path from the destination node to the root node
        List<Integer> shortestPath = new ArrayList<>();
        for (int i = destNode; i != -1; i = path[i]) {
            shortestPath.add(0, i); // add each node to the front of the list
        }

        // print results
        System.out.println("Shortest Path Length: " + distances[destNode]);
        System.out.println("Shortest Path: " + shortestPath);
    }
    public static void main(String[] args) {
        DijkstrasAlgorithm dijkstras = new DijkstrasAlgorithm();

        // print the matrix
        int[][] inputGraph = dijkstras.readFile("C:\\Users\\kykyl\\OneDrive\\Documents\\Comp Sci Stuff\\CS 2223 Engling\\Dijkstras and Closed Hashing\\src\\Text\\Dijkstras\\initialMatrix");
        System.out.println("Adjacency Matrix:");
        for (int[] row : inputGraph) {
            System.out.println(Arrays.toString(row));
        }

        // take user input
        Scanner nodeInput = new Scanner(System.in);
        System.out.print("Enter the start node: ");
        int startNode = nodeInput.nextInt();
        System.out.print("Enter the destination node: ");
        int destNode = nodeInput.nextInt();
    
        // runs Dijkstras
        dijkstras.runDijkstras(inputGraph, startNode, destNode);

        nodeInput.close();
    }
}
