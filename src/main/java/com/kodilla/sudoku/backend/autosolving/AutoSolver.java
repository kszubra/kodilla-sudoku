package com.kodilla.sudoku.backend.autosolving;

import com.kodilla.sudoku.backend.assets.SudokuBoard;

public interface AutoSolver {

    void solveBoard(SudokuBoard boardToSolve);
    boolean isSolvable(int[][] board);


}
