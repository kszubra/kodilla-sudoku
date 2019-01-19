package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.assets.*;
import com.kodilla.sudoku.backend.exceptions.TooManyItemsException;
import com.kodilla.sudoku.backend.exceptions.ValueNotAvailableException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AssetsTestSuite {
    private static SudokuBoard testBoard = new SudokuBoard();
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

        try {
            testBoard.getBoardRows().get(3).addField(new SudokuField(2,3));
        } catch (TooManyItemsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testDisplayingBoard() {
        System.out.println("displaying board");

        testBoard.displayBoard();
    }

    @Test
    public void testEqualityOfCoordinates() {
        System.out.println("comparing coordinates");

        //given
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
        SudokuField testField = testBoard.getSudokuField(2,2);

        //then
        Assert.assertNotNull(testField);
    }

    @Test
    public void testSettingFieldValue() {
        System.out.println("setting field value");

        //Given
        testBoard.setFieldValue(2, 5, 6);

        //then
        Assert.assertEquals(6, testBoard.getSudokuFieldValue(2,5));
    }

    @Test
    public void testIfObjectsAreSame() {
        System.out.println("whether references refer to same object");

        //given
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
}
