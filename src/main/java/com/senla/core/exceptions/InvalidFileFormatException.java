package com.senla.core.exceptions;

import com.senla.core.ATM;

import java.util.logging.Logger;

public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) {
        super(message);
    }

    @Override
    public String getMessage(){
        return "Invalid file format: " + super.getMessage();
    }
}
