package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.exceptions.ValueNotAvailableException;
import com.kodilla.sudoku.backend.logger.Logger;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class SudokuField {
    public static final int EMPTY = -1;

    private final BoardCoordinates coordinates;
    private int value;
    private List<Integer> possibleValues = new ArrayList<>();
    private int blockNumber;

    public SudokuField(int coordinateRow, int coordinateColumn) {
        this.coordinates = new BoardCoordinates(coordinateRow, coordinateColumn);
        this.value = EMPTY;
        for(int i = 1; i<10; i++){
            possibleValues.add(i);
        }

        assignToBlock();
        Logger.getInstance().log("Creating new SudokuField at Row: " + coordinateRow + " Column: " + coordinateColumn + " in block: " + blockNumber);
    }

    private void assignToBlock() {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        if( (row > -1 && row < 3) && (column > -1 && column <3) ) {
            this.blockNumber = 0;
        }
        if( (row > -1 && row < 3) && (column > 2 && column <6) ) {
            this.blockNumber = 1;
        }
        if( (row > -1 && row < 3) && (column > 5 && column <9) ) {
            this.blockNumber = 2;
        }

        if( (row > 2 && row < 6) && (column > -1 && column <3) ) {
            this.blockNumber = 3;
        }
        if( (row > 2 && row < 6) && (column > 2 && column <6) ) {
            this.blockNumber = 4;
        }
        if( (row > 2 && row < 6) && (column > 5 && column <9) ) {
            this.blockNumber = 5;
        }

        if( (row > 5 && row < 9) && (column > -1 && column <3) ) {
            this.blockNumber = 6;
        }
        if( (row > 5 && row < 9) && (column > 2 && column <6) ) {
            this.blockNumber = 7;
        }
        if( (row > 5 && row < 9) && (column > 5 && column <9) ) {
            this.blockNumber = 8;
        }
    }

    /**
     * DO NOT USE METHOD BELOW ON ITS OWN - ALWAYS THROUTH GAME BOARD. BOARD REMOVES SET VALUE FROM OTHER FIELDS.
     * USING THIS METHOD ALONE WOULDN'T SECURE IT.
     * */

    public void setValue(int value) throws ValueNotAvailableException {
        if(possibleValues.contains(value)) {
            Logger.getInstance().log("Setting SudokuField(" + this.coordinates.toString() + " Block: " + blockNumber + ") value for: " + value);
            this.value = value;
        } else {
            throw new ValueNotAvailableException("Exception: " + value + " is not available for the field " + coordinates.toString());
        }
    }

    public void removeFromPossibleValues(Integer valueToRemove) {
        possibleValues.remove(new Integer(valueToRemove));
        Logger.getInstance().log("Removing " + valueToRemove + " from possible values in row " + coordinates.getRow() +
                " and column " + coordinates.getColumn() + " in block " +blockNumber + ". Remaining possible values are: " + possibleValues);
    }

    public int getRow() {
        return coordinates.getRow();
    }

    public int getColumn() {
        return coordinates.getColumn();
    }

    public boolean valueIsEmpty() {
        return value == EMPTY;
    }

    public void addToPossibleValues(Integer valueToAdd) {
        if(!possibleValues.contains(valueToAdd)) {

            possibleValues.add(valueToAdd);
            Logger.getInstance().log("Adding " + valueToAdd + " to possible values in row " + coordinates.getRow() +
                    " and column " + coordinates.getColumn() + " in block " + blockNumber + ". Remaining possible values are now: " + possibleValues);
        } else {
            Logger.getInstance().log("Value " + valueToAdd + " was not added to the list of possible values for the field " + coordinates + " because it already exists there");
        }
    }

    public Integer resetField() {
        if(!valueIsEmpty()) {
            Integer valueToErease = new Integer(value);
            addToPossibleValues(valueToErease);
            value = EMPTY;
            return valueToErease;
        } else return null;
    }

    @Override
    public String toString() {
        //return "B" + block + " R" + coordinates.getRow() + " C" + coordinates.getRow() + " V" + value;
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuField that = (SudokuField) o;
        return value == that.value &&
                Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, value);
    }
}
