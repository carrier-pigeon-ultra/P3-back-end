package com.revature.exceptions;

public class WeakPasswordException extends Exception{
    public WeakPasswordException() {
        super("Weak password.");
    }
}
