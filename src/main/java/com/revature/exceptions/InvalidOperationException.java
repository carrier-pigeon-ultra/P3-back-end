package com.revature.exceptions;

public class InvalidOperationException extends RuntimeException {

    public InvalidOperationException() {
    }
    public InvalidOperationException(String message) {
        super(message);
    }
}
