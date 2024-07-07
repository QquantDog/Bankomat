package com.senla.core.account;

import com.senla.core.exceptions.CardValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @Nested
    class UnlockedAccountTest{
        @BeforeEach
        void setUpUnlockedAccount(){
            account = new Account("1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS-1, null);
        }
        @Test
        void lockAccount() {
//          здесь невозможна провоерка на то что кол-во попыток меньше на 1
            assertNull(account.getLockTime());
            account.lockAccount();
            assertNotNull(account.getLockTime());
        }

        @Test
        void setBalance_notPositive_throws() {
            assertThrows(IllegalArgumentException.class, ()->account.setBalance(BigDecimal.valueOf(-344.50)));
        }

    }

    @Nested
    class LockedAccountTest{
        @BeforeEach
        void setUpLockedAccount(){
            account = new Account("1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS, LocalDateTime.now());
        }

        @Test
        void unlockAccount() {
            assertNotNull(account.getLockTime());
            account.unlockAccount();
            assertNull(account.getLockTime());
            assertEquals(0, account.getPinAttempts());
        }

    }


    @ParameterizedTest
    @MethodSource("invalidAccountDataProvider")
    void preValidate_invalidData_throwsCardValidationException(String cardNumber, String pin, BigDecimal balance, int pinAttempts, LocalDateTime lockTime) {
        assertThrows(CardValidationException.class, ()->Account.preValidate(cardNumber, pin, balance, pinAttempts, lockTime));
    }
    @ParameterizedTest
    @MethodSource("validAccountDataProvider")
    void preValidate_validData_throwsCardValidationException(String cardNumber, String pin, BigDecimal balance, int pinAttempts, LocalDateTime lockTime) {
        assertDoesNotThrow(()->Account.preValidate(cardNumber, pin, balance, pinAttempts, lockTime));
    }

    //      valid : Arguments.of("1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS-1, null),
    private static Stream<Arguments> invalidAccountDataProvider() {
        return Stream.of(
                Arguments.of(" 1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS-1, null),
                Arguments.of("1111-2222-3333-4444", "a234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS-1, null),
                Arguments.of("1111-2222-3333-4444", "1234", BigDecimal.valueOf(-2000.0), Account.MAX_PIN_ATTEMPTS-1, null),
                Arguments.of("1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS+1, null),
                Arguments.of("1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS, null),
                Arguments.of("1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS-1, LocalDateTime.now()),
                Arguments.of("1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS-1, LocalDateTime.now())
        );
    }
    private static Stream<Arguments> validAccountDataProvider() {
        return Stream.of(
                Arguments.of("1111-2222-3333-4444", "1234", BigDecimal.valueOf(2000.0), Account.MAX_PIN_ATTEMPTS-1, null),
                Arguments.of("1411-2522-3383-4424", "7952", BigDecimal.valueOf(7567.757), 0, null),
                Arguments.of("1411-2522-3383-4424", "7952", BigDecimal.valueOf(7567.757), Account.MAX_PIN_ATTEMPTS, LocalDateTime.now()),
                Arguments.of("1411-2522-3383-4424", "7952", BigDecimal.valueOf(7567.757), Account.MAX_PIN_ATTEMPTS, LocalDateTime.of(3,4,5,6,7,8))
        );
    }

}