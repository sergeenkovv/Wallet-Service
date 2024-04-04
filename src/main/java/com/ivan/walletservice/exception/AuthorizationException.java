package com.ivan.walletservice.exception;

/**
 * AuthorizationException class representing an exception for authorization related issues.
 */
public class AuthorizationException extends RuntimeException {

    /**
     * Constructs an AuthorizationException with the specified message.
     *
     * @param message The detail message of the exception.
     */
    public AuthorizationException(String message) {
        super(message);
    }
}