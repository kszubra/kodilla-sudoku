package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import com.kodilla.sudoku.backend.exceptions.NoValueToEreaseException;
import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;
import com.kodilla.sudoku.backend.exceptions.ValueNotAvailableException;
import com.kodilla.sudoku.backend.generator.BoardGenerator;
import com.kodilla.sudoku.backend.logger.Logger;
import lombok.Getter;

import java.util.*;

@Getter
public class SudokuBoard {

    private static final int BOARD_SIZE = 81;
    private static final int BLOCK_SIZE = 9;
    private static final int ROWS_NUMBER = 9;
    private static final int COLUMNS_NUMBER = 9;

    private final Random randomGenerator = new Random();

    private Map<BoardCoordinates, SudokuField> fields;
    private List<SudokuBlock> boardBlocks;
    private List<SudokuRow> boardRows;
    private List<SudokuColumn> boardColumns;

    private int[][] startingBoard = new int[9][9];


    public SudokuBoard() {
        fields = new HashMap<>();
        boardRows = new ArrayList<>();
        boardColumns = new ArrayList<>();
        boardBlocks = new ArrayList<>();

        for(int number = 0; number < ROWS_NUMBER; number++) {
            boardRows.add(new SudokuRow(number));
            boardColumns.add(new SudokuColumn(number));
            boardBlocks.add(new SudokuBlock(number));
        }

        for(int row = 0; row < ROWS_NUMBER; row++) {

            for(int column = 0; column < COLUMNS_NUMBER; column++) {

                BoardCoordinates coordinate = new BoardCoordinates(row, column);
                SudokuField sudokuField = new SudokuField(row, column);

                fields.put(coordinate, sudokuField);

                try {
                    boardRows.get(row).addField(sudokuField);
                    boardColumns.get(column).addField(sudokuField);
                    boardBlocks.get(sudokuField.getBlockNumber()).addFieldToBlock(sudokuField);
                } catch (TooManyItemsException e) {
                    System.out.println(e.getMessage());
                }

            }
        }

        setStartingExampleBoard();

    }

    private void setStartingExampleBoard() {

        preFill(DifficultyLevel.MEDIUM);

    }


    public void setStartingBoard(int[][] initializeMatrix) {
        startingBoard = initializeMatrix;
        setBoardFromMatrix(initializeMatrix);
    }

    public void setBoardFromMatrix(int[][] matrix) {
        ereaseAllFields();
        for(int row = 0; row<ROWS_NUMBER; row++) {
            for(int column = 0; column<COLUMNS_NUMBER; column ++) {
                int matrixValue = matrix[row][column];
                System.out.println("Setting: " + matrixValue + " in row: " + row + " column: " + column + " of the board");
                setFieldValue(row, column, matrixValue);
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

    public void preFill(DifficultyLevel difficultyLevel) {

        BoardGenerator generator = new BoardGenerator();

        int numberOfFieldsToPreFill;

        switch(difficultyLevel){
            case EASY:
                numberOfFieldsToPreFill = 30;
                break;
            case MEDIUM:
                numberOfFieldsToPreFill = 20;
                break;
            case HARD:
                numberOfFieldsToPreFill = 10;
                break;
            default:
                numberOfFieldsToPreFill = 20;
                break;
        }

        startingBoard = generator.getSolvableBoard(BOARD_SIZE - numberOfFieldsToPreFill);

        setBoardFromMatrix(startingBoard);


    }

    public void ereaseFieldValue(int row, int column) {

        BoardCoordinates cellCoordinates = new BoardCoordinates(row, column);
        int fieldBlock =  fields.get(cellCoordinates).getBlockNumber();
        try {
            int value = fields.get(cellCoordinates).resetField();
            for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
                if(entry.getValue().getColumn() == column || entry.getValue().getRow() == row || entry.getValue().getBlockNumber() == fieldBlock) {
                    entry.getValue().addToPossibleValues(value);
                }
            }
        } catch (NoValueToEreaseException e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean setFieldValue(int row, int column, int value) {
        try {
            BoardCoordinates cellCoordinates = new BoardCoordinates(row, column);
            fields.get(cellCoordinates).setValue(value);
            int fieldBlock =  fields.get(cellCoordinates).getBlockNumber();

            /**
             * 0 doesn't follow the rules and is not mentioned in available list so don't try to remove it
             */
            if (value != 0) {
                for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
                    if(entry.getValue().getColumn() == column || entry.getValue().getRow() == row || entry.getValue().getBlockNumber() == fieldBlock) {
                        entry.getValue().removeFromPossibleValues(value);
                    }
                }
            }

            return true;

        } catch (ValueNotAvailableException e) {
            Logger.getInstance().log(e.getMessage());
            System.out.println(e.getMessage());
            return false;
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

    public void ereaseAllFields() {
        for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
            try {
                entry.getValue().resetField();
            } catch (NoValueToEreaseException e) {
                System.out.println(e.getMessage());
            }
        }
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
