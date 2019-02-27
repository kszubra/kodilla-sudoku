package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.*;
import com.kodilla.sudoku.backend.enumerics.DifficultyLevel;
import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;
import com.kodilla.sudoku.backend.exceptions.ValueNotAvailableException;
import org.junit.*;

import java.util.List;

public class AssetsTestSuite {
    private SudokuBoard testBoard;
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
    public void testFieldsNumberInMap() {
        System.out.println("number of fields in map");
        testBoard = new SudokuBoard();

        //when
        int realFieldsNumber = testBoard.getFieldsNumber();

        //then
        System.out.println("Map contains " + realFieldsNumber + " fields");
        Assert.assertEquals(81, realFieldsNumber);
    }

    @Test
    public void testRowsNumber() {
        System.out.println("number of rows");

        //given
        testBoard = new SudokuBoard();
        List<SudokuRow> testRows = testBoard.getBoardRows();

        //when
        int expectedNumber = 9;

        //then
        System.out.println("Board contains " + testRows.size() + " rows");
        Assert.assertEquals(expectedNumber, testRows.size());
    }

    @Test
    public void testColumnsNumber() {
        System.out.println("number of columns");

        //given
        testBoard = new SudokuBoard();
        List<SudokuColumn> testColumns = testBoard.getBoardColumns();

        //when
        int expectedNumber = 9;

        //then
        System.out.println("Board contains " + testColumns.size() + " rows");
        Assert.assertEquals(expectedNumber, testColumns.size());
    }

    @Test
    public void testAccidentalAddingTooManyItems() {
        System.out.println("trying to add 10th field to the row");
        testBoard = new SudokuBoard();

        try {
            testBoard.getBoardRows().get(3).addField(new SudokuField(2,3));
        } catch (TooManyItemsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDisplayingBoard() {
        System.out.println("displaying board");
        testBoard = new SudokuBoard();

        testBoard.displayBoard();
    }

    @Test
    public void testEqualityOfCoordinates() {
        System.out.println("comparing coordinates");

        //given
        testBoard = new SudokuBoard();
        BoardCoordinates coordinateOne = new BoardCoordinates(2,4);
        BoardCoordinates coordinateTwo = new BoardCoordinates(2, 4);

        //then
        Assert.assertEquals(coordinateOne, coordinateTwo);
    }

    /**
     * Two tests below give NullPointer when removing hashCode() from Coordinates. Check it!
     * */

    @Test
    public void testGetField() {
        System.out.println("getting a field from the board");

        //Given
        testBoard = new SudokuBoard();
        SudokuField testField = testBoard.getSudokuField(2,2);

        //then
        Assert.assertNotNull(testField);
    }

    @Test
    public void testSettingFieldValue() {
        System.out.println("setting field value");

        //Given
        testBoard = new SudokuBoard();
        testBoard.setFieldValue(2, 5, 6);

        //then
        Assert.assertEquals(6, testBoard.getSudokuFieldValue(2,5));
    }

    @Test
    public void testIfObjectsAreSame() {
        System.out.println("whether references refer to same object");

        //given
        testBoard = new SudokuBoard();
        testBoard.setFieldValue(5,3, 7);

        //when
        int valueByMap = testBoard.getSudokuFieldValue(5,3);
        int valueByRow = testBoard.getBoardColumns().get(3).getFieldsInColumn().get(5).getValue(); // TERRIBLE -> DO STH WITH IT

        //then
        Assert.assertEquals(valueByMap, valueByRow);
    }

    @Test
    public void testIfObjectsAreSamePartTwo() {
        System.out.println("whether references refer to same object");

        //given
        testBoard = new SudokuBoard();
        try{
            testBoard.getBoardColumns().get(4).getFieldsInColumn().get(2).setValue(9);
        } catch (ValueNotAvailableException e) {
            System.out.println(e.getMessage());
        }

        //when
        int valueByMap = testBoard.getSudokuFieldValue(2, 4);
        int valueByRow = testBoard.getBoardColumns().get(4).getFieldsInColumn().get(2).getValue();

        //then
        Assert.assertEquals(valueByMap, valueByRow);
    }

    @Test
    public void testAddingSameValueInARowAndColumn() {
        System.out.println("possibility to add same value in one row and column");

        //given
        testBoard = new SudokuBoard();
        int value = 5;
        int row = 2;
        int column = 3;
        testBoard.setFieldValue(row, column, value);

        //when
        testBoard.setFieldValue(row, column+2, value);
        testBoard.setFieldValue(row+2, column, value);

        //then
        System.out.println(testBoard.getSudokuField(row, column).getValue());
        System.out.println(testBoard.getSudokuField(row, column+2).getValue());
        System.out.println(testBoard.getSudokuField(row+2, column).getValue());

        Assert.assertNotEquals(testBoard.getSudokuField(row, column).getValue(), testBoard.getSudokuField(row, column+2).getValue());
        Assert.assertNotEquals(testBoard.getSudokuField(row, column).getValue(), testBoard.getSudokuField(row+2, column).getValue());
    }

    @Test
    public void testCheckingBoardsEquality() {
        System.out.println("whether two boards are equal");

        //given
        SudokuBoard testBoardOne = new SudokuBoard();
        SudokuBoard testBoardTwo = new SudokuBoard();

        //when
        testBoardOne.setFieldValue(2,3,4);
        testBoardTwo.setFieldValue(2,3,4);

        //then
        Assert.assertTrue(testBoardOne.equals(testBoardTwo));
    }

    @Test
    public void testEreasingValue() {
        System.out.println("ereasing given field");

        //given
        testBoard = new SudokuBoard();
        SudokuBoard boardBeforeChanges = new SudokuBoard();

        testBoard.setFieldValue(3,5, 8);
        testBoard.setFieldValue(6, 7, 3);

        //when
        testBoard.ereaseFieldValue(3,5);
        testBoard.ereaseFieldValue(6,7);

        //then
        Assert.assertTrue(testBoard.equals(boardBeforeChanges));
    }

    @Test
    public void testEreasingAllFields() {
        System.out.println("ereasing all fields of the board");

        //given
        testBoard = new SudokuBoard();
        SudokuBoard boardBeforeChanges = new SudokuBoard();

        testBoard.setFieldValue(3,5, 8);
        testBoard.setFieldValue(6, 7, 3);

        //when
        testBoard.ereaseAllFields();

        //then
        Assert.assertTrue(testBoard.equals(boardBeforeChanges));

    }

    @Test
    public void testProperFieldsInBlocks() {
        System.out.println("whether fields are assigned to proper blocks");

        //given
        testBoard = new SudokuBoard();

        //then
        for(int number = 0; number<9; number++){
            for(SudokuField field : testBoard.getBoardBlocks().get(number).getFieldsInBlock()) {
                Assert.assertTrue(field.getBlockNumber() == number);
            }
        }
    }

    @Test
    public void testFillingWithRandomValue() {
        System.out.println("filling given coordinates with random available value");

        //given
        testBoard = new SudokuBoard();

        //when
        testBoard.preFill(DifficultyLevel.MEDIUM);

        //then
        testBoard.displayBoard();
    }

    @Test
    public void testSettingBoardFromMatrix() {
        System.out.println("setting game board from provided matrix");

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
        int[][] boardMatrix = testBoard.getStartingBoard();

        //then
        testBoard.displayBoard();
        for(int row = 0; row<9; row++) {
            for(int column = 0; column<9; column ++) {
                int matrix = boardMatrix[row][column];
                int board = testBoard.getSudokuFieldValue(row, column);
                System.out.println("Row: " + row + " Column: " + column + " Matrix=" + matrix + " Board=" + board);
                Assert.assertEquals(matrix, board);
            }
        }
    }


}
