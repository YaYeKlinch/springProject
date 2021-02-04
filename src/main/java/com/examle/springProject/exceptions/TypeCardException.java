package com.examle.springProject.exceptions;

public class TypeCardException extends RuntimeException {

    public TypeCardException() {
    }

    public TypeCardException(String message) {
        super(message);
    }

    public TypeCardException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeCardException(Throwable cause) {
        super(cause);
    }

    public TypeCardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}