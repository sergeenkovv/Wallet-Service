package com.ivan.walletservice.exception;

/**
 * PlayerNotFoundException class representing an exception for when a player is not found.
 */
public class PlayerNotFoundException extends RuntimeException {

    /**
     * Constructs a PlayerNotFoundException with the specified message.
     * @param message The detail message of the exception.
     */
    public PlayerNotFoundException(String message) {
        super(message);
    }
}