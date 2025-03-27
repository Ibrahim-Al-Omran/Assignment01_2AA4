package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractWalker implements Walker {
    private StringOps operator = new StringOps();
    private Reader reader = new Reader();
    protected String filename;
    protected char[][] maze;
    protected List<Position> ends;
    protected Position start;
    protected Position end;
    protected Position curr;
    protected final Logger logger = LogManager.getLogger();

    public AbstractWalker(String filename) {
        this.filename = filename;
        try {
            this.maze = reader.readFile(filename);
        } catch (Exception e) {
            logger.error("/!\\ An error has occurred /!\\");
        }
        this.ends = findEnds(maze);
        this.start = ends.get(0);
        this.end = ends.get(1); 
        this.curr = new Position(start.row, start.col);
    }
    
    
    // Template method implementation that follows the template pattern
    @Override
    public final String explore() {
        initializeExploration();
        String path = performExploration();
        return operator.factorize(path);
    }
    

    // Initializes the exploration process
    protected void initializeExploration() {
        curr = new Position(start.row, start.col);
    }
    
   
    protected abstract String performExploration();
    
    
    //Finds the entry and exit points in the maze
    protected List<Position> findEnds(char[][] maze) {
        List<Position> coords = new ArrayList<>();
        for (int i = 0; i < maze.length; i++) {
            if (maze[i].length > 0 && maze[i][0] == ' ') {
                coords.add(new Position(i, 0)); 
                break;
            }
        }    
        for (int i = 0; i < maze.length; i++) {
            if (maze[i].length > 0 && maze[i][maze[i].length - 1] == ' ') { 
                coords.add(new Position(i, maze[i].length - 1)); 
                break; 
            }
        }
        return coords;
    }
    
    @Override
    public boolean isValidMove(char[][] maze, Position pos) {
        // Ensure the row and column are within bounds
        if (pos.row < 0 || pos.row >= maze.length || pos.col < 0 || pos.col >= maze[pos.row].length) {
            return false;
        }
        // Ensure position is not a wall
        return maze[pos.row][pos.col] != '#';
    }

    
}
