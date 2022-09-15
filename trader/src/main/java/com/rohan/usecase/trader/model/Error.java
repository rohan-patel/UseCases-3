package com.rohan.usecase.trader.model;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class Error {
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public Error(HttpStatus httpStatus, String message, String details) {
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
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