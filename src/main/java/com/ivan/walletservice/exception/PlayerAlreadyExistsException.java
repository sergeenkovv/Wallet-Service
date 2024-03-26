package com.ivan.walletservice.exception;

public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}