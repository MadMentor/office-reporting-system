package com.office.officereportingsystem.exception;

public class AccountNotFoundException extends ApplicationException {

    private final Object data;

    public AccountNotFoundException() {
        super("ACCOUNT_NOT_FOUND");
        this.data = null;
    }

    public AccountNotFoundException(Object account) {
        super("ACCOUNT_NOT_FOUND");
        this.data = account;
    }

    public Object getData() {
        return data;
    }
}
