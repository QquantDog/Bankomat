package com.senla.ui;


import com.senla.core.ATM;
import com.senla.core.exceptions.ATMException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;


public class Menu {
    private ATM atm;

    public Menu(ATM atm) {
        this.atm = atm;
    }

    public void displayMainMenu() {
        System.out.println("ATM Main Menu:");
        System.out.println("1. Login");
        System.out.println("2. Check Balance");
        System.out.println("3. Withdraw");
        System.out.println("4. Deposit");
        System.out.println("5. Transaction History");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public void authenticateUser(Scanner scanner) {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        try {
            atm.authenticate(cardNumber, pin);
            System.out.println("Authentication successful.");
        } catch (ATMException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performCheckBalance() {
        try {
            System.out.println("Current balance: " + atm.checkBalance().setScale(2, RoundingMode.DOWN));
        } catch (ATMException e) {
            System.out.println(e.getMessage());
        }
    }

    public void performWithdraw(Scanner scanner) {
        System.out.print("Enter amount to withdraw: ");
        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine());
            atm.withdraw(amount.setScale(2, RoundingMode.DOWN));
            System.out.println("Withdrawal successful.");
        } catch (ATMException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("Incorrect amount format");
        }
    }

    public void performDeposit(Scanner scanner) {
        System.out.print("Enter amount to deposit: ");
        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine());
            atm.deposit(amount.setScale(2, RoundingMode.DOWN));
            System.out.println("Deposit successful.");
        } catch (ATMException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e){
            System.out.println("Incorrect amount format");
        }
    }

    public void performPrintTransactionHistory() {
        try {
            atm.printTransactionHistory();
        } catch (ATMException e) {
            System.out.println(e.getMessage());
        }
    }
}

