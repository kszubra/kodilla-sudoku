package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;

import java.util.ArrayList;
import java.util.List;

public class SudokuColumn {

    private static final int MAX_FIELDS = 9;
    private int columnNumber;
    private List<SudokuField> fieldsInColumn;

    public SudokuColumn(int columnNumber) {
        this.columnNumber = columnNumber;
        this.fieldsInColumn = new ArrayList<>();
    }

    public int getColumnNumber() {
        return columnNumber;
    }



    public List<SudokuField> getFieldsInColumn() {
        return fieldsInColumn;
    }

    public void addField(SudokuField field) throws TooManyItemsException {
        if(fieldsInColumn.size() < MAX_FIELDS) {
            fieldsInColumn.add(field);
        } else {
            throw new TooManyItemsException("Exception: this column is already full");
        }

    }
}
