package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.SudokuBoard;
import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.autosolving.brutesolving.BSolver;
import org.junit.*;

@Ignore
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
                {8, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 3, 6, 0, 0, 0, 0, 0},
                {0, 7, 0, 0, 9, 0, 2, 0, 0},
                {0, 5, 0, 0, 0, 7, 0, 0, 0},
                {0, 0, 0, 0, 4, 5, 7, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 3, 0},
                {0, 0, 1, 0, 0, 0, 0, 6, 8},
                {0, 0, 8, 5, 0, 0, 0, 1, 0},
                {0, 9, 0, 0, 0, 0, 4, 0, 0}
        };
        testBoard.setStartingBoard(startingMatrix);


        //when
        solver.solveBoard(testBoard);

        //then
        Assert.assertTrue(testBoard.isComplete());
        testBoard.displayBoard();

    }


}
