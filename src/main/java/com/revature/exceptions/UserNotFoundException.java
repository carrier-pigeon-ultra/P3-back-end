package com.revature.exceptions;

public class UserNotFoundException  extends Exception{
    public UserNotFoundException() {
        super("No account found with userId   provided. Please try again.");
    }
}
