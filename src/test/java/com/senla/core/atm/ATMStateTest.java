package com.senla.core.atm;

import com.senla.core.exceptions.ATMStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ATMStateTest {

    private ATMState atmState;

    @BeforeEach
    void setUp(){
        atmState = new ATMState();
    }

    @Test
    void depositAtmBalance_lessThanMaxLimit_noThrow() {
        assertDoesNotThrow(()->atmState.depositAtmBalance(BigDecimal.valueOf(1)));
    }
    @Test
    void depositAtmBalance_moreThanMaxLimit_throw() {
        assertThrows(ATMStateException.class, ()->atmState.depositAtmBalance(atmState.getUpperLimit().add(BigDecimal.valueOf(1.0))));
    }

    @Test
    void withdrawAtmBalance_moreThanBalance_throw() {
        assertThrows(ATMStateException.class, ()->atmState.withdrawAtmBalance(atmState.getAtmBalance().add(BigDecimal.valueOf(1.0))));
    }
    @Test
    void withdrawAtmBalance_lessThanBalance_noThrow() {
        assertDoesNotThrow(()->atmState.withdrawAtmBalance(BigDecimal.valueOf(1.0)));
    }
}