package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.exceptions.ValueNotAvailableException;
import com.kodilla.sudoku.backend.logger.Logger;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SudokuField {
    public static final int EMPTY = -1;

    private final BoardCoordinates coordinates;
    private int value;
    private List<Integer> possibleValues = new ArrayList<>();

    public SudokuField(int coordinateRow, int coordinateColumn) {
        Logger.getInstance().log("Creating new SudokuField at Row: " + coordinateRow + " Column: " + coordinateColumn);
        this.coordinates = new BoardCoordinates(coordinateRow, coordinateColumn);
        this.value = EMPTY;
        for(int i = 1; i<10; i++){
            possibleValues.add(i);
        }
    }

    public void setValue(int value) throws ValueNotAvailableException {
        if(possibleValues.contains(value)) {
            Logger.getInstance().log("Setting SudokuField(" + this.coordinates.toString() + ") value for: " + value);
            this.value = value;
        } else {
            throw new ValueNotAvailableException("Exception: " + value + " is not available for the field " + coordinates.toString());
        }
    }

    public void removeFromPossibleValues(Integer valueToRemove) {
        possibleValues.remove(new Integer(valueToRemove));
        Logger.getInstance().log("Removing " + valueToRemove + " from possible values in row " + coordinates.getRow() +
                " and column " + coordinates.getColumn());
    }

    public int getRow() {
        return coordinates.getRow();
    }

    public int getColumn() {
        return coordinates.getColumn();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
