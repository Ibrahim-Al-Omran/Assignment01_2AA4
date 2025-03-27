package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Tremaux extends AbstractWalker {

    public Tremaux(String filename) {
        super(filename);
    }

    @Override
    protected String performExploration() {
        StringBuilder solution = new StringBuilder();
        Map<Position, Integer> visited = new HashMap<>();
        Stack<Position> pathStack = new Stack<>();

        // Directions: 0 = up, 1 = right, 2 = down, 3 = left
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int direction = 1; // Begin facing right

        pathStack.push(curr);
        visited.put(curr, 1);

        while (!pathStack.isEmpty()) {
            Position pos = pathStack.peek();

            // Check if reached end
            if (pos.row == end.row && pos.col == end.col) {
                break;
            }

            boolean moved = false;

            for (int i = 0; i < 4; i++) {
                int newDir = (direction + i) % 4;
                Position newPos = new Position(pos.row + directions[newDir][0], pos.col + directions[newDir][1]);

                if (isValidMove(maze, newPos)) {
                    int visitCount = visited.getOrDefault(newPos, 0);

                    if (visitCount < 2) {
                        if (i == 1) {
                            solution.append("R"); 
                        } else if (i == 2) {
                            solution.append("RR"); 
                        } else if (i == 3) {
                            solution.append("L");
                        }

                        direction = newDir;
                        pathStack.push(newPos);
                        visited.put(newPos, visitCount + 1);
                        solution.append("F");
                        moved = true;
                        break;
                    }
                }
            }

            if (!moved) {
                pathStack.pop();
                solution.append("RR");
            }
        }

        return solution.toString();
    }
}
