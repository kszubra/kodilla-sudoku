package com.kodilla.sudoku.backend.autosolving.backtracking;

import com.kodilla.sudoku.backend.assets.SudokuBoard;
import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.exceptions.UnableToSolveException;

import java.util.stream.IntStream;

public class BSolver implements AutoSolver {
    private static final int BOARD_SIZE = 9;
    private static final int BLOCK_SIZE = 3;

    private static final int EMPTY = 0;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 9;

    private static int[][] board;

    public void solveBoard(SudokuBoard boardToSolve) {
        board = boardToSolve.getStartingBoard();
        if( solve(board) ) {
            boardToSolve.setBoardFromMatrix(board);
        } else {
            throw new UnableToSolveException("Unable to solve this puzzle");
        }
;
    }

    private boolean solve(int[][] board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] == EMPTY) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[row][column] = k;
                        if (isValid(board, row, column) && solve(board)) {
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

    private boolean isValid(int[][] board, int row, int column) {
        return rowConstraint(board, row) &&
                columnConstraint(board, column) &&
                subsectionConstraint(board, row, column);
    }

    private boolean subsectionConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        int subsectionRowStart = (row / BLOCK_SIZE) * BLOCK_SIZE;
        int subsectionRowEnd = subsectionRowStart + BLOCK_SIZE;

        int subsectionColumnStart = (column / BLOCK_SIZE) * BLOCK_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + BLOCK_SIZE;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c)) return false;
            }
        }
        return true;
    }

    private boolean columnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(0, BOARD_SIZE)
                .allMatch(row -> checkConstraint(board, row, constraint, column));
    }

    private boolean rowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(0, BOARD_SIZE)
                .allMatch(column -> checkConstraint(board, row, constraint, column));
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
