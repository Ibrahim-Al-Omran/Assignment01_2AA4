package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Walker{
    String filename;
    String path;
    char[][] maze;
    List<Position> ends;
    Position start;
    Position end;
    Position curr;
    private static final Logger logger = LogManager.getLogger();

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
 

    public String explore() {
        StringBuilder solution = new StringBuilder();
        Stack<Position> checkpoints = new Stack<>();
        Stack<Integer> directionStack = new Stack<>(); //store direction at checkpoints
        Stack<Integer> solutionLengthStack = new Stack<>(); //store solution length at checkpoints
        Set<Position> visited = new HashSet<>();  //track visited positions
        int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; //up, right, down, left
        boolean moving = true;
        int direction = 1; //start facing right (1 = right, 2 = down, 3 = left, 0 = up)
    
        while (moving) {
           
            visited.add(new Position(curr.row, curr.col));
    
            //check if reached end
            if (curr.row == end.row && curr.col == end.col) {
                moving = false;
                break;
            }
    
            //find possible moves
            List<Position> options = new ArrayList<>();
            for (int[] move : moves) {
                int newRow = curr.row + move[0];
                int newCol = curr.col + move[1];
                Position nextPos = new Position(newRow, newCol);
    
                //check if move is valid and not visited
                if (newRow >= 0 && newRow < maze.length && 
                    newCol >= 0 && newCol < maze[newRow].length && 
                    maze[newRow][newCol] == ' ' && 
                    !visited.contains(nextPos)) {
                    options.add(nextPos);
                }
            }
    
            if (!options.isEmpty()) {
                //save checkpoint if there are multiple paths
                if (options.size() > 1) { 
                    checkpoints.push(new Position(curr.row, curr.col));
                    directionStack.push(direction);
                    solutionLengthStack.push(solution.length());
                }
    
                //move to the first available option
                Position next = options.get(0);
                Pair<String, Integer> moveResult = moveDirection(next, direction);
                solution.append(moveResult.getFirst());
                direction = moveResult.getSecond(); //update direction
                curr = next;
            } 
            else {
                //backtrack to the last checkpoint
                if (!checkpoints.isEmpty()) {
                    //remove current position from visited to allow re-exploration
                    visited.remove(curr);
                    //restore checkpoint and direction
                    Position checkpoint = checkpoints.pop();
                    direction = directionStack.pop();
                    curr = checkpoint;
    
                    //remove the moves added since the last checkpoint
                    int lastSolutionLength = solutionLengthStack.pop();
                    solution.setLength(lastSolutionLength);
    
                } 
                else {
                    moving = false; //no more moves possible
                }
            }
        }
        String path = solution.toString();
        return factorize(path);
    }


    private Pair<String, Integer> moveDirection(Position next, int direction) {
        //calculate the difference between the current position and the next position
        int rowDiff = next.row - curr.row;
        int colDiff = next.col - curr.col;
    
        //determine relative direction of the next move
        int nextDirection;
        if (rowDiff == -1 && colDiff == 0) {
            nextDirection = 0; //up
        } else if (rowDiff == 0 && colDiff == 1) {
            nextDirection = 1; //right
        } else if (rowDiff == 1 && colDiff == 0) {
            nextDirection = 2; //down
        } else if (rowDiff == 0 && colDiff == -1) {
            nextDirection = 3; //left
        } else {
            return new Pair<>("", direction); //invalid move (shouldnt happen)
        }
    
        //calculate the turn required to face the next direction
        int turn = (nextDirection - direction + 4) % 4;
    
        //determine the movement command and new direction
        switch (turn) {
            case 0:
                return new Pair<>("F", nextDirection); //move forward (no turn needed)
            case 1:
                return new Pair<>("RF", nextDirection); //turn right and move forward
            case 2:
                return new Pair<>("RRF", nextDirection); //turn around (right twice) and move forward (should not happen but still need to handle)
            case 3:
                return new Pair<>("LF", nextDirection); //turn left and move forward
            default:
                return new Pair<>("", direction); //invalid turn (should not happen)
        }
    }


    public void checkPath(){
        try {
            if (checkFactorized(path)){
                path = convertFactorized(path);
            }
            //check if path is valid
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

    private String factorize(String path) {
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
    
    private String convertFactorized(String path) {
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
    
    private boolean checkFactorized(String path){
        for (int i = 0; i<path.length(); i++){
            char c = path.charAt(i);
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }

    private static class Pair<K, V> {
        private K first;
        private V second;
    
        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    
        public K getFirst() {
            return first;
        }
    
        public V getSecond() {
            return second;
        }
    }
}

