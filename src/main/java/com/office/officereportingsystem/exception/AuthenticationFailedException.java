package com.office.officereportingsystem.exception;

public class AuthenticationFailedException extends RuntimeException{
    public AuthenticationFailedException(String messageCode) {
        super(messageCode);
    }
}
