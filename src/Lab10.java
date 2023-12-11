import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Lab10 {
    private static final int MAZE_SIZE = 20;

    public static void main(String[] args) {
        File file = new File("/example/file/path.txt");
        int lives = 200;
        int posX = 0;
        int posY = 0;
        String[] input = new String[MAZE_SIZE];
        try {
            Scanner scan = new Scanner(file);

            for (int i = 0; i < MAZE_SIZE; i++) {
                input[i] = scan.nextLine(); // Read the maze
            }
            posX = Integer.parseInt(scan.nextLine());
            posY = Integer.parseInt(scan.nextLine());
            scan.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        boolean[][] maze = new boolean[MAZE_SIZE][MAZE_SIZE];
        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                maze[i][j] = input[i].charAt(j) != 'X';
            }
        }

        MazeSolver solver = new MazeSolver(maze, posX, posY);

        List<Node> path = solver.aStarSearch(posX, posY);
        if (path.isEmpty()) {
            System.out.println("No path found!");
            return;
        }

        Brain myBrain = new Brain(path);

        while (lives > 0) {
            System.out.println("Current position: " + posX + " " + posY);
            for (int i = 0; i < 20; i++) { // Print out the map
                for (int j = 0; j < 20; j++) {
                    if (posX == i && posY == j) {
                        System.out.print("o"); // Current position
                    } else if (maze[i][j]) {
                        System.out.print(" "); // There is a space
                    } else {
                        System.out.print("X"); // There is a wall
                    }
                }
                System.out.println();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String move = myBrain.getMove(posX, posY);
            if (move.equals("north") && posY > 0 && maze[posX - 1][posY]) {
                posX--; // Move North if possible
            } else if (move.equals("south") && posY < 19 && maze[posX + 1][posY]) {
                posX++; // Move South if possible
            } else if (move.equals("east") && posX < 19 && maze[posX][posY + 1]) {
                posY++; // Move East if possible
            } else if (move.equals("west") && posX > 0 && maze[posX][posY - 1]) {
                posY--; // Move West if possible
            }

            lives--;

            // Check if the exit is found
            if (solver.isExit(posX, posY)) {
                System.out.println("You found the exit at: " + posX + "," + posY);
                System.exit(0);
            }
        }

        System.out.println("You died in the maze!");
    }
}

