package com.office.officereportingsystem.exception;

public class AccountNotFoundException extends RuntimeException {

    private final String messageCode;

    public AccountNotFoundException(String messageCode) {
        super(messageCode);
        this.messageCode = messageCode;
    }
    public String getMessageCode() {
        return messageCode;
    }
}
