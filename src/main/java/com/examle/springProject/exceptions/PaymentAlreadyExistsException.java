package com.examle.springProject.exceptions;

public class PaymentAlreadyExistsException extends RuntimeException {

    public PaymentAlreadyExistsException() {
    }

    public PaymentAlreadyExistsException(String message) {
        super(message);
    }

    public PaymentAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public PaymentAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}