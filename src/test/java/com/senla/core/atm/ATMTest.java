package com.senla.core.atm;

import com.senla.core.account.Account;
import com.senla.core.account.AccountRepository;

import com.senla.core.exceptions.CardNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ATMTest {

    @InjectMocks
    private ATM atm;

    @Mock
    private AccountRepository repo;

    @Mock
    private ATMState atmState;

    private String validCardNumber = "1111-1111-1111-1111";
    private String validPin = "1111";

    @Nested
    class  AuthTest{

//      Parametr Spy
        private Account fetchedAccount;

        @BeforeEach
        void setUpForFullValidation(){
            fetchedAccount = Mockito.spy(new Account(validCardNumber,validPin, BigDecimal.valueOf(10), 0, null));
            Mockito.when(repo.getAccountByCardName(Mockito.anyString()))
                    .thenReturn(fetchedAccount);
            Mockito.lenient().when(fetchedAccount.getCardNumber()).thenReturn(validCardNumber);
            Mockito.lenient().when(fetchedAccount.isLocked()).thenReturn(false);
            Mockito.lenient().when(fetchedAccount.getPin()).thenReturn(validPin);
        }

        @Test
        void authenticate_fullValidCard_currentAccountEqualsFound() {
            assertDoesNotThrow(()->atm.authenticate(validCardNumber, validPin));

            try{
                Field privateField = ATM.class.getDeclaredField("currentAccount");
                privateField.setAccessible(true);
                Account throughReflection = (Account) privateField.get(atm);

                assertEquals(throughReflection, fetchedAccount);
            } catch (NoSuchFieldException e){
                System.out.println("Abort test due to Not found field in ATM class");
                assertEquals(1,0);
            } catch (IllegalAccessException e) {
                System.out.println("Abort test due to Not found field in atm instance");
                assertEquals(1,0);
            }
        }

        @Test
        void authenticate_accountMissing_throws() {
            Mockito.when(repo.getAccountByCardName(Mockito.anyString()))
                    .thenReturn(null);
            assertThrows(CardNotFoundException.class, ()->atm.authenticate(validCardNumber, validPin), "Card number not found");
        }
    }

    @Test
    void checkBalance() {
    }

    @Test
    void withdraw() {
    }

    @Test
    void deposit() {
    }

    @Test
    void printTransactionHistory() {
    }

    @Test
    void ensureAuthenticated() {
    }
}