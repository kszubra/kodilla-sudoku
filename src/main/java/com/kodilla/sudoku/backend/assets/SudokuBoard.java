package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;
import com.kodilla.sudoku.backend.exceptions.ValueNotAvailableException;
import com.kodilla.sudoku.backend.logger.Logger;
import lombok.Getter;

import java.util.*;

@Getter
public class SudokuBoard {

    private static final int FIELDS_NUMBER = 81;
    private static final int BLOCKS_NUMBER = 9;
    private static final int ROWS_NUMBER = 9;
    private static final int COLUMNS_NUMBER = 9;

    private final Random randomGenerator = new Random();

    private Map<BoardCoordinates, SudokuField> fields;
    private List<SudokuBlock> boardBlocks;
    private List<SudokuRow> boardRows;
    private List<SudokuColumn> boardColumns;


    public SudokuBoard() {
        fields = new HashMap<>();
        boardRows = new ArrayList<>();
        boardColumns = new ArrayList<>();
        boardBlocks = new ArrayList<>();

        for(int number = 0; number < ROWS_NUMBER; number++) {
            boardRows.add(new SudokuRow(number));
            boardColumns.add(new SudokuColumn(number));
        }

        for(int row = 0; row < ROWS_NUMBER; row++) {

            for(int column = 0; column < COLUMNS_NUMBER; column++) {

                BoardCoordinates coordinate = new BoardCoordinates(row, column);
                SudokuField sudokuField = new SudokuField(row, column);

                fields.put(coordinate, sudokuField);

                try {
                    boardRows.get(row).addField(sudokuField);
                    boardColumns.get(column).addField(sudokuField);
                } catch (TooManyItemsException e) {
                    System.out.println(e.getMessage());
                }

            }
        }

    }

    public int getFieldsNumber() {
        return fields.size();
    }


    public void displayBoard() {
        for(SudokuRow row : boardRows) {
            System.out.println(row.getFieldsInRow());
        }
    }

    public SudokuField getSudokuField(int row, int column) {
        return fields.get(new BoardCoordinates(row, column));
    }

    public int getSudokuFieldValue(int row, int column) {
        return fields.get(new BoardCoordinates(row, column)).getValue();
    }

    public void autoSolve() {
        for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
            if(entry.getValue().valueIsEmpty()) {

                int row = entry.getValue().getRow();
                int column = entry.getValue().getColumn();

                int amountOfAvaiableValues = entry.getValue().getPossibleValues().size();
                System.out.println("Amount of possible values at field " +"R"+row + " C"+column + " = " + amountOfAvaiableValues);
                int indexOfRandomAvaiableValue = randomGenerator.nextInt(amountOfAvaiableValues);
                int randomAvaiableValue = entry.getValue().getPossibleValues().get(indexOfRandomAvaiableValue);

                setFieldValue(row, column, randomAvaiableValue);
            }
        }
    }

    public void ereaseFieldValue(int row, int column) {

        BoardCoordinates cellCoordinates = new BoardCoordinates(row, column);
        Block fieldBlock =  fields.get(cellCoordinates).getBlock();
        int value = fields.get(cellCoordinates).resetField();

        for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
            if(entry.getValue().getColumn() == column || entry.getValue().getRow() == row || entry.getValue().getBlock().equals(fieldBlock)) {
                entry.getValue().addToPossibleValues(value);
            }
        }


    }

    public void setFieldValue(int row, int column, int value) {
        try {
            BoardCoordinates cellCoordinates = new BoardCoordinates(row, column);
            fields.get(cellCoordinates).setValue(value);
            Block fieldBlock =  fields.get(cellCoordinates).getBlock();

            for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
                if(entry.getValue().getColumn() == column || entry.getValue().getRow() == row || entry.getValue().getBlock().equals(fieldBlock)) {
                    entry.getValue().removeFromPossibleValues(value);
                }
            }

        } catch (ValueNotAvailableException e) {
            Logger.getInstance().log(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public boolean isComplete() {
        boolean boardIsCompleted = true;

        for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
            if(entry.getValue().valueIsEmpty()) {
                boardIsCompleted = false;
            }
        }

        return boardIsCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SudokuBoard that = (SudokuBoard) o;
        return Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fields);
    }
}
