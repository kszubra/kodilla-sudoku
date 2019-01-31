package com.kodilla.sudoku.backend.generator;

import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.autosolving.brutesolving.BSolver;

import java.util.Arrays;
import java.util.Random;

public class BoardGenerator {
    private final Random RANDOM = new Random();
    private final int FIELDS_TO_FILL = 8;
    private int[][] board = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}

    };
    AutoSolver solver = new BSolver();

    private boolean makeSolvableBoard() {

        generateRandomBoard();

        while( !solver.isSolvable(board) ) {
            generateRandomBoard();
        }

        return true;
    }

    public int[][] getSolvableBoard(int fieldsToZerofy) {
        makeSolvableBoard();
        setSomeFieldsToZero(fieldsToZerofy);
        return board;
    }

    private void setSomeFieldsToZero(int fieldsToZerofy) {
        for(int i = 0; i < fieldsToZerofy; i++) {
            int row = randomRowOrColumn();
            int column = randomRowOrColumn();

            while(board[row][column] == 0) {
                row = randomRowOrColumn();
                column = randomRowOrColumn();
            }

            board[row][column] = 0;

        }
    }

    public void generateRandomBoard() {
        for(int i = 0; i<FIELDS_TO_FILL; i++ ) {

            int row = randomRowOrColumn();
            int column = randomRowOrColumn();

            while(board[row][column] != 0) {
                row = randomRowOrColumn();
                column = randomRowOrColumn();
            }

            setRandomAvailableValue(row, column);
        }
    }

    private void setRandomAvailableValue(int row, int column) {
        int value = randomValue();
        while( !isAvailable(row, column, value) ) {
            value = randomValue();
        }
        board[row][column] = value;
    }

    private boolean isAvailable(int row, int column, int value) {
        return !existsInRow(row, value) &&
                !existsInColumn(column, value) &&
                !existsInBlock(row, column, value);
    }

    private boolean existsInBlock(int row, int column, int value) {
        int r = row - (row % 3);
        int c = column - (column % 3);

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (board[i][j] == value)
                    return true;

        return false;
    }

    private boolean existsInColumn(int column, int value) {
        for (int i = 0; i < 9; i++)
            if (board[i][column] == value)
                return true;

        return false;
    }

    private boolean existsInRow(int row, int value) {
        for (int i = 0; i < 9; i++)
            if (board[row][i] == value)
                return true;

        return false;
    }

    public void displayBoard() {
        Arrays.stream(board)
                .forEach(e-> System.out.println(Arrays.toString(e)));
    }

    private int randomRowOrColumn() {
        return RANDOM.nextInt(9);
    }

    private int randomValue() {
        return RANDOM.nextInt(9) + 1;
    }


}
