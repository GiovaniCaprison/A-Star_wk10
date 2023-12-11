import java.util.*;

public class Brain {
    private final Iterator<Node> pathIterator;
    private Node nextNode;

    public Brain(List<Node> path) {
        this.pathIterator = path.iterator();
        this.nextNode = pathIterator.next(); // Start node
    }

    public String getMove(int currentX, int currentY) {
        if (!pathIterator.hasNext()) return ""; // End of path

        Node currentNode = nextNode;
        nextNode = pathIterator.next();

        if (currentNode.x - 1 == nextNode.x) return "north";
        if (currentNode.x + 1 == nextNode.x) return "south";
        if (currentNode.y - 1 == nextNode.y) return "west";
        if (currentNode.y + 1 == nextNode.y) return "east";

        return "err"; // Should not reach here if the path is valid
    }
}

