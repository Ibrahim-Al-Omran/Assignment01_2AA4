package ca.mcmaster.se2aa4.mazerunner;

public class Checker extends Walker {
    
     public Checker(String filename, String path) {
         super(filename, path);
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
        return curr.row == end.row && curr.col == end.col;
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

}
