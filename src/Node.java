public class Node implements Comparable<Node> {
    // Public fields for the coordinates (x, y), cost to reach this node, heuristic value, and reference to the parent node.
    public int x, y, cost, heuristic;
    public Node parent;

    // Constructor for the Node class.
    public Node(int x, int y, int cost, int heuristic, Node parent) {
        this.x = x; // X coordinate of the node in the maze.
        this.y = y; // Y coordinate of the node in the maze.
        this.cost = cost; // Cost to reach this node from the start node.
        this.heuristic = heuristic; // Heuristic value (estimated cost) to reach the goal from this node.
        this.parent = parent; // Reference to the parent node in the path.
    }

    // Implementation of the compareTo method from the Comparable interface.
    @Override
    public int compareTo(Node other) {
        // Compares this node with another node based on the sum of the cost and heuristic.
        // This is crucial for sorting in the priority queue used in A* search.
        return Integer.compare(this.cost + this.heuristic, other.cost + other.heuristic);
    }

    // Overridden toString method for easy printing and debugging.
    @Override
    public String toString() {
        // Returns a string representation of the node, showing its coordinates, cost, and heuristic value.
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", cost=" + cost +
                ", heuristic=" + heuristic +
                '}';
    }
}

