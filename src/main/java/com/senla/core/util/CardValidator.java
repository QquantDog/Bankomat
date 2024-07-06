package com.senla.core.util;


public class CardValidator {
    private static final String CARD_REGEX = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$";
    private static final String PIN_REGEX = "^\\d{4}$";

    public static boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches(CARD_REGEX);
    }

    public static boolean isValidPin(String pin) {
        return pin.matches(PIN_REGEX);
    }
}

