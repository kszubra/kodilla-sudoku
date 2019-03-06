package com.kodilla.sudoku.backend.autosolving.brutesolving;

import com.kodilla.sudoku.backend.assets.SudokuBoard;
import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.exceptions.UnableToSolveException;

public class BSolver implements AutoSolver {
    private final int ROWS_NUMBER = 9;
    private final int COLUMNS_NUMBER = 9;
    private final int EMPTY = 0;

    private static int[][] board;

    public BSolver() {
    }

    public BSolver(int[][] board) {
        this.board = board;
    }

    public void solveBoard(SudokuBoard boardToSolve) {
        this.board = boardToSolve.getStartingBoard();
        if( solve() ) {
            boardToSolve.setBoardFromMatrix(board);
        } else {
            throw new UnableToSolveException("Unable to solve this puzzle");
        }
    }

    public boolean isSolvable(int[][] board) {
        this.board = board;
        return solve();
    }

    public int[][] getBoard() {
        return board;
    }

    private boolean solve() {
        for (int row = 0; row < ROWS_NUMBER; row++) {
            for (int column = 0; column < COLUMNS_NUMBER; column++) {
                if (board[row][column] == EMPTY) {
                    for (int value = 1; value < 10; value++) {
                        if (isAvailable(row, column, value)) {
                            board[row][column] = value;
                            if ( solve() ) {
                                return true;
                            } else {
                                board[row][column] = EMPTY;
                            }
                        }
                    }
                    return false;
                }
            }
        }

        return true;
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
        for (int i = 0; i < COLUMNS_NUMBER; i++)
            if (board[i][column] == value)
                return true;

        return false;
    }

    private boolean existsInRow(int row, int value) {
        for (int i = 0; i < ROWS_NUMBER; i++)
            if (board[row][i] == value)
                return true;

        return false;
    }

}
