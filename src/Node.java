public class Node implements Comparable<Node> {
    public int x, y, cost, heuristic;
    public Node parent;

    public Node(int x, int y, int cost, int heuristic, Node parent) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.heuristic = heuristic;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost + this.heuristic, other.cost + other.heuristic);
    }

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", cost=" + cost +
                ", heuristic=" + heuristic +
                '}';
    }
}

