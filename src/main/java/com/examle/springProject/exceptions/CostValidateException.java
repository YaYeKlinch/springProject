package com.examle.springProject.exceptions;

public class CostValidateException extends RuntimeException {

    public CostValidateException() {
    }

    public CostValidateException(String message) {
        super(message);
    }

    public CostValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CostValidateException(Throwable cause) {
        super(cause);
    }

    public CostValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}