package com.ivan.walletservice.exception;

/**
 * RegistrationException class representing an exception for registration-related issues.
 */
public class RegistrationException extends RuntimeException {

    /**
     * Constructs a RegistrationException with the specified message.
     * @param message The detail message of the exception.
     */
    public RegistrationException(String message) {
        super(message);
    }
}