package com.senla.ui;


import com.senla.core.atm.ATM;
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
        System.out.println("6. Exit Saving");
        System.out.println("9. Exit Discarding Changes");
        System.out.println("d. For Printing Internal Account Info");
        System.out.print("Enter your choice: ");
    }

    public void authenticateUser(Scanner scanner) {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        try {
            atm.authenticate(cardNumber, pin);
            System.out.println("Authentication successful\n");
        } catch (ATMException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    public void performCheckBalance() {
        try {
            atm.ensureAuthenticated();
            System.out.println("Current balance: " + atm.checkBalance().setScale(2, RoundingMode.DOWN) + "\n");
        } catch (ATMException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    public void performWithdraw(Scanner scanner) {
        try {
            atm.ensureAuthenticated();
            System.out.print("Enter amount to withdraw: ");

            BigDecimal amount = new BigDecimal(scanner.nextLine());
            atm.withdraw(amount.setScale(2, RoundingMode.DOWN));
            System.out.println("Withdrawal successful\n");
        } catch (ATMException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (NumberFormatException e){
            System.out.println("Incorrect amount format\n");
        }
    }

    public void performDeposit(Scanner scanner) {
        try {
            atm.ensureAuthenticated();
            System.out.print("Enter amount to deposit: ");

            BigDecimal amount = new BigDecimal(scanner.nextLine());
            atm.deposit(amount.setScale(2, RoundingMode.DOWN));
            System.out.println("Deposit successful\n");
        } catch (ATMException e) {
            System.out.println(e.getMessage() + "\n");
        } catch (NumberFormatException e){
            System.out.println("Incorrect amount format\n");
        }
    }

    public void performPrintTransactionHistory() {
        try {
            atm.ensureAuthenticated();
            atm.printTransactionHistory();
        } catch (ATMException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }
}

