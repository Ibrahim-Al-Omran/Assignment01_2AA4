package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        
        
        try {
            logger.info("**** Reading the maze from file " + args[1]);
            if (args.length == 2 && args[0].equals("-i") || args.length == 4 && args[0].equals("-i") && args[2].equals("-method")) {
                logger.info("**** Computing path");
                printMaze(args[1]);

                Walker solver;
                if (args.length == 4) {
                    solver = WalkerFactory.createWalker(args[3], args[1]);
                } else {
                    solver = WalkerFactory.createDefaultWalker(args[1]);
                }

                try {
                    String result = solver.explore();
                    System.out.println(result);
                } catch (Exception e) {
                    logger.warn("PATH NOT COMPUTED");
                }
            } 
            else if (args.length >= 4 && args[0].equals("-i") && args[2].equals("-p")) {
                StringBuilder pathBuilder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    pathBuilder.append(args[i]);
                }
                String path = pathBuilder.toString().replaceAll("\\s+", ""); // remove all spaces
                Checker checker = new Checker(args[1], path);
                checker.checkPath();
            }
        } catch (Exception e) {
            logger.error("/!\\ An error has occurred /!\\");
        }

        logger.info("** End of MazeRunner");
    }


    private static void printMaze(String filename) throws Exception{
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


