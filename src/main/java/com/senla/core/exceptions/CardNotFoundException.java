package com.senla.core.exceptions;


public class CardNotFoundException extends ATMException {
    private static final String basic_message = "Card number is missing";
    public CardNotFoundException() {
        super(basic_message);
    }
    public CardNotFoundException(String message) {
        super(basic_message + " " + message);
    }
}
