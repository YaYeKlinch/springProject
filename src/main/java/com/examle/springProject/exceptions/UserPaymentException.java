package com.examle.springProject.exceptions;

public class UserPaymentException extends RuntimeException {

    public UserPaymentException() {
    }

    public UserPaymentException(String message) {
        super(message);
    }

    public UserPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserPaymentException(Throwable cause) {
        super(cause);
    }

    public UserPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
