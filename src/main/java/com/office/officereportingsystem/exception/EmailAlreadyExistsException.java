package com.office.officereportingsystem.exception;

public class EmailAlreadyExistsException extends ApplicationException{

    private final Object data;

    public EmailAlreadyExistsException() {
        super("EMAIL_ALREADY_EXISTS");
        this.data = null;
    }

    public EmailAlreadyExistsException(Object adminData) {
        super("EMAIL_ALREADY_EXISTS");
        this.data = adminData;
    }

    public Object getData() {
        return data;
    }
}
