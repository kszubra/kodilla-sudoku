package com.kodilla.sudoku.backend.logger;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Logger {

    private static Logger loggerInstance = null;
    private List<Log> logs;

    private Logger() {
        logs = new ArrayList<>();
    }
    public static Logger getInstance() {
        if (loggerInstance == null) {
            synchronized(Logger.class) {
                if (loggerInstance == null) {
                    loggerInstance = new Logger();
                }
            }
        }
        return loggerInstance;
    }

    public void log(String action) {

        logs.add(new Log(LocalDateTime.now(), action));
        System.out.println(LocalDateTime.now() + " " + action);
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void saveLogsToTxt() {

        LocalDateTime saveTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String logPath = "/log_" + saveTime.format(formatter) + ".txt";

        FileWriter fileWriter = null;
        String dataToSave = logs.stream()
                .map(n->n.toString())
                .collect(Collectors.joining("\r\n"));

        try {
            fileWriter = new FileWriter(logPath);
            fileWriter.write(dataToSave);

        } catch (Exception e){
            System.out.println("Exception: " + e.toString());

        } finally {

            if(fileWriter != null){
                try{
                    fileWriter.close();
                } catch (Exception e){
                    System.out.println("Exception: " + e.toString());
                }
            }
        }
    }

    public void displayLogs() {
        logs.stream()
                .map(n->n.toString())
                .forEach(System.out::println);
    }

    public void clearLogs() {
        logs.clear();
    }

    public int getLogsNumber() {
        return logs.size();
    }

}
