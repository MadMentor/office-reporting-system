package com.office.officereportingsystem.exception;

public class CannotDeleteSuperAdminException extends ApplicationException{

    private final Object data;

    public CannotDeleteSuperAdminException() {
        super("CANNOT_DELETE_SUPER_ADMIN");
        this.data = null;
    }

    public CannotDeleteSuperAdminException(Object data) {
        super("CANNOT_DELETE_SUPER_ADMIN");
        this.data = data;
    }

    public Object getData() {
        return data;
    }
}
