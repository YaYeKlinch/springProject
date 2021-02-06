package com.examle.springProject.controller.utility;

import com.examle.springProject.exceptions.PaymentValidateException;

public class PaymentValidator {
    private static final int LENGTH_OF_PROPERTY=8;
    public static boolean isPropertyCorrect(Long property){
        return String.valueOf(property).length() == LENGTH_OF_PROPERTY && property > 0;
    }
    public static void validatePayment( Long property) throws PaymentValidateException {
        if (!isPropertyCorrect(property)) {
            throw new PaymentValidateException(
                    "Property is incorrect");
        }
    }
}
