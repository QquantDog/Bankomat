package com.senla.core.util;

import com.senla.core.account.Account;
import com.senla.core.exceptions.CardValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccountValidatorTest {

    @ParameterizedTest
    @MethodSource("invalidAccountDataProvider")
    void preValidate_invalidData_throwsCardValidationException(String cardNumber, String pin, BigDecimal balance, int pinAttempts, LocalDateTime lockTime) {
        assertThrows(CardValidationException.class, ()-> AccountValidator.preValidate(cardNumber, pin, balance, pinAttempts, lockTime));
    }
    @ParameterizedTest
    @MethodSource("validAccountDataProvider")
    void preValidate_validData_throwsCardValidationException(String cardNumber, String pin, BigDecimal balance, int pinAttempts, LocalDateTime lockTime) {
        assertDoesNotThrow(()->AccountValidator.preValidate(cardNumber, pin, balance, pinAttempts, lockTime));
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