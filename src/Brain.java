import java.util.*;

public class Brain {
    private final Iterator<Node> pathIterator; // Iterator to traverse through the list of nodes.
    private Node nextNode; // Stores the next node in the path.

    // Constructor for the Brain class.
    public Brain(List<Node> path) {
        this.pathIterator = path.iterator(); // Initialize the iterator with the provided path.
        this.nextNode = pathIterator.next(); // Start with the first node in the path.
    }

    // Method to determine the direction to move based on the current and next nodes.
    public String getMove() {
        if (!pathIterator.hasNext()) return ""; // Return an empty string if there are no more nodes in the path.

        Node currentNode = nextNode; // Store the current node.
        nextNode = pathIterator.next(); // Update to the next node in the path.

        // Determine the direction to move based on the relative positions of the current and next nodes.
        if (currentNode.x - 1 == nextNode.x) return "north"; // Move north if the next node is directly above the current node.
        if (currentNode.x + 1 == nextNode.x) return "south"; // Move south if the next node is directly below the current node.
        if (currentNode.y - 1 == nextNode.y) return "west"; // Move west if the next node is to the left of the current node.
        if (currentNode.y + 1 == nextNode.y) return "east"; // Move east if the next node is to the right of the current node.

        return "err"; // Return an error string if the path is invalid or the direction cannot be determined.
    }
}

