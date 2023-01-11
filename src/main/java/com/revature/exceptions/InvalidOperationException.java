package com.revature.exceptions;

public class InvalidOperationException extends RuntimeException {

    public InvalidOperationException() {
        super("Oops, search text too short. Please try again. Search text must be greater than 0 characters long.");
    }
}
