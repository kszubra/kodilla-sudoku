package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;
import com.kodilla.sudoku.backend.logger.Logger;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SudokuBlock {

    private static final int MAX_FIELDS = 9;
    private final BoardCoordinates coordinates;
    private List<SudokuField> fieldsInBlock;

    public SudokuBlock(int coordinateRow, int coordinateColumn) {
        Logger.getInstance().log("Creating SudokuBlock at X: " + coordinateRow +
                                                                " Y: " + coordinateColumn);
        this.coordinates = new BoardCoordinates(coordinateRow, coordinateColumn);
        this.fieldsInBlock = new ArrayList<>();
    }

    public void addFieldToBlock(SudokuField field) throws TooManyItemsException {
        if(fieldsInBlock.size() < MAX_FIELDS) {
            fieldsInBlock.add(field);
        } else {
            throw new TooManyItemsException("Exception: this block is already full");
        }

    }
}
