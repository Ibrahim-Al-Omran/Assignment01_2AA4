package ca.mcmaster.se2aa4.mazerunnertest;

import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.RightHand;
import ca.mcmaster.se2aa4.mazerunner.StringOps;

public class TestingRightHandHelper extends RightHand {
    private StringOps operator = new StringOps();
    public TestingRightHandHelper(String filename) {
        super(filename);
    }
    
    // Expose protected methods for testing
    public String factorize(String path) {
        return operator.factorize(path);
    }
    
    public String convertFactorized(String path) {
        return operator.convertFactorized(path);
    }
    
    public boolean isValidMove(char[][] maze, Position pos) {
        return super.isValidMove(maze, pos);
    }
    
    // Access to protected fields for testing
    public Position getStart() {
        return start;
    }
    
    public Position getEnd() {
        return end;
    }
    
    public char[][] getMaze() {
        return maze;
    }
}

