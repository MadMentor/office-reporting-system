package com.office.officereportingsystem.exception;

public class CannotDeleteAdminException extends ApplicationException{

    private final Object data;

    public CannotDeleteAdminException() {
        super("CANNOT_DELETE_ADMIN");
        this.data = null;
    }

    public CannotDeleteAdminException(Object data) {
        super("CANNOT_DELETE_ADMIN");
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
