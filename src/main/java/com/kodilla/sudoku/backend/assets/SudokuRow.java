package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;

import java.util.ArrayList;
import java.util.List;

public class SudokuRow {

    private static final int MAX_FIELDS = 9;
    private int rowNumber;
    private List<SudokuField> fieldsInRow;

    public SudokuRow(int rowNumber) {
        this.rowNumber = rowNumber;
        this.fieldsInRow = new ArrayList<>();
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public List<SudokuField> getFieldsInRow() {
        return fieldsInRow;
    }

    public void addField(SudokuField field) throws TooManyItemsException {
        if(fieldsInRow.size() < MAX_FIELDS) {
            fieldsInRow.add(field);
        } else {
            throw new TooManyItemsException("Exception: this row is already full");
        }
    }
}
