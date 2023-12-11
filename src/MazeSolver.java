import java.util.*;

public class MazeSolver {
    private static final int MAZE_SIZE = 20; // Constant for the size of the maze.
    private final boolean[][] maze; // 2D array representing the maze, where true indicates a path and false indicates a wall.
    private final int startX; // Starting X coordinate in the maze.
    private final int startY; // Starting Y coordinate in the maze.

    // Constructor for the MazeSolver class.
    public MazeSolver(boolean[][] maze, int startX, int startY) {
        this.maze = maze; // Initialize the maze layout.
        this.startX = startX; // Set the starting X position.
        this.startY = startY; // Set the starting Y position.
    }

    // Method to perform A* search algorithm from the start position.
    public List<Node> aStarSearch(int startX, int startY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(); // Priority queue for open nodes, sorted based on heuristic cost.
        boolean[][] closedSet = new boolean[MAZE_SIZE][MAZE_SIZE]; // 2D array to track visited nodes.
        Node startNode = new Node(startX, startY, 0, calculateHeuristic(startX, startY), null); // Create the start node.

        openSet.add(startNode); // Add the start node to the open set.

        while (!openSet.isEmpty()) {
            Node current = openSet.poll(); // Remove and get the node with the lowest cost from the open set.
            if (isExit(current.x, current.y)) {
                return reconstructPath(current); // If the current node is an exit, reconstruct and return the path.
            }

            closedSet[current.x][current.y] = true; // Mark the current node as visited.

            // Iterate over possible directions: right, down, left, up.
            for (int[] dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int nextX = current.x + dir[0]; // Calculate the next X position.
                int nextY = current.y + dir[1]; // Calculate the next Y position.

                // If the move is valid and not visited, process the neighbor node.
                if (isValidMove(nextX, nextY, closedSet)) {
                    int newCost = current.cost + 1; // Increment the cost to reach the neighbor node.
                    int heuristic = calculateHeuristic(nextX, nextY); // Calculate heuristic cost for the neighbor node.
                    Node neighbor = new Node(nextX, nextY, newCost, heuristic, current); // Create a new node for the neighbor.

                    openSet.add(neighbor); // Add the neighbor node to the open set.
                }
            }
        }

        return Collections.emptyList(); // Return an empty list if no path is found.
    }

    // Method to check if the given coordinates are an exit of the maze.
    public boolean isExit(int x, int y) {
        // Check if the position is on the border of the maze but not the start position.
        return (x == 0 || x == MAZE_SIZE - 1 || y == 0 || y == MAZE_SIZE - 1) && !(x == startX && y == startY);
    }

    // Method to check if a move is valid.
    private boolean isValidMove(int x, int y, boolean[][] closedSet) {
        // Check within maze bounds, if the path is open, and if the node has not been visited.
        return x >= 0 && x < MAZE_SIZE && y >= 0 && y < MAZE_SIZE && maze[x][y] && !closedSet[x][y];
    }

    // Method to calculate the heuristic (Manhattan distance) to the nearest edge of the maze.
    private static int calculateHeuristic(int x, int y) {
        return Math.min(Math.min(x, MAZE_SIZE - 1 - x), Math.min(y, MAZE_SIZE - 1 - y));
    }

    // Method to reconstruct the path from the exit node to the start node.
    private static List<Node> reconstructPath(Node node) {
        LinkedList<Node> path = new LinkedList<>();
        while (node != null) {
            path.addFirst(node); // Add the node to the beginning of the list.
            node = node.parent; // Move to the parent node.
        }
        return path; // Return the reconstructed path.
    }
}
