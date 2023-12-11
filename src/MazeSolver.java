import java.util.*;

public class MazeSolver {
    private static final int MAZE_SIZE = 20;
    private final boolean[][] maze;
    private final int startX;
    private final int startY;

    public MazeSolver(boolean[][] maze, int startX, int startY) {
        this.maze = maze;
        this.startX = startX;
        this.startY = startY;
    }

    public List<Node> aStarSearch(int startX, int startY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        boolean[][] closedSet = new boolean[MAZE_SIZE][MAZE_SIZE];
        Node startNode = new Node(startX, startY, 0, calculateHeuristic(startX, startY), null);

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (isExit(current.x, current.y)) {
                return reconstructPath(current);
            }

            closedSet[current.x][current.y] = true;

            for (int[] dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int nextX = current.x + dir[0];
                int nextY = current.y + dir[1];

                if (isValidMove(nextX, nextY, closedSet)) {
                    int newCost = current.cost + 1;
                    int heuristic = calculateHeuristic(nextX, nextY);
                    Node neighbor = new Node(nextX, nextY, newCost, heuristic, current);

                    openSet.add(neighbor);
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    // Additional methods inside MazeSolver class

    public boolean isExit(int x, int y) {
        return (x == 0 || x == MAZE_SIZE - 1 || y == 0 || y == MAZE_SIZE - 1) && !(x == startX && y == startY);
    }

    private boolean isValidMove(int x, int y, boolean[][] closedSet) {
        // Check if the move is valid
        return x >= 0 && x < MAZE_SIZE && y >= 0 && y < MAZE_SIZE && maze[x][y] && !closedSet[x][y];
    }

    private static int calculateHeuristic(int x, int y) {
        // Calculate Manhattan distance to the nearest edge as a heuristic
        return Math.min(Math.min(x, MAZE_SIZE - 1 - x), Math.min(y, MAZE_SIZE - 1 - y));
    }

    private static List<Node> reconstructPath(Node node) {
        LinkedList<Node> path = new LinkedList<>();
        while (node != null) {
            path.addFirst(node);
            node = node.parent;
        }
        return path;
    }
}
