package com.senla.core.atm;

public enum ATMOperationType {
    DEPOSIT(1),
    WITHDRAW(2);

    private final int value;

    ATMOperationType(int value){
        this.value = value;
    }

    @Override
    public String toString(){
        return switch (this.value) {
            case 1 -> "deposit";
            case 2 -> "withdraw";
            default -> "invalid type";
        };
    }
}
