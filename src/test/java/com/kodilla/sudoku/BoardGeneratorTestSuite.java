package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.autosolving.AutoSolver;
import com.kodilla.sudoku.backend.autosolving.brutesolving.BSolver;
import com.kodilla.sudoku.backend.generator.BoardGenerator;
import org.junit.*;

public class BoardGeneratorTestSuite {
    private static int testNumber = 0;

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
    public void testGeneratingBoards() {
        System.out.println("generating boards with given amount of fields - without solve check");

        //given
        BoardGenerator generator = new BoardGenerator();

        //when
        generator.generateRandomBoard();

        //then
        generator.displayBoard();
    }

    @Test
    public void testGeneratingSolvableBoards() {
        System.out.println("generating boards with given amount of fields - checking solvability");

        //given
        int[][] testBoard;
        AutoSolver solver = new BSolver();

        for(int i = 0; i<100; i++) {
            BoardGenerator generator = new BoardGenerator();
            testBoard = generator.getSolvableBoard(20);

            Assert.assertTrue( solver.isSolvable(testBoard) );
        }

    }
}
