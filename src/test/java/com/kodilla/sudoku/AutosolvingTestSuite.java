package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.SudokuBoard;
import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.autosolving.brutesolving.BSolver;
import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AutosolvingTestSuite {
    private SudokuBoard testBoard;
    private static int testNumber = 0;
    private AutoSolver solver = new BSolver();

    @Before
    public void beforeTest() {
        testNumber++;
        System.out.println("---------- STARTING TEST NR. " + testNumber + " ----------");
        System.out.print("Testing: ");
    }

    @After
    public void afterTest() {
        System.out.println("---------- TEST NR. " + testNumber + " FINISHED ----------\r\n");
    }

    @Test
    public void testSolvingBoard() {
        System.out.println("solving and displaying result");

        //given
        testBoard = new SudokuBoard();
        int[][] startingMatrix = {
                {3, 0, 6, 5, 0, 8, 4, 0, 0},
                {5, 2, 0, 0, 0, 0, 0, 0, 0},
                {0, 8, 7, 0, 0, 0, 0, 3, 1},
                {0, 0, 3, 0, 1, 0, 0, 8, 0},
                {9, 0, 0, 8, 6, 3, 0, 0, 5},
                {0, 5, 0, 0, 9, 0, 6, 0, 0},
                {1, 3, 0, 0, 0, 0, 2, 5, 0},
                {0, 0, 0, 0, 0, 0, 0, 7, 4},
                {0, 0, 5, 2, 0, 6, 3, 0, 0}
        };
        testBoard.setStartingBoard(startingMatrix);


        //when
        solver.solveBoard(testBoard);

        //then
        Assert.assertTrue(testBoard.isComplete());
        testBoard.displayBoard();

    }

    @Test
    public void testSolvingRandomBoard() {
        System.out.println(" making random board and solving it on HARD");

            ///given
            testBoard = new SudokuBoard();
            testBoard.preFill(DifficultyLevel.HARD);

            //when
            solver.solveBoard(testBoard);

            //then
            Assert.assertTrue(testBoard.isComplete());
    }

}
