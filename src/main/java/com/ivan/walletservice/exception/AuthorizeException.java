package com.ivan.walletservice.exception;

public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String message) {
        super(message);
    }
}