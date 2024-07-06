package com.senla.core;

import com.senla.core.exceptions.ATMStateException;

import java.math.BigDecimal;


public class ATMState {

    private BigDecimal MAX_DEPOSIT_AMOUNT;
    private BigDecimal atmBalance;

    public ATMState() {
        atmBalance = new BigDecimal("100000.00");
        MAX_DEPOSIT_AMOUNT = new BigDecimal("1000000.00");
    }

    public void depositAtmBalance(BigDecimal amount) throws ATMStateException {
        if (amount.compareTo(MAX_DEPOSIT_AMOUNT) >= 0)
            throw new ATMStateException(ATMOperationType.DEPOSIT, "Upper deposit limit exceeded");
        atmBalance = atmBalance.add(amount);
    }

    public void withdrawAtmBalance(BigDecimal amount) throws ATMStateException {
        if (amount.compareTo(atmBalance) > 0)
            throw new ATMStateException(ATMOperationType.WITHDRAW, "Insufficient funds in ATM: " + atmBalance.toString());
        atmBalance = atmBalance.subtract(amount);
    }

}
