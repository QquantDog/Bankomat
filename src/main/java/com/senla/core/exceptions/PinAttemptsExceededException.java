package com.senla.core.exceptions;


public class PinAttemptsExceededException extends ATMException {
    private static final String basic_message = "Card is locked due to too many incorrect PIN attempts";
    public PinAttemptsExceededException() {
        super(basic_message);
    }
    public PinAttemptsExceededException(String message) {
        super(basic_message + " " + message);
    }
}