package com.syllabus.api.aspectLogger.persistLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static Logger _instance;
    private List<String> logs;
    private PrintWriter fileWriter;
    private String logFileName;

    private Logger() {
        logs = new ArrayList<>();
        initializeLogFile();
    }

    // Singleton instance retrieval
    public static Logger getInstance() {
        if (_instance == null) {
            synchronized (Logger.class) {
                if (_instance == null) {
                    _instance = new Logger();
                }
            }
        }
        return _instance;
    }

    // Log a message
    public void writeLog(String message) {
        rotateLogFileIfNeeded();
        String log = String.format("[%s] %s", LocalDateTime.now(), message);
        System.out.println(log);
        fileWriter.println(log);
        fileWriter.flush();
        logs.add(log);
    }

    // Display all logged messages
    public void displayLogs() {
        for (String log : logs) {
            System.out.println(log);
        }
    }

    // Initialize the log file and set the file writer
    private void initializeLogFile() {
        createLogFileName();
        try {
            fileWriter = new PrintWriter(new FileWriter("src/main/java/com/syllabus/api/aspectLogger/persistLog/logs/"+logFileName, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a new log file name based on the current date
    private void createLogFileName() {
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        logFileName = "server_" + formattedDate + ".log";
    }

    // Rotate the log file if a new day has started
    private void rotateLogFileIfNeeded() {
        LocalDateTime now = LocalDateTime.now();
        String currentDay = now.format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        if (!currentDay.equals(logFileName.substring(7, 17))) {
            closeLogFile();
            createLogFileName();
            initializeLogFile();
            removeOldLogFiles();
        }
    }

    // Close the current log file writer
    private void closeLogFile() {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }

    // Remove log files older than 5 days
    private void removeOldLogFiles() {
        System.out.println("removeOldLogsFiles()");
        LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
        File logsDirectory = new File("src/main/java/com/syllabus/api/aspectLogger/persistLog/logs/");

        File[] logFiles = logsDirectory.listFiles((dir, name) -> name.matches("server_\\d{2}_\\d{2}_\\d{4}.log"));


        if (logFiles != null) {
            for (File logFile : logFiles) {
                String fileName = logFile.getName();
                String datePart = fileName.substring(7, 17);
                LocalDate logFileDate = LocalDate.parse(datePart, DateTimeFormatter.ofPattern("dd_MM_yyyy"));

                if (logFileDate.isBefore(fiveDaysAgo)) {
                    if (logFile.delete()) {
                        System.out.println("Deleted old log file: " + logFile.getName());
                    } else {
                        System.err.println("Failed to delete old log file: " + logFile.getName());
                    }
                }
            }
        }
    }
}

