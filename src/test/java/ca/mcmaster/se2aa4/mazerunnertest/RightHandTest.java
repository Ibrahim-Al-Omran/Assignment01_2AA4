package ca.mcmaster.se2aa4.mazerunnertest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.RightHand;
import ca.mcmaster.se2aa4.mazerunner.Tremaux;

@ExtendWith(MockitoExtension.class)
public class RightHandTest {

    private RightHand right;
    private Tremaux trem;
    private TestingRightHandHelper rightTest;
    String factPathRight;
    String factPathTrem;
    String fullPathRight;
    char[][] exampleMaze;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    public void setup() {
        factPathRight = "FR6F2R8FR2FR2F2R2FR2FR4FR2FL4FL2F2R2FR4FR2FL2FR2FR4FR2F2R2FL2FR2FR4FR2F2R2FL2FR2F2R2FR2FR2F2R4FR2FR2F2R4FR2FR2F2R4FR2FR2F2R2FR10FR2FR8F2R8FL2FR4FR2FR2F2R2FR2FR14F2R12FR2FR6F2R4FR2FR6FR2FL6F2R6FR2FR8F2R12FR2FR10F2R6FR2FR4F2R4FL2FR4FL2FR2FL2FR2FL2FR2FL4FR2FR2F2R4FR2FR6FR2F2R2FR2FR4F2R2FR2FR4F2R4FR2FR2F2R2FR2FR4FR2FL2F2R2FR2FR6FL2FR8F2R8FR2FR10FR4FR2F2R2FR2F2R2FR2FR2FL4FR2F2R4F2R2FR4FR2FR2F2R4FR2FR6F2R6FR4FR2FR2FL2F2R2FR4FR2FR2F2R2FR2FR4F2R4FL4FR2FR4F2R2FR2F2R2FR2FR2F2R6FR2FR8FR6FR2F2R2FL2FRF";
        fullPathRight = "FRFFFFFFRRFFFFFFFFRFFRFFRRFFRFFRFFFFRFFLFFFFLFFRRFFRFFFFRFFLFFRFFRFFFFRFFRRFFLFFRFFRFFFFRFFRRFFLFFRFFRRFFRFFRFFRRFFFFRFFRFFRRFFFFRFFRFFRRFFFFRFFRFFRRFFRFFFFFFFFFFRFFRFFFFFFFFRRFFFFFFFFLFFRFFFFRFFRFFRRFFRFFRFFFFFFFFFFFFFFRRFFFFFFFFFFFFRFFRFFFFFFRRFFFFRFFRFFFFFFRFFLFFFFFFRRFFFFFFRFFRFFFFFFFFRRFFFFFFFFFFFFRFFRFFFFFFFFFFRRFFFFFFRFFRFFFFRRFFFFLFFRFFFFLFFRFFLFFRFFLFFRFFLFFFFRFFRFFRRFFFFRFFRFFFFFFRFFRRFFRFFRFFFFRRFFRFFRFFFFRRFFFFRFFRFFRRFFRFFRFFFFRFFLFFRRFFRFFRFFFFFFLFFRFFFFFFFFRRFFFFFFFFRFFRFFFFFFFFFFRFFFFRFFRRFFRFFRRFFRFFRFFLFFFFRFFRRFFFFRRFFRFFFFRFFRFFRRFFFFRFFRFFFFFFRRFFFFFFRFFFFRFFRFFLFFRRFFRFFFFRFFRFFRRFFRFFRFFFFRRFFFFLFFFFRFFRFFFFRRFFRFFRRFFRFFRFFRRFFFFFFRFFRFFFFFFFFRFFFFFFRFFRRFFLFFRF";
        factPathTrem = "FR6F2R8FR6F2R6FLF6R2F2RF2RF10R18FR2F2R4FR2FR8FR4F2R4FL8FL4F2RF2RF8RLF19R2F2RF2RF16RL12F2R11F2RL4F2R3F6RF16R2F2RF2RF10R2F2RF2RFL2F2RF2RF21R2FR4F2R4FLF8RLF17R10F2R9F7R2FR4F2R4FLF8R4FR2F2R2FL3F2RL2F2RF2RF14RF18RF17R2F2RF2RFL2F2RF2RF26RFL14FR2F2R4F2RF2RF8RL13F2RL8F2R7F14RF24R10FR6F2R16FR6FR2F2R2FL6FL9F2RL2FR2F2R8F2R5F2RL4FR4F2R4FL3F2RL2F2RF2RF18RF24RL4F2R3F3R2F2RF2RF11RF14RLF16RLF4RLFL2F2RF2RF44RLF";
        right = new RightHand("examples/medium.maz.txt");
        trem = new Tremaux("examples/medium.maz.txt");
        rightTest = new TestingRightHandHelper("examples/medium.maz.txt");
        exampleMaze = new char[][] {{' ', '#', '#'}, {' ', ' ', ' '}, {'#', '#', ' '}};
    }
    
    @Test
    public void testRightExplore() throws Exception {
        String rightPath = right.explore();
        assertEquals(factPathRight, rightPath);
    }

    @Test
    public void testTremauxExplore() throws Exception {
        String tremauxPath = trem.explore();
        assertEquals(factPathTrem, tremauxPath);
    }
    
    @Test
    public void testConvertFactorized() {
        String solvedfullPathRight = rightTest.convertFactorized(factPathRight);
        assertEquals(fullPathRight, solvedfullPathRight);
    }
    
    @Test
    public void testFactorize() {
        String resultfactPathRight = rightTest.factorize(fullPathRight);
        assertEquals(factPathRight, resultfactPathRight);
    }
    
    @Test
    public void testIsValidMove() {
        // Setup a small test maze for clarity
        char[][] testMaze = {
            {'#', '#', '#'},
            {'#', ' ', '#'},
            {'#', ' ', '#'}
        };
        
        // Valid positions (empty spaces)
        Position validPos1 = new Position(1, 1);
        Position validPos2 = new Position(2, 1);
        
        // Invalid positions (walls)
        Position wallPos1 = new Position(0, 0);
        Position wallPos2 = new Position(1, 0);
        
        // Out of bounds positions
        Position outOfBoundsPos1 = new Position(-1, 0);
        Position outOfBoundsPos2 = new Position(3, 1);
        
        // Test valid moves
        assertTrue(rightTest.isValidMove(testMaze, validPos1));
        assertTrue(rightTest.isValidMove(testMaze, validPos2));
        
        // Test invalid moves (walls)
        assertFalse(rightTest.isValidMove(testMaze, wallPos1));
        assertFalse(rightTest.isValidMove(testMaze, wallPos2));
        
        // Test out of bounds moves
        assertFalse(rightTest.isValidMove(testMaze, outOfBoundsPos1));
        assertFalse(rightTest.isValidMove(testMaze, outOfBoundsPos2));
    }
    
    @Test
    public void testPositionEquality() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        Position p3 = new Position(2, 1);
        
        // Test equality
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        
        // Test hashCode consistency
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        
        // Test null handling
        assertNotEquals(p1, null);
        
        // Test different type
        assertNotEquals(p1, "Not a Position");
    }
    
    @Test
    public void testRightHandVsTremauxDifferentPaths() throws Exception {
        
        String rightHandPath = right.explore();
        String tremauxPath = trem.explore();
        
        // Both should find valid paths
        assertNotNull(rightHandPath);
        assertNotNull(tremauxPath);
        assertTrue(rightHandPath.matches("^[0-9FLR]*$"));
        assertTrue(tremauxPath.matches("^[0-9FLR]*$"));
    }
    
    @Test
    public void testStartAndEndDetection() throws IOException {
        
        // Check that start and end are correctly identified
        Position start = rightTest.getStart();
        Position end = rightTest.getEnd();
        
        assertEquals(23, start.row);
        assertEquals(0, start.col);
        assertEquals(27, end.row);
        assertEquals(30, end.col);
    }
    
    @Test
    public void testPathValidity() throws Exception {
        
        // First get a valid path with RightHand
        RightHand solver = new RightHand("examples/small.maz.txt");
        String factorizedPath = solver.explore();
        
        // Convert to unfactorized
        String unfactorizedPath = rightTest.convertFactorized(factorizedPath);
        
        // Create a checker with this valid path
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        TestingCheckerHelper checker = new TestingCheckerHelper("examples/small.maz.txt", unfactorizedPath);
        checker.checkPath();
        
        System.setOut(originalOut); // Reset System.out
        String output = outputStream.toString().trim();
        
        // The path should be valid
        assertTrue(output.contains("Correct path"));
        
        // Test with invalid path
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        
        TestingCheckerHelper badChecker = new TestingCheckerHelper("examples/small.maz.txt", "FRFRFRFR"); // Just spin in place
        badChecker.checkPath();
        
        System.setOut(originalOut);
        output = outputStream.toString().trim();
        
        // The path should be invalid
        assertTrue(output.contains("Incorrect path"));
    }
    
    @Test
    public void testHandlingOfCornerCases() {
        // Test empty path
        assertEquals("", rightTest.factorize(""));
        assertEquals("", rightTest.convertFactorized(""));
        
        // Test single character path
        assertEquals("F", rightTest.factorize("F"));
        assertEquals("F", rightTest.convertFactorized("F"));
        
        // Test path with just one direction repeated
        assertEquals("10F", rightTest.factorize("FFFFFFFFFF"));
        assertEquals("FFFFFFFFFF", rightTest.convertFactorized("10F"));
    }
    

}