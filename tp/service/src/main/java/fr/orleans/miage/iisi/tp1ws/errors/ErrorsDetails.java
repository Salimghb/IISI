package fr.orleans.miage.iisi.tp1ws.errors;

import java.time.LocalDateTime;

public class ErrorsDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorsDetails(String message, String details) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }

    public ErrorsDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
