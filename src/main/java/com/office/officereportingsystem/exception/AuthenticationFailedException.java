package com.office.officereportingsystem.exception;

public class AuthenticationFailedException extends ApplicationException{

    private final Object data;

    public AuthenticationFailedException() {
        super("AUTHENTICATION_FAILED");
        this.data = null;
    }

    public AuthenticationFailedException(Object data) {
        super("AUTHENTICATION_FAILED");
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
