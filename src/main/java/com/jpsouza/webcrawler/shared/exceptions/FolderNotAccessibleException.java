package com.jpsouza.webcrawler.shared.exceptions;

public class FolderNotAccessibleException extends ApplicationException {
    public FolderNotAccessibleException(String message) {
        super(message);
    }

    public FolderNotAccessibleException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public FolderNotAccessibleException(Exception exception) {
        super(exception.getMessage(), exception.getCause());
    }
}
