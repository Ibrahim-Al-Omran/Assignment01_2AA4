package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashSet;
import java.util.Set;

public class RightHand extends AbstractWalker {

    private int direction; // 0 = up, 1 = right, 2 = down, 3 = left
    private StringBuilder path;

    public RightHand(String filename) {
        super(filename);
    }

    @Override
    protected void initializeExploration() {
        super.initializeExploration();
        this.direction = 1; // begin facing right
        this.path = new StringBuilder();
    }

    @Override
    protected String performExploration() {
        Set<String> visited = new HashSet<>();

        while (!curr.equals(end)) {
            String state = curr.row + "," + curr.col + "," + direction;
            if (visited.contains(state)) break; // Avoid infinite loops
            visited.add(state);

            Position rightPos = getRight();
            Position frontPos = getFront();
            Position leftPos = getLeft();

            if (isValidMove(maze, rightPos)) {
                right();
                forward();
            } else if (isValidMove(maze, frontPos)) {
                forward();
            } else if (isValidMove(maze, leftPos)) {
                left();
                forward();
            } else {
                uTurn();
            }
        }

        return path.toString();
    }

    private Position getRight() {
        Position pos = new Position(curr.row, curr.col);
        move(pos, (direction + 1) % 4); // Right is current direction + 1
        return pos;
    }

    private Position getFront() {
        Position pos = new Position(curr.row, curr.col);
        move(pos, direction); // Front is current direction
        return pos;
    }

    private Position getLeft() {
        Position pos = new Position(curr.row, curr.col);
        move(pos, (direction + 3) % 4); // Left is current direction + 3 (equivalent to -1)
        return pos;
    }

    private void move(Position pos, int dir) {
        switch (dir % 4) {
            case 0 -> pos.row -= 1; // Up
            case 1 -> pos.col += 1; // Right
            case 2 -> pos.row += 1; // Down
            case 3 -> pos.col -= 1; // Left
        }
    }

    private void forward() {
        path.append("F");
        move(curr, direction);
    }

    private void right() {
        direction = (direction + 1) % 4;
        path.append("R");
    }

    private void left() {
        direction = (direction + 3) % 4;
        path.append("L");
    }

    private void uTurn() {
        direction = (direction + 2) % 4;
        path.append("RR");
    }
}