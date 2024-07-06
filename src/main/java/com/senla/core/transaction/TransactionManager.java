package com.senla.core.transaction;

import com.senla.core.atm.ATMOperationType;
import com.senla.core.account.Account;

import java.math.BigDecimal;
import java.util.List;

public class TransactionManager {
    public static void recordTransaction(Account account, ATMOperationType type, BigDecimal amount) {
        Transaction transaction = new Transaction(type, amount);
        account.addTransaction(transaction);
    }

    public static void printTransactionHistory(Account account) {
        List<Transaction> transactions = account.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
            System.out.println();
        }
    }
}
