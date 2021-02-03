package com.examle.springProject.controller.utility;

import com.examle.springProject.exceptions.PinValidateException;

public class PinValidator {
    private static final int LENGTH_OF_PIN=4;
    private static boolean isPinCorrect(Integer pin){

        return String.valueOf(pin).length() == LENGTH_OF_PIN && pin > 0;
    }
    private static boolean isPinConfirmed(Integer pin , Integer confirmPin){
        return pin.equals(confirmPin);
    }
    public static void validatePin(Integer pin , Integer confirmPin) throws PinValidateException{
        if (!PinValidator.isPinCorrect(pin)) {
            throw new PinValidateException(
                    " Pin is incorrect ");
        }
        if (!PinValidator.isPinConfirmed(pin , confirmPin)) {
            throw new PinValidateException(
                    " Confirm pin isn't equals to pin  ");
        }
    }
}
