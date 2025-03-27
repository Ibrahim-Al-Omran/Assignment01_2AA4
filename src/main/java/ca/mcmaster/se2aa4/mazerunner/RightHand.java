package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashSet;
import java.util.Set;

public class RightHand extends Walker {

    private int direction; // 0 = up, 1 = right, 2 = down, 3 = left (same as Walker)
    private StringBuilder path; 

    public RightHand(String filename) {
        super(filename);
        this.direction = 1; //begin facing right 
        this.path = new StringBuilder();
    }

    @Override
    public String explore() {
        Set<String> visited = new HashSet<>();

        while (!curr.equals(end)) {
            String state = curr.row + "," + curr.col + "," + direction;
            if (visited.contains(state)) break; //avoid infinite loops
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

        return factorize(path.toString()); 
    }

    private Position getRight() {
        Position pos = new Position(curr.row, curr.col);
        move(pos, (direction + 1) % 4); //right is current direction + 1
        return pos;
    }

    private Position getFront() {
        Position pos = new Position(curr.row, curr.col);
        move(pos, direction); //front is current direction
        return pos;
    }

    private Position getLeft() {
        Position pos = new Position(curr.row, curr.col);
        move(pos, (direction + 3) % 4); //left is current direction + 3 (equivalent to -1)
        return pos;
    }

    private void move(Position pos, int dir) {
        switch (dir % 4) {
            case 0 -> pos.row -= 1; //up
            case 1 -> pos.col += 1; //right
            case 2 -> pos.row += 1; //down
            case 3 -> pos.col -= 1; //left
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