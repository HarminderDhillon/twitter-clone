package com.dhillon.twitterclone.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an API error response.
 */
public class ApiError {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> details;
    
    /**
     * Default constructor.
     */
    public ApiError() {
        this.timestamp = LocalDateTime.now();
        this.details = new ArrayList<>();
    }
    
    /**
     * Constructor with basic error information.
     *
     * @param status HTTP status code
     * @param error error type
     * @param message error message
     * @param path request path
     */
    public ApiError(int status, String error, String message, String path) {
        this();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    
    // Getters and Setters
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public List<String> getDetails() {
        return details;
    }
    
    public void setDetails(List<String> details) {
        this.details = details;
    }
    
    /**
     * Add a detail message to the error.
     *
     * @param detail the detail message
     */
    public void addDetail(String detail) {
        this.details.add(detail);
    }
} 