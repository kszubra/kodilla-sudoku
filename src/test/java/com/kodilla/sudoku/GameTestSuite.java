package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.Game;
import com.kodilla.sudoku.backend.assets.InitialGameData;
import com.kodilla.sudoku.backend.assets.SudokuBoard;
import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import org.junit.*;

@Ignore
public class GameTestSuite {
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
    public void testNewGame() {
        System.out.println("new game and changing board");

        //given
        Game testGame = new Game(new InitialGameData("X", DifficultyLevel.HARD));
        SudokuBoard independentBoard = new SudokuBoard();

        //when
        independentBoard.setFieldValue(2,3, 5);
        testGame.getGameBoard().setFieldValue(2,3, 5);


        //then
        Assert.assertEquals(5, testGame.getGameBoard().getSudokuFieldValue(2,3));
        Assert.assertEquals(5, independentBoard.getSudokuFieldValue(2,3));
    }

}
