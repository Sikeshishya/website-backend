package com.filcon.electronics.exception;

public class ContactSubmissionException extends RuntimeException {
    public ContactSubmissionException(String message) {
        super(message);
    }

    public ContactSubmissionException(String message, Throwable cause) {
        super(message, cause);
    }
}