package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        Walker walker = new Walker();
        try {
            if (args.length ==2 && args[0].equals("-i")){
                printMaze(args[1]);
            }
            else if (args.length == 4 && args[0].equals("-i") && args[2].equals("-p")){
                logger.info("**** Reading the maze from file " + args[1]);
                walker.checkPath(args[1], args[3]);
            }
            else throw new Exception(); 
        }
        catch(Exception e) {
            logger.error("/!\\ An error has occured /!\\");
        }

        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }


    private static void printMaze(String filename) throws Exception{
        logger.info("**** Reading the maze from file " + filename);
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line;
                while ((line = reader.readLine()) != null) {
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            logger.trace("WALL ");
                        } else if (line.charAt(idx) == ' ') {
                            logger.trace("PASS ");
                        }
                    }
                    logger.trace(System.lineSeparator());
                }
    }
}


