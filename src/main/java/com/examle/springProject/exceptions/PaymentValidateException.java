package com.examle.springProject.exceptions;

public class PaymentValidateException extends RuntimeException{

    public PaymentValidateException() {
    }

    public PaymentValidateException(String message) {
        super(message);
    }

    public PaymentValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentValidateException(Throwable cause) {
        super(cause);
    }

    public PaymentValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
