package com.senla.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transaction {
    private final LocalDateTime date;
    private final ATMOperationType type;
    private final BigDecimal amount;

    public Transaction(ATMOperationType type, BigDecimal amount) {
        this.date = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
    }


    @Override
    public String toString() {
        return date + " " + type.toString() + " " + amount;
    }
}
