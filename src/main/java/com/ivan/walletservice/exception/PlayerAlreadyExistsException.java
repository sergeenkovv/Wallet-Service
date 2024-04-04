package com.ivan.walletservice.exception;

/**
 * PlayerAlreadyExistsException class representing an exception for when a player already exists.
 */
public class PlayerAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a PlayerAlreadyExistsException with the specified message.
     * @param message The detail message of the exception.
     */
    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}