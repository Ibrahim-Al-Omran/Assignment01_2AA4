package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


abstract class Walker{
    String filename;
    String path;
    char[][] maze;
    List<Position> ends;
    Position start;
    Position end;
    Position curr;
    protected final Logger logger = LogManager.getLogger();

    public Walker(String filename, String path){
        this.filename = filename;
        this.path = path;
        try {
            this.maze = readFile(filename);
        } catch (Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
        this.ends = findEnds(maze);
        this.start = ends.get(0);
        this.end = ends.get(1); 
        this.curr = new Position(start.row, start.col);
        
    }
    public Walker(String filename){
        this.filename = filename;
        try {
            this.maze = readFile(filename);
        } catch (Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }
        this.ends = findEnds(maze);
        this.start = ends.get(0);
        this.end = ends.get(1); 
        this.curr = new Position(start.row, start.col);
        
    } 
    
    public String explore(){
        return null;
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
    
    private List<Position> findEnds(char[][] maze) {
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
    
    
    protected boolean isValidMove(char[][] maze, Position pos) {
        //ensure the row and column are within bounds
        if (pos.row < 0 || pos.row >= maze.length || pos.col < 0 || pos.col >= maze[pos.row].length) {
            return false;
        }
        //ensure position is not a wall
        return maze[pos.row][pos.col] != '#';
    }

    protected String factorize(String path) {
        if (path == null || path.isEmpty()) {
            return path; //handle empty or null inputs
        }
    
        StringBuilder newPath = new StringBuilder();
        int count = 1;
        char prev = path.charAt(0);
    
        for (int i = 1; i < path.length(); i++) {
            if (path.charAt(i) == prev) {
                count++;
            } else {
                if (count == 1) {
                    newPath.append(prev);
                } else {
                    newPath.append(count).append(prev);
                }
                count = 1;
                prev = path.charAt(i);
            }
        }
    
        //append the last character or its count
        if (count == 1) {
            newPath.append(prev);
        } else {
            newPath.append(count).append(prev);
        }
    
        return newPath.toString();
    }
    
    protected String convertFactorized(String path) {
        if (path == null || path.isEmpty()) {
            return path; 
        }
    
        StringBuilder newPath = new StringBuilder();
        int i = 0;
    
        while (i < path.length()) {
            char c = path.charAt(i);
    
            //check if the current character is a digit
            if (Character.isDigit(c)) {
                //extract the full count
                int count = 0;
                while (i < path.length() && Character.isDigit(path.charAt(i))) {
                    count = count * 10 + Character.getNumericValue(path.charAt(i)); //convert char to int
                    i++;
                }
    
                //append the character count times
                if (i < path.length()) {
                    char ch = path.charAt(i);
                    for (int j = 0; j < count; j++) {
                        newPath.append(ch);
                    }
                    i++;
                } else {
                    // Handle malformed input (e.g., ends with a digit)
                    throw new IllegalArgumentException("Error -- Invalid factorized path");
                }
            } else {
                //append character as-is
                newPath.append(c);
                i++;
            }
        }
    
        return newPath.toString();
    }
    
    
}

