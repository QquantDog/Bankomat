package com.senla.core.exceptions;

import com.senla.core.ATMOperationType;

public class ATMStateException extends Exception {

    ATMOperationType type;

    public ATMStateException(ATMOperationType type, String message) {
        super(message);
        this.type = type;
    }
    @Override
    public String getMessage(){
        return "ATM " + type.toString() + " error due to: " + super.getMessage();
    }
//    public String toString(){
//
//    }
}
