package com.senla.core.exceptions;


public class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) {
        super(message);
    }

    @Override
    public String getMessage(){
        return "Invalid file format: " + super.getMessage();
    }
}
