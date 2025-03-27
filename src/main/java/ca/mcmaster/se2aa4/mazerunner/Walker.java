package ca.mcmaster.se2aa4.mazerunner;

public interface Walker {
    
     //template method that coordinates the exploration algorithm
    String explore();
    
    //Checks if a move to a position is valid in the maze
    boolean isValidMove(char[][] maze, Position pos);
    
    
}

