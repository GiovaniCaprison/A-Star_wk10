import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Lab10 {

    // Constant representing the size of the maze.
    private static final int MAZE_SIZE = 20;

    public static void main(String[] args) {

        // Path to the maze file.
        File file = new File("/example/file/path.txt");

        // Number of lives (moves allowed) in the game.
        int lives = 200;

        // Starting positions in the maze.
        int posX;
        int posY;

        // Array to store each line of the maze as read from the file.
        String[] input = new String[MAZE_SIZE];
        try {
            Scanner scan = new Scanner(file);

            // Reading the maze structure line by line.
            for (int i = 0; i < MAZE_SIZE; i++) {
                input[i] = scan.nextLine();
            }

            // Reading the starting X and Y coordinates.
            posX = Integer.parseInt(scan.nextLine());
            posY = Integer.parseInt(scan.nextLine());
            scan.close();
        } catch (FileNotFoundException e) {
            System.err.println("The specified file could not be found: " + e.getMessage());
            return; // Exit if file not found.
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse a number from the file: " + e.getMessage());
            return; // Exit on parsing error.
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            return; // Exit on any other error.
        }

        // Creating a 2D boolean array to represent the maze (true for open space, false for walls).
        boolean[][] maze = new boolean[MAZE_SIZE][MAZE_SIZE];
        for (int i = 0; i < MAZE_SIZE; i++) {
            for (int j = 0; j < MAZE_SIZE; j++) {
                maze[i][j] = input[i].charAt(j) != 'X'; // 'X' represents a wall.
            }
        }

        // Initializing the maze solver with the maze matrix and starting positions.
        MazeSolver solver = new MazeSolver(maze, posX, posY);

        // Finding a path through the maze using A* search algorithm.
        List<Node> path = solver.aStarSearch(posX, posY);
        if (path.isEmpty()) {
            System.out.println("No path found!");
            return; // Exit if no path is found.
        }

        // Creating an instance of Brain to determine the moves along the path.
        Brain myBrain = new Brain(path);

        // Main game loop, continues until lives run out.
        while (lives > 0) {
            System.out.println("Current position: " + posX + " " + posY);

            System.out.println();

            // Print the current state of the maze.
            for (int i = 0; i < MAZE_SIZE; i++) {
                for (int j = 0; j < MAZE_SIZE; j++) {
                    if (posX == i && posY == j) {
                        System.out.print("o"); // Mark the current position.
                    } else if (maze[i][j]) {
                        System.out.print(" "); // Open space.
                    } else {
                        System.out.print("X"); // Wall.
                    }
                }
                System.out.println();
            }

            // Delay to slow down the execution for better visualization.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            }

            // Get the next move from Brain based on the path.
            String move = myBrain.getMove();

            // Move the player according to the path and if the move is valid.
            if (move.equals("north") && posY > 0 && maze[posX - 1][posY]) {
                posX--;
            } else if (move.equals("south") && posY < 19 && maze[posX + 1][posY]) {
                posX++;
            } else if (move.equals("east") && posX < 19 && maze[posX][posY + 1]) {
                posY++;
            } else if (move.equals("west") && posX > 0 && maze[posX][posY - 1]) {
                posY--;
            }

            lives--; // Decrease life count after each move.

            System.out.println();

            System.out.println("Lives remaining: " + lives);

            System.out.println("---------------------------------");

            // Check if the exit is found.
            if (solver.isExit(posX, posY)) {
                System.out.println("You found the exit at: " + posX + "," + posY + " with " + lives + " lives remaining!");
                System.exit(0);
            }
        }

        System.out.println("You died in the maze!");
    }
}

