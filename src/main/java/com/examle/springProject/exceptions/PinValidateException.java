package com.examle.springProject.exceptions;

public class PinValidateException  extends RuntimeException{

    public PinValidateException() {
    }

    public PinValidateException(String message) {
        super(message);
    }

    public PinValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PinValidateException(Throwable cause) {
        super(cause);
    }

    public PinValidateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
