package com.senla.core.exceptions;


public class IncorrectPinException extends ATMException {
    private static final String basic_message = "Incorrect Pin";
    public IncorrectPinException() {
        super(basic_message);
    }
    public IncorrectPinException(String message) {
        super(basic_message + " " + message);
    }
}