package com.kodilla.sudoku.backend.assets;

import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;
import com.kodilla.sudoku.backend.exceptions.ValueNotAvailableException;
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

    }


    public void setStartingBoard(int[][] initializeMatrix) {
        startingBoard = initializeMatrix;
        setBoardFromMatrix(initializeMatrix);

    }

    public void setBoardFromMatrix(int[][] matrix) {
        for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
            int row = entry.getKey().getRow();
            int column = entry.getKey().getColumn();
            int valueToSet = matrix[row][column];

            setFieldValue(row, column, valueToSet);
        }

    }

    public int[][] getBoardToSolve() {
        return startingBoard;
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

        int numberOfFieldsToPreFill;

        switch(difficultyLevel){
            case EASY:
                numberOfFieldsToPreFill = 50;
                break;
            case MEDIUM:
                numberOfFieldsToPreFill = 30;
                break;
            case HARD:
                numberOfFieldsToPreFill = 10;
                break;
            default:
                numberOfFieldsToPreFill = 30;
                break;
        }

        Set<BoardCoordinates> randomCoordinatesToFill = new HashSet<>();

        while(randomCoordinatesToFill.size() < numberOfFieldsToPreFill) {
            int randomRow = randomGenerator.nextInt(9);
            int randomColumn = randomGenerator.nextInt(9);
            randomCoordinatesToFill.add(new BoardCoordinates(randomRow, randomColumn));
        }

        for(BoardCoordinates coordinates : randomCoordinatesToFill) {
            setRandomValue(coordinates);
        }

        for(int row =0; row<ROWS_NUMBER; row++) {
            for(int column = 0; column<COLUMNS_NUMBER; column++) {
                startingBoard[row][column] = fields.get(new BoardCoordinates(row, column)).getValue();
            }
        }

    }

    private void setRandomValue(BoardCoordinates coordinates) {
        int amountOfAvaiableValues = fields.get(coordinates).getPossibleValues().size();
        int indexOfRandomAvaiableValue = randomGenerator.nextInt(amountOfAvaiableValues);
        int randomAvaiableValue = fields.get(coordinates).getPossibleValues().get(indexOfRandomAvaiableValue);

        setFieldValue(coordinates.getRow(), coordinates.getColumn(), randomAvaiableValue);
    }


    public void ereaseFieldValue(int row, int column) {

        BoardCoordinates cellCoordinates = new BoardCoordinates(row, column);
        int fieldBlock =  fields.get(cellCoordinates).getBlockNumber();
        int value = fields.get(cellCoordinates).resetField();

        for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
            if(entry.getValue().getColumn() == column || entry.getValue().getRow() == row || entry.getValue().getBlockNumber() == fieldBlock) {
                entry.getValue().addToPossibleValues(value);
            }
        }


    }

    public void setFieldValue(int row, int column, int value) {
        try {
            BoardCoordinates cellCoordinates = new BoardCoordinates(row, column);
            fields.get(cellCoordinates).setValue(value);
            int fieldBlock =  fields.get(cellCoordinates).getBlockNumber();

            for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
                if(entry.getValue().getColumn() == column || entry.getValue().getRow() == row || entry.getValue().getBlockNumber() == fieldBlock) {
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

    public void ereaseAllFields() {
        for(Map.Entry<BoardCoordinates, SudokuField> entry : fields.entrySet()) {
            entry.getValue().resetField();
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
