package com.senla.core.account;

import com.senla.core.util.CardValidator;
import com.senla.core.transaction.Transaction;
import com.senla.core.exceptions.CardValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Account {
    private String cardNumber;
    private String pin;
    private BigDecimal balance;

    private int pinAttempts;
    private LocalDateTime lockTime;
    private List<Transaction> transactions;

    public static final int MAX_PIN_ATTEMPTS = 3;
    public static final int LOCK_DURATION_SECONDS = 3600 * 24;

    public Account(String cardNumber, String pin, BigDecimal balance, int pinAttempts, LocalDateTime lockTime) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.pinAttempts = pinAttempts;
        this.lockTime = lockTime;
        this.transactions = new ArrayList<>();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getPinAttempts() {
        return pinAttempts;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setBalance(BigDecimal balance) throws IllegalArgumentException {
        if (balance.compareTo(BigDecimal.valueOf(0)) < 0)
            throw new IllegalArgumentException("Balance couldn't be negative");
        this.balance = balance;
    }

    public void incrementPinAttempts() {
        this.pinAttempts++;
    }

    public void resetPinAttempts() {
        this.pinAttempts = 0;
    }

    public void lockAccount() {
        this.lockTime = LocalDateTime.now();
    }

    public void unlockAccount() {
        this.lockTime = null;
        resetPinAttempts();
    }

    public boolean isLocked() {
        return lockTime != null;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "CN: " + cardNumber
                + " pin: " + pin
                + " balance: " + balance
                + " attempts: " + pinAttempts
                + " lock date: " + (isLocked() ? lockTime.toString() : "-");
    }
}
