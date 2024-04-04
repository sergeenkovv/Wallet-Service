package com.ivan.walletservice.exception;

/**
 * TransactionOperationException class representing an exception for transaction operation issues.
 */
public class TransactionOperationException extends RuntimeException {

    /**
     * Constructs a TransactionOperationException with the specified message.
     * @param message The detail message of the exception.
     */
    public TransactionOperationException(String message) {
        super(message);
    }
}