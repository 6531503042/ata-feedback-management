package dev.bengi.feedbackservice.domain.exception;

public class DuplicateProjectNameException extends RuntimeException {
    public DuplicateProjectNameException(String message) {
        super(message);
    }
} 