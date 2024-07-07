package com.senla.core.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {

    private AccountRepository repo = new AccountRepository();
    private static final String cardNumber_1_inRepo = "1111-2222-3333-4444";
    private static final String cardNumber_2_inRepo = "9765-2562-3433-4444";
    private static final String cardNumber_not_inRepo = "0000-2222-3333-4444";

    @BeforeEach
    void setUp(){
        repo.saveAllAccounts(Arrays.asList(
                new Account(cardNumber_1_inRepo, "1111", BigDecimal.valueOf(2000.0), 0, null),
                new Account(cardNumber_2_inRepo, "1234", BigDecimal.valueOf(5000.0), Account.MAX_PIN_ATTEMPTS - 1, null)
        ));
    }

    @Test
    void getAccountByCardName_existingAccount_account() {
        assertNotNull(repo.getAccountByCardName(cardNumber_1_inRepo));
        assertNotNull(repo.getAccountByCardName(cardNumber_2_inRepo));
    }
    @Test
    void getAccountByCardName_nonExistingAccount_null() {
        assertNull(repo.getAccountByCardName(cardNumber_not_inRepo));
    }
}