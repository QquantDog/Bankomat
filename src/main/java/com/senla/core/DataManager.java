package com.senla.core;

import com.senla.core.atm.ATM;
import com.senla.core.account.Account;
import com.senla.core.account.AccountRepository;
import com.senla.core.exceptions.CardValidationException;
import com.senla.core.exceptions.InvalidFileFormatException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;


public class DataManager {
    private static final String FILE_PATH = System.getProperty("BuildFilePath") == null ? "accounts.txt"
            : System.getProperty("BuildFilePath");

    public static void loadAccounts(AccountRepository repo) throws InvalidFileFormatException {

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 5) {
                    String cardNumber = parts[0];
                    String pin = parts[1];
                    BigDecimal balance = new BigDecimal(parts[2]);
                    int pinAttempts = Integer.parseInt(parts[3]);

                    LocalDateTime lockDate;
                    if(pinAttempts == Account.MAX_PIN_ATTEMPTS) lockDate = LocalDateTime.parse(parts[4]);
                    else lockDate = null;

                    try{
                        Account.preValidate(cardNumber, pin, balance, pinAttempts, lockDate);
                        repo.saveAccount(new Account(cardNumber, pin, balance, pinAttempts, lockDate));
                    } catch (CardValidationException e){
                        throw new InvalidFileFormatException(e.getMessage());
                    }

                } else{
                    throw new InvalidFileFormatException("Invalid record in file");
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new InvalidFileFormatException(e.getMessage());
        }
    }


    public static void saveAccounts(AccountRepository repo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Account account : repo.getAllAccounts()) {
                bw.write(DataManager.writeHelper(account));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to accounts file: " + e.getMessage());
        }
    }
    private static String writeHelper(Account account){
        return account.getCardNumber()
                + " " + account.getPin()
                + " " + account.getBalance().setScale(2, RoundingMode.DOWN)
                + " " + account.getPinAttempts()
                + " " + (account.isLocked() ? account.getLockTime() : 0);
    }
    public static void debugAccountsInfo(AccountRepository repo){
        System.out.println("Accounts: ");
        for (var a : repo.getAllAccounts()) {
            System.out.println(a);
        }
        System.out.println();
    }
}

