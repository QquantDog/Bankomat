package com.senla;

import java.util.Scanner;

import com.senla.core.*;
import com.senla.core.atm.ATM;
import com.senla.core.atm.ATMState;
import com.senla.core.account.AccountRepository;
import com.senla.core.exceptions.InvalidFileFormatException;
import com.senla.ui.Menu;


public class Main {
    public static void main(String[] args) {
        AccountRepository repo = new AccountRepository();
        try {
            DataManager.loadAccounts(repo);
        } catch (InvalidFileFormatException e) {
            System.out.println("Invalid resource file - exiting program, " + e.getMessage());
            System.exit(42);
        }

        ATMState atmState = new ATMState();
        ATM atm = new ATM(repo, atmState);
        Menu menu = new Menu(atm);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            menu.displayMainMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    menu.authenticateUser(scanner);
                    break;
                case "2":
                    menu.performCheckBalance();
                    break;
                case "3":
                    menu.performWithdraw(scanner);
                    break;
                case "4":
                    menu.performDeposit(scanner);
                    break;
                case "5":
                    menu.performPrintTransactionHistory();
                    break;
                case "6":
                    DataManager.saveAccounts(repo);
                    System.exit(0);
                    break;
                case "d": {
                    DataManager.debugAccountsInfo(repo);
                    break;
                }
                case "9": {
                    System.out.println("Program exited without saving");
                    System.exit(0);
                }
                default:
                    System.out.println("Invalid choice. Please try again");
            }
        }
    }
}
