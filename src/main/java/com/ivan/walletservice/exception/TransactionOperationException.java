package com.ivan.walletservice.exception;

public class TransactionOperationException extends RuntimeException {

    public TransactionOperationException(String message) {
        super(message);
    }
}