package com.revature.exceptions;

public class UserNotFoundException  extends RuntimeException{
    public UserNotFoundException() {
        super("No account found with email address  provided. Please try again.");
    }
}
