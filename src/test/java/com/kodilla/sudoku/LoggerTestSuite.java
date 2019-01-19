package com.kodilla.sudoku;

import com.kodilla.sudoku.backend.logger.Logger;
import org.junit.Assert;
import org.junit.Test;

public class LoggerTestSuite {
    private Logger logger = Logger.getInstance();

    @Test
    public void testDisplayingLogs() {
        //Given
        logger.log("First test log");
        logger.log("Second test log");

        //Then
        logger.displayLogs();
        Assert.assertEquals(2, logger.getLogsNumber());
    }

    @Test
    public void testClearingLogs() {
        //Given
        logger.log("First test log");
        logger.log("Second test log");

        //When
        logger.clearLogs();

        //Then
        Assert.assertEquals(0, logger.getLogsNumber());

    }


}
