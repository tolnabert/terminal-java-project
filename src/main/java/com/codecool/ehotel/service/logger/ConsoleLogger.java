package com.codecool.ehotel.service.logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class ConsoleLogger implements Logger {

    private String logLevel;

    public ConsoleLogger() {
        logLevel = System.getProperty("com.codecool.ehotel.loglevel", "INFO");
    }

    @Override
    public void logInfo(String message) {
        logMessage(message, "INFO");
    }

    @Override
    public void logError(String message) {
        logMessage(message, "ERROR");
    }

    @Override
    public void logDebug(String message) {
        if (Objects.equals(logLevel, "DEBUG")) {
            logMessage(message, "DEBUG");
        }
    }

    private void logMessage(String message, String type) {
        String entry = "[" + LocalDateTime.now() + "] " + type + ": " + message;
        System.out.println(entry);
    }
}
