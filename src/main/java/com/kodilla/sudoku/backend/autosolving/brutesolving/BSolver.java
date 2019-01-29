package com.kodilla.sudoku.backend.autosolving.brutesolving;

import com.kodilla.sudoku.backend.assets.SudokuBoard;
import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.exceptions.UnableToSolveException;

public class BSolver implements AutoSolver {
    private static final int BLOCK_SIZE = 9;
    private static final int BLOCK_ROW_NUMBER = 3;
    private static final int ROWS_NUMBER = 9;
    private static final int COLUMNS_NUMBER = 9;
    private static final int EMPTY = 0;

    private static int[][] board;

    public void solveBoard(SudokuBoard boardToSolve) {
        board = boardToSolve.getStartingBoard();
        if( solve(board) ) {
            boardToSolve.setBoardFromMatrix(board);
        } else {
            throw new UnableToSolveException("Unable to solve this puzzle");
        }
    }

    private boolean solve(int[][] board) {
        for (int row = 0; row < ROWS_NUMBER; row++) {
            for (int column = 0; column < COLUMNS_NUMBER; column++) {
                if (board[row][column] == EMPTY) {
                    for (int i = 1; i < 10; i++) {
                        board[row][column] = i;
                        if (isAvailable(board, row, column) && solve(board)) {
                            return true;
                        }
                        board[row][column] = EMPTY;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAvailable(int[][] board, int row, int column) {
        return isAvailableInRow(board, row) &&
                isAvailableInColumn(board, column) &&
                isAvailableInBlock(board, row, column);
    }

    private boolean isAvailableInBlock(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[BLOCK_SIZE];
        int subsectionRowStart = (row / BLOCK_ROW_NUMBER) * BLOCK_ROW_NUMBER;
        int subsectionRowEnd = subsectionRowStart + BLOCK_ROW_NUMBER;

        int subsectionColumnStart = (column / BLOCK_ROW_NUMBER) * BLOCK_ROW_NUMBER;
        int subsectionColumnEnd = subsectionColumnStart + BLOCK_ROW_NUMBER;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c)) return false;
            }
        }
        return true;
    }

    private boolean isAvailableInColumn(int[][] board, int column) {
        boolean[] constraint = new boolean[COLUMNS_NUMBER];

        for(int i=0; i<COLUMNS_NUMBER; i++) {
            if( !checkConstraint(board, i, constraint, column) ){
                return false;
            }
        }
        return true;
    }

    private boolean isAvailableInRow(int[][] board, int row) {
        boolean[] constraint = new boolean[ROWS_NUMBER];

        for(int i=0; i<ROWS_NUMBER; i++) {
            if ( !checkConstraint(board, row, constraint, i) ) {
                return false;
            }
        }
        return true;
    }

    private boolean checkConstraint(int[][] board, int row, boolean[] constraint, int column) {
        if (board[row][column] != EMPTY) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
}
