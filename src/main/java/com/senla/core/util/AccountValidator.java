package com.senla.core.util;

import com.senla.core.account.Account;
import com.senla.core.exceptions.CardValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class AccountValidator {
    public static void preValidate(String cardNumber, String pin, BigDecimal balance, int pinAttempts, LocalDateTime lockTime) throws CardValidationException {
        if (!CardValidator.isValidCardNumber(cardNumber)) throw new CardValidationException("Invalid card format");
        if (!CardValidator.isValidPin(pin)) throw new CardValidationException("Invalid pin format");
        if (balance.compareTo(BigDecimal.valueOf(0)) < 0) throw new CardValidationException("Invalid amount of money");
        if (pinAttempts < 0 || pinAttempts > Account.MAX_PIN_ATTEMPTS)
            throw new CardValidationException("Invalid pin attempts");
        if (pinAttempts == Account.MAX_PIN_ATTEMPTS && lockTime == null)
            throw new CardValidationException("Invalid lock time");
        if (pinAttempts != Account.MAX_PIN_ATTEMPTS && lockTime != null)
            throw new CardValidationException("Invalid lock time");
    }
}