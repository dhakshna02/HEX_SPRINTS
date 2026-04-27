package com.bank.MavericksBank.exceptions;

public class AccountOwnerInvalidException extends RuntimeException {
    public AccountOwnerInvalidException(String message) {
        super(message);
    }
}
