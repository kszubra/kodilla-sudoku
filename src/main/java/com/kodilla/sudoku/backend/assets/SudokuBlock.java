package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SudokuBlock {

    private static final int MAX_FIELDS = 9;
    private final int blockNumber;
    private List<SudokuField> fieldsInBlock;

    public SudokuBlock(int number) {
        this.blockNumber = number;
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
