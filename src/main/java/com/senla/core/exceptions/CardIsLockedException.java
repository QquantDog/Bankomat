package com.senla.core.exceptions;

public class CardIsLockedException extends ATMException {
    private static final String basic_message = "Card is locked";
    public CardIsLockedException() {
        super(basic_message);
    }
    public CardIsLockedException(String message) {
        super(basic_message + " " + message);
    }
}
