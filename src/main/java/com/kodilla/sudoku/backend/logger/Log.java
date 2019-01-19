package com.kodilla.sudoku.backend.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private LocalDateTime logTime;
    private String actionToLog;

    public Log(LocalDateTime logTime, String actionToLog) {
        this.logTime = logTime;
        this.actionToLog = actionToLog;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  "[" + logTime.format(formatter) + "]" + " : " + actionToLog;
    }
}
