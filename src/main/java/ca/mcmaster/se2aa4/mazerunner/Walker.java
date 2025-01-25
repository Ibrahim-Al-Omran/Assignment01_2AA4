package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Walker{

    private static final Logger logger = LogManager.getLogger();

    /*
    private void explore(){
        //the function that moves through the maze, it will go in any open tile
        Queue<Position> queue = new LinkedList<>();
        Set<Position> wentOver = new HashSet<>();

        while (!queue.isEmpty()){
            Position curr = queue.poll();
        }
    }*/

    public void checkPath(String filename, String path){
        
        try {
            //convert maze into 2D array
            char[][] maze = readFile(filename);

            //find the start and end of maze
            List<Position> ends = findEnds(maze);
            Position start = ends.get(0);
            Position end = ends.get(1);

            //now check if path is valid
            boolean validPath = followPath(maze, start, path, end);

            if(validPath){
                System.out.println("Correct path");
            }
            else{
                System.out.println("Incorrect path");
            }
        }
        catch (Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
    }


    private char[][] readFile(String filename) throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] maze = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            maze[i] = lines.get(i).toCharArray();
        }
        return maze;
    }
    
    private static List<Position> findEnds(char[][] maze) {
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
    
    private boolean followPath(char[][] maze, Position start, String path, Position end) {
        // directions: 0 = up, 1 = right, 2 = down, 3 = left (from top view)
        int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int direction = 1; // begin facing right
    
        Position curr = new Position(start.row, start.col);
    
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
        
            if (!isValidMove(maze, curr)) return false;
        }
        if (curr.row == end.row && curr.col == end.col) return true;
        else return false;
    }
    
    private boolean isValidMove(char[][] maze, Position pos) {
        //ensure the row and column are within bounds
        if (pos.row < 0 || pos.row >= maze.length || pos.col < 0 || pos.col >= maze[pos.row].length) {
            return false;
        }
        //ensure position is not a wall
        return maze[pos.row][pos.col] != '#';
    }
    
    static class Position {
        int col;
        int row;
        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
