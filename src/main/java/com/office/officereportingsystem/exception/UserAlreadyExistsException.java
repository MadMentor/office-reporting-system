package com.office.officereportingsystem.exception;

public class UserAlreadyExistsException extends RuntimeException{

    private final Object adminData;

    public UserAlreadyExistsException(String message, Object adminData) {
        super(message);
        this.adminData = adminData;
    }

    public Object getAdminData() {
        return adminData;
    }
}
