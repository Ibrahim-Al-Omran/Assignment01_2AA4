package ca.mcmaster.se2aa4.mazerunnertest;

import ca.mcmaster.se2aa4.mazerunner.Checker;

public class TestingCheckerHelper extends Checker {
    
    public TestingCheckerHelper(String filename, String path) {
        super(filename, path);
    }
    
    // Expose the checkPath method to test
    @Override
    public void checkPath() {
        super.checkPath();
    }
}