package com.senla.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CardValidatorTest {

    @ParameterizedTest
    @MethodSource("validCardsProvider")
    public void testValidCards(String card) {
        assertTrue(CardValidator.isValidCardNumber(card));
    }

    @ParameterizedTest
    @MethodSource("invalidCardsProvider")
    public void testInvalidCards(String card) {
        assertFalse(CardValidator.isValidCardNumber(card));
    }

    private static Stream<Arguments> validCardsProvider() {
        return Stream.of(
                Arguments.of("1111-2222-3333-4444"),
                Arguments.of("1001-2312-3849-4164")
        );
    }

    private static Stream<Arguments> invalidCardsProvider() {
        return Stream.of(
                Arguments.of(" 1111-2222-3333-4444"),
                Arguments.of("b"),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("1111-2222-3333-4444 "),
                Arguments.of("1111222233334444"),
                Arguments.of("1111-222a-3333-4444")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPinProvider")
    public void testInvalidPin(String pin) {
        assertFalse(CardValidator.isValidPin(pin));
    }

    @ParameterizedTest
    @MethodSource("validPinProvider")
    public void testValidPin(String pin) {
        assertTrue(CardValidator.isValidPin(pin));
    }

    private static Stream<Arguments> validPinProvider() {
        return Stream.of(
                Arguments.of("1111"),
                Arguments.of("5234"),
                Arguments.of("7832")
        );
    }

    private static Stream<Arguments> invalidPinProvider() {
        return Stream.of(
                Arguments.of(" 1111"),
                Arguments.of("1234 "),
                Arguments.of("12a4 "),
                Arguments.of("b"),
                Arguments.of(""),
                Arguments.of(" ")
        );
    }

}