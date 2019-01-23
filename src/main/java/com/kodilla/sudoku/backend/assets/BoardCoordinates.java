package com.kodilla.sudoku.backend.assets;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoardCoordinates {

    private int row;
    private int column;

    public BoardCoordinates(BoardCoordinates coordinates) {
        this.row = coordinates.getRow();
        this.column = coordinates.getColumn();
    }

    @Override
    public String toString() {
        return "(Row: " + row +
                " Col: " + column +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardCoordinates that = (BoardCoordinates) o;
        return row == that.row &&
                column == that.column;
    }

    @Override
    public int hashCode() {
        return row;
    }
}
