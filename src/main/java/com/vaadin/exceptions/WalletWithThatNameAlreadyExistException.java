package com.vaadin.exceptions;

public class WalletWithThatNameAlreadyExistException extends RuntimeException {
    public WalletWithThatNameAlreadyExistException(String message) {
        super(message);
    }
}
