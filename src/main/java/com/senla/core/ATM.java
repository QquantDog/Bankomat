package com.senla.core;

import com.senla.core.exceptions.ATMException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;


public class ATM {

    private AccountRepository repo;
    private Account currentAccount;
    private ATMState atmState;

    public static final int MAX_PIN_ATTEMPTS = 2;

    public ATM(AccountRepository repo, ATMState atmState) {
        this.repo = repo;
        this.atmState = atmState;
    }

    public void authenticate(String cardNumber, String pin) throws ATMException {
        if (!CardValidator.isValidCardNumber(cardNumber)) throw new ATMException("Invalid input card format");
        if (!CardValidator.isValidPin(pin)) throw new ATMException("Invalid input pin format");

        var account = repo.getAccountByCardName(cardNumber);
        if (account == null) throw new ATMException("Card number not found.");

        if (account.getCardNumber().equals(cardNumber)) {
            if (account.isLocked() && Duration.between(account.getLockTime(), LocalDateTime.now()).toSeconds() < Account.LOCK_DURATION_SECONDS) {
                throw new ATMException("Card is locked. Try again later.");
            } else if (account.isLocked()) {
                account.unlockAccount();
            }
            if (account.getPin().equals(pin)) {
                currentAccount = account;
                account.resetPinAttempts();
                System.out.println("Authentication successful for card: " + cardNumber);
                return;
            } else {
                account.incrementPinAttempts();
                if (account.getPinAttempts() >= MAX_PIN_ATTEMPTS) {
                    account.lockAccount();
                    throw new ATMException("Card is locked due to too many incorrect PIN attempts.");
                } else {
                    throw new ATMException("Incorrect PIN");
                }
            }
        }
    }

    public BigDecimal checkBalance() throws ATMException {
        ensureAuthenticated();
        return currentAccount.getBalance();
    }

    public void withdraw(BigDecimal amount) throws ATMException {
        ensureAuthenticated();
        if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) throw new ATMException("Amount should be positive");
        if (amount.compareTo(currentAccount.getBalance()) > 0) throw new ATMException("Not enough money :(");
//        положительеый amount и хватает денег на балансе
        try {
            atmState.withdrawAtmBalance(amount);
            currentAccount.setBalance(currentAccount.getBalance().subtract(amount));

            TransactionManager.recordTransaction(currentAccount, ATMOperationType.WITHDRAW, amount);
            System.out.println("Withdrawal of " + amount + " from card: " + currentAccount.getCardNumber());
        } catch (Exception e) {
            throw new ATMException("Error while withdrawing money from ATM, " + e.getMessage());
        }
    }


    public void deposit(BigDecimal amount) throws ATMException {
        ensureAuthenticated();
        if (amount.compareTo(BigDecimal.valueOf(0)) <= 0) throw new ATMException("Amount should be positive");
        try {
            atmState.depositAtmBalance(amount);
            currentAccount.setBalance(currentAccount.getBalance().add(amount));

            TransactionManager.recordTransaction(currentAccount, ATMOperationType.DEPOSIT, amount);
            System.out.println("Deposit of " + amount + " to card: " + currentAccount.getCardNumber());
        } catch (Exception e) {
            throw new ATMException("Error while deposit money to ATM, " + e.getMessage());
        }
    }

    public void printTransactionHistory() throws ATMException {
        ensureAuthenticated();
        TransactionManager.printTransactionHistory(currentAccount);
    }

    private void ensureAuthenticated() throws ATMException {
        if (currentAccount == null) {
            throw new ATMException("User not authenticated.");
        }
    }

}

