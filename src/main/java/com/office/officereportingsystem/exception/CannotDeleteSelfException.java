package com.office.officereportingsystem.exception;

public class CannotDeleteSelfException extends ApplicationException{

    private final Object data;

    public CannotDeleteSelfException() {
        super("CANNOT_DELETE_SELF");
        this.data = null;
    }

    public CannotDeleteSelfException(Object data) {
        super("CANNOT_DELETE_SELF");
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
