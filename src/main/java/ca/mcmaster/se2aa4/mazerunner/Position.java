package ca.mcmaster.se2aa4.mazerunner;

import java.util.Objects;

//class used in walker to represent a position in the maze
public class Position {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    public int hashCode() {
        return Objects.hash(row, col);
    }
}