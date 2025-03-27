package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Checker {
    private String filename;
    private StringOps operator = new StringOps();
    private String path;
    private char[][] maze;
    private Position start;
    private Position end;
    private Position curr;
    private final Logger logger = LogManager.getLogger();
    private Walker pathHelper; // Use Walker interface for path operations

    public Checker(String filename, String path) {
        this.filename = filename;
        this.path = path;
        
        // Create a temporary PathHelper to handle file loading and utility methods
        this.pathHelper = new PathHelper(filename);
        
        // We only need these fields from the helper
        this.maze = ((PathHelper)pathHelper).getMaze();
        this.start = ((PathHelper)pathHelper).getStart();
        this.end = ((PathHelper)pathHelper).getEnd();
        this.curr = new Position(start.row, start.col);
    }

    public void checkPath() {
        try {
            if (checkFactorized(path)) {
                path = operator.convertFactorized(path);
            }
            // Check if path is valid
            boolean validPath = followPath(maze, start, path, end);

            if (validPath) {
                System.out.println("Correct path");
            } else {
                System.out.println("Incorrect path");
            }
        } catch (Exception e) {
            logger.error("/!\\ An error has occurred /!\\");
        }
    }

    private boolean followPath(char[][] maze, Position start, String path, Position end) {
        // directions: 0 = up, 1 = right, 2 = down, 3 = left (from top view)
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int direction = 1; // begin facing right
       
        for (char move : path.toCharArray()) {
            switch (move) {
                case 'F': // move forward in current direction
                    curr.row += directions[direction][0];
                    curr.col += directions[direction][1];
                    break;
                case 'L': // turn left (CCW)
                    direction = (direction + 3) % 4;
                    break;
                case 'R': // turn right (CW)
                    direction = (direction + 1) % 4; 
                    break;
                default:
                    return false;
            }
        
            if (!pathHelper.isValidMove(maze, curr)) return false;
        }
        return curr.row == end.row && curr.col == end.col;
    }

    private boolean checkFactorized(String path) {
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    
    // Private helper class that implements Walker interface just for utility methods
    private static class PathHelper extends AbstractWalker {
        public PathHelper(String filename) {
            super(filename);
        }
        
        @Override
        protected String performExploration() {
            // Not used by Checker, just a placeholder implementation
            return "";
        }
        
        // Expose necessary fields
        public char[][] getMaze() {
            return maze;
        }
        
        public Position getStart() {
            return start;
        }
        
        public Position getEnd() {
            return end;
        }
    }
}
