package com.senla.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AccountRepository {
    private final List<Account> accounts = new ArrayList<Account>();

    public List<Account> getAllAccounts(){
        return this.accounts;
    }

    public Account getAccountByCardName(String cardNumber){
        Optional<Account> result = accounts.stream().filter(acc -> Objects.equals(acc.getCardNumber(), cardNumber)).findFirst();
        return result.orElse(null);
    }
    public void saveAccount(Account account){
        this.accounts.add(account);
    }
}
