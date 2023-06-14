package com.jpsouza.webcrawler.shared.exceptions;

public class ApplicationException extends Exception {
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ApplicationException(Exception exception) {
        super(exception.getMessage(), exception.getCause());
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
