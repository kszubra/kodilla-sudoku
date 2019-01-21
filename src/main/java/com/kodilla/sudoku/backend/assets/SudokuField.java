package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.exceptions.ValueNotAvailableException;
import com.kodilla.sudoku.backend.logger.Logger;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.sudoku.backend.assets.Block.*;

@Getter
public class SudokuField {
    public static final int EMPTY = -1;

    private final BoardCoordinates coordinates;
    private int value;
    private List<Integer> possibleValues = new ArrayList<>();
    private Block block;

    public SudokuField(int coordinateRow, int coordinateColumn) {
        this.coordinates = new BoardCoordinates(coordinateRow, coordinateColumn);
        this.value = EMPTY;
        for(int i = 1; i<10; i++){
            possibleValues.add(i);
        }

        assignToBlock();
        Logger.getInstance().log("Creating new SudokuField at Row: " + coordinateRow + " Column: " + coordinateColumn + " in block: " + block);
    }

    private void assignToBlock() {
        int row = coordinates.getRow();
        int column = coordinates.getColumn();

        if( (row > -1 && row < 3) && (column > -1 && column <3) ) {
            this.block = ONE;
        }
        if( (row > -1 && row < 3) && (column > 2 && column <6) ) {
            this.block = TWO;
        }
        if( (row > -1 && row < 3) && (column > 5 && column <9) ) {
            this.block = THREE;
        }

        if( (row > 2 && row < 6) && (column > -1 && column <3) ) {
            this.block = FOUR;
        }
        if( (row > 2 && row < 6) && (column > 2 && column <6) ) {
            this.block = FIVE;
        }
        if( (row > 2 && row < 6) && (column > 5 && column <9) ) {
            this.block = SIX;
        }

        if( (row > 5 && row < 9) && (column > -1 && column <3) ) {
            this.block = SEVEN;
        }
        if( (row > 5 && row < 9) && (column > 2 && column <6) ) {
            this.block = EIGHT;
        }
        if( (row > 5 && row < 9) && (column > 5 && column <9) ) {
            this.block = NINE;
        }
    }

    /**
     * DO NOT USE METHOD BELOW ON ITS OWN - ALWAYS THROUTH GAME BOARD. BOARD REMOVES SET VALUE FROM OTHER FIELDS.
     * USING THIS METHOD ALONE WOULDN'T SECURE IT.
     * */

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
                " and column " + coordinates.getColumn() + " in block " + block);
    }

    public int getRow() {
        return coordinates.getRow();
    }

    public int getColumn() {
        return coordinates.getColumn();
    }

    @Override
    public String toString() {
        return "B" + block + " R" + coordinates.getRow() + " C" + coordinates.getRow() + " V" + value;
    }
}
