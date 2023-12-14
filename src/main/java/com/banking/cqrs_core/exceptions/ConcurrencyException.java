package com.banking.cqrs_core.exceptions;

public class ConcurrencyException extends RuntimeException{
    public ConcurrencyException(String message) {
        super(message);
    }
}
